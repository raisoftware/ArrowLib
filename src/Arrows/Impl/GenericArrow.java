package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ArrowUtils;
import Arrows.Utils.ExceptionUtils;
import Shared.Collection0.*;
import Shared.MethodBus.MethodBus;
import com.google.common.collect.*;
import java.util.*;
import java.util.Map.Entry;

public class GenericArrow<K, V> implements Arrow<K, V>
{
	private final SetMultimap<K, V> keysToValues = HashMultimap.create();
	private final SetMultimap<V, K> valuesToKeys = HashMultimap.create();

	private final MethodBus<ArrowEditor> methodBus;
	private final Arrow<V, K> inverseArrow = new InverseGenericArrow();
	private final Class domain;
	private final Class codomain;
	private final boolean allowsMultipleSources;
	private final boolean allowsMultipleTargets;
	private final boolean listenable;
	private final Diagram diagram;


	public GenericArrow( Diagram diagram, Class domain, Class codomain, boolean allowsMultipleSources, boolean allowsMultipleTargets, boolean listenable )
	{
		methodBus = new MethodBus<>( ArrowEditor.class );
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
	public Set0<Entry<K, V>> relations()
	{
		return new BasicSet0( keysToValues.entries(), Map.Entry.class );
	}

	@Override
	public Set0<K> sources()
	{
		return new BasicSet0( keysToValues.keySet(), domain() );
	}

	@Override
	public Set0<V> targets()
	{
		return new BasicSet0( valuesToKeys.keySet(), codomain() );
	}

	@Override
	public Set0<V> targets( K source )
	{
		return new BasicSet0( keysToValues.get( source ), codomain() );
	}

	@Override
	public V target( K source )
	{
		return (V) ArrowUtils.target( this, source );
	}

	@Override
	public String toString()
	{
		return ArrowUtils.toString( diagram, this, "GenericArrow" );
	}

	@Override
	public OrderedSet0<ArrowEditor> subscribers()
	{
		return methodBus;
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

	private void removeInternal( K source, V target )
	{
		keysToValues.remove( source, target );
		valuesToKeys.remove( target, source );
	}

	@Override
	public void remove( K source, V target )
	{
		removeInternal( source, target );
		methodBus.publisher().remove( source, target );
	}

	@Override
	public void removeAll( K source, Iterable<? extends V> targets )
	{
		ImmutableList<? extends V> targetsList = ImmutableList.copyOf( targets );
		for( V target : targetsList )
		{
			removeInternal( source, target );
		}
		methodBus.publisher().removeAll( source, targetsList );
	}

	@Override
	public void removeAll( Iterable<? extends K> sources, V target )
	{
		ImmutableList<? extends K> sourcesList = ImmutableList.copyOf( sources );
		for( K source : sourcesList )
		{
			removeInternal( source, target );
		}
		methodBus.publisher().removeAll( sourcesList, target );
	}

	@Override
	public void aim( Iterable<? extends K> sources, V target )
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

		methodBus.publisher().aim( sources, target );
	}

	@Override
	public void aim( K source, Iterable<? extends V> targets )
	{
		assert ( domain().isInstance( source ) ) : source + " not of type " + domain();
		for( V target : targets )
		{
			assert ( codomain().isInstance( target ) ) : target + " not of type " + codomain();
		}

		ArrowUtils.checkForMultipleSourcesTargets( GenericArrow.this, allowsMultipleSources, allowsMultipleTargets, source, targets );

		boolean mapChanged = keysToValues.putAll( source, targets );

		for( V target : targets )
		{
			boolean changed = valuesToKeys.put( target, source );
			mapChanged |= changed;
		}

		methodBus.publisher().aim( source, targets );
	}

	@Override
	public void aim( K source, V target )
	{
		assert ( domain().isInstance( source ) ) : source + " not of type " + domain();
		assert ( codomain().isInstance( target ) ) : target + " not of type " + codomain();

		if( ( !allowsMultipleTargets && sources().contains( source ) )
			|| ( !allowsMultipleSources && sources().contains( source ) ) )
		{
			throw ExceptionUtils.multipleSourcesTargetsException( source, target );
		}

		put( source, target );

		methodBus.publisher().aim( source, target );
	}

	private final class InverseGenericArrow implements Arrow<V, K>
	{
		@Override
		public Set0<V> sources()
		{
			return new BasicSet0( valuesToKeys.keySet(), domain() );
		}

		@Override
		public Set0<K> targets()
		{
			return new BasicSet0( keysToValues.keySet(), codomain() );
		}

		@Override
		public Set0<K> targets( V source )
		{
			return new BasicSet0( valuesToKeys.get( source ), codomain() );
		}

		@Override
		public Arrow<K, V> inverse()
		{
			return GenericArrow.this;
		}

		@Override
		public Set0<Entry<V, K>> relations()
		{
			return new BasicSet0( valuesToKeys.entries(), Map.Entry.class );
		}

		@Override
		public K target( V source )
		{
			return (K) ArrowUtils.target( this, source );
		}

		@Override
		public String toString()
		{
			return ArrowUtils.toString( diagram, this, "InverseGenericArrow" );
		}

		@Override
		public OrderedSet0<ArrowEditor> subscribers()
		{
			return methodBus;
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

		@Override
		public void aim( V source, Iterable<? extends K> targets )
		{
			GenericArrow.this.aim( targets, source );
		}

		@Override
		public void aim( V source, K target )
		{
			GenericArrow.this.aim( target, source );
		}

		@Override
		public void remove( V source, K target )
		{
			GenericArrow.this.remove( target, source );
		}

		@Override
		public void aim( Iterable<? extends V> sources, K target )
		{
			GenericArrow.this.aim( target, sources );
		}

		@Override
		public void removeAll( V source, Iterable<? extends K> targets )
		{
			GenericArrow.this.removeAll( targets, source );
		}

		@Override
		public void removeAll( Iterable<? extends V> sources, K target )
		{
			GenericArrow.this.removeAll( target, sources );
		}

	}

}
