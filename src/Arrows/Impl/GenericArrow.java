package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ArrowUtils;
import Arrows.Utils.ExceptionUtils;
import Shared.BasicSet0;
import Shared.MethodBus.MethodSet.MethodSet;
import Shared.Set0;
import com.google.common.collect.*;
import java.util.*;
import java.util.Map.Entry;

public class GenericArrow<K, V> implements Arrow<K, V>, Arrow.Editor<K, V>
{
	private final SetMultimap<K, V> keysToValues = HashMultimap.create();
	private final SetMultimap<V, K> valuesToKeys = HashMultimap.create();

	MethodSet<Arrow.Editor> methodSet;
	Arrow<V, K> inverseArrow = new InverseGenericArrow();
	private final Class domain;
	private final Class codomain;
	private final boolean allowsMultipleSources;
	private final boolean allowsMultipleTargets;
	private final boolean listenable;
	private final Diagram diagram;


	public GenericArrow( Diagram diagram, Class domain, Class codomain, boolean allowsMultipleSources, boolean allowsMultipleTargets, boolean listenable )
	{
		methodSet = new MethodSet( this, Arrow.Editor.class );
		this.domain = domain;
		this.codomain = codomain;
		this.listenable = listenable;
		this.allowsMultipleSources = allowsMultipleSources;
		this.allowsMultipleTargets = allowsMultipleTargets;
		this.diagram = diagram;
	}

	private boolean put( K key, V value )
	{
		return keysToValues.put( key, value ) && valuesToKeys.put( value, key );
	}

	@Override
	public void remove( K source, V target )
	{
		if( target == null )
		{
			Set<V> removedTargets = keysToValues.removeAll( source );
			for( V removedTarget : removedTargets )
			{
				valuesToKeys.remove( removedTarget, source );
			}
		}
		else if( source == null )
		{
			Set<K> removedSources = valuesToKeys.removeAll( target );
			for( K removedSource : removedSources )
			{
				keysToValues.remove( removedSource, target );
			}
		}
		else
		{
			keysToValues.remove( source, target );
			valuesToKeys.remove( target, source );
		}
	}

	@Override
	public void connect( Iterable<? extends K> sources, V target )
	{
		assert ( codomain().isInstance( target ) );
		for( K source : sources )
		{
			assert ( domain().isInstance( source ) );
		}

		ArrowUtils.checkForMultipleSourcesTargets( inverse(), allowsMultipleTargets, allowsMultipleSources, target, sources );

		boolean mapChanged = valuesToKeys.putAll( target, sources );

		for( K source : sources )
		{
			boolean changed = keysToValues.put( source, target );
			mapChanged |= changed;
		}
	}

	@Override
	public void connect( K source, Iterable<? extends V> targets )
	{
		assert ( domain().isInstance( source ) ) : source + " not of type " + domain();
		for( V target : targets )
		{
			assert ( codomain().isInstance( target ) ) : target + " not of type " + codomain();
		}

		ArrowUtils.checkForMultipleSourcesTargets( this, allowsMultipleSources, allowsMultipleTargets, source, targets );

		boolean mapChanged = keysToValues.putAll( source, targets );

		for( V target : targets )
		{
			boolean changed = valuesToKeys.put( target, source );
			mapChanged |= changed;
		}
	}

	@Override
	public void connect( K source, V target )
	{
		assert ( domain().isInstance( source ) ) : source + " not of type " + domain();
		assert ( codomain().isInstance( target ) ) : target + " not of type " + codomain();

		if( ( !allowsMultipleTargets && sources().contains( source ) )
			|| ( !allowsMultipleSources && sources().contains( source ) ) )
		{
			throw ExceptionUtils.multipleSourcesTargetsException( source, target );
		}

		put( source, target );
	}

	@Override
	public Set0<Entry<K, V>> relations()
	{
		return new BasicSet0( keysToValues.entries() );
	}

	@Override
	public Set0<K> sources()
	{
		return new BasicSet0( keysToValues.keySet() );
	}

	@Override
	public Set0<V> targets()
	{
		return new BasicSet0( valuesToKeys.keySet() );
	}

	@Override
	public Set0<V> targets( K source )
	{
		return new BasicSet0( keysToValues.get( source ) );
	}

	@Override
	public V target( K source ) throws Exception
	{
		return (V) ArrowUtils.target( this, source );
	}

	@Override
	public String toString()
	{
		return ArrowUtils.toString( diagram, this, "GenericArrow" );
	}

	@Override
	public Set0<Editor> listeners()
	{
		return methodSet;
	}

	@Override
	public Editor<K, V> editor()
	{
		return listenable ? methodSet.publisher() : this;
	}

	@Override
	public Arrow<V, K> inverse()
	{
		return inverseArrow;
	}

	@Override
	public Class codomain()
	{
		return codomain;
	}

	@Override
	public Class domain()
	{
		return domain;
	}

	private final class InverseGenericArrow implements Arrow<V, K>, Arrow.Editor<V, K>
	{
		@Override
		public Set0<V> sources()
		{
			return new BasicSet0( valuesToKeys.keySet() );
		}

		@Override
		public Set0<K> targets()
		{
			return new BasicSet0( keysToValues.keySet() );
		}

		@Override
		public Set0<K> targets( V source )
		{
			return new BasicSet0( valuesToKeys.get( source ) );
		}

		@Override
		public Arrow<K, V> inverse()
		{
			return GenericArrow.this;
		}

		@Override
		public void connect( V source, Iterable<? extends K> targets )
		{
			GenericArrow.this.editor().connect( targets, source );
		}

		@Override
		public void connect( V source, K target )
		{
			GenericArrow.this.editor().connect( target, source );
		}

		@Override
		public Set0<Entry<V, K>> relations()
		{
			return new BasicSet0( valuesToKeys.entries() );
		}

		@Override
		public void remove( V source, K target )
		{
			GenericArrow.this.editor().remove( target, source );
		}

		@Override
		public K target( V source ) throws Exception
		{
			return (K) ArrowUtils.target( this, source );
		}

		@Override
		public String toString()
		{
			return ArrowUtils.toString( diagram, this, "InverseGenericArrow" );
		}

		@Override
		public void connect( Iterable<? extends V> sources, K target )
		{
			GenericArrow.this.editor().connect( target, sources );
		}

		@Override
		public Set0<Editor> listeners()
		{
			return methodSet;
		}

		@Override
		public Editor<V, K> editor()
		{
			return this;
		}

		@Override
		public Class codomain()
		{
			return domain;
		}

		@Override
		public Class domain()
		{
			return codomain;
		}
	}

}
