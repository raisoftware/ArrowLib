package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ArrowUtils;
import Arrows.Utils.ExceptionUtils;
import Shared.BasicSet0;
import Shared.MethodList.MethodList;
import Shared.MethodList.MethodList.Positioning;
import Shared.Set0;
import com.google.common.collect.*;
import java.util.*;
import java.util.Map.Entry;

public class GenericArrow<K, V> implements Arrow<K, V>
{
	private final SetMultimap<K, V> keysToValues = HashMultimap.create();
	private final SetMultimap<V, K> valuesToKeys = HashMultimap.create();

	private final MethodList<Arrow.Editor> methodSet;
	private final Arrow<V, K> inverseArrow = new InverseGenericArrow();
	private final Editor<K, V> arrowEditor = new GenericArrowEditor();
	private final Class domain;
	private final Class codomain;
	private final boolean allowsMultipleSources;
	private final boolean allowsMultipleTargets;
	private final boolean listenable;
	private final Diagram diagram;


	public GenericArrow( Diagram diagram, Class domain, Class codomain, boolean allowsMultipleSources, boolean allowsMultipleTargets, boolean listenable )
	{
		methodSet = new MethodList( Arrow.Editor.class );
		methodSet.insert( arrowEditor, Positioning.After );
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
	public MethodList<Editor> listeners()
	{
		return methodSet;
	}

	@Override
	public Editor<K, V> editor()
	{
		return listenable ? methodSet.publisher() : arrowEditor;
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

	private final class GenericArrowEditor implements Arrow.Editor<K, V>
	{
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

			ArrowUtils.checkForMultipleSourcesTargets( GenericArrow.this, allowsMultipleSources, allowsMultipleTargets, source, targets );

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
	}

	private final class InverseGenericArrow implements Arrow<V, K>
	{
		Editor<V, K> inverseGenericArrowEditor = new InverseGenericArrowEditor();

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
		public MethodList<Editor> listeners()
		{
			return methodSet;
		}

		@Override
		public Editor<V, K> editor()
		{
			return inverseGenericArrowEditor;
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

		private final class InverseGenericArrowEditor implements Arrow.Editor<V, K>
		{
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
			public void remove( V source, K target )
			{
				GenericArrow.this.editor().remove( target, source );
			}

			@Override
			public void connect( Iterable<? extends V> sources, K target )
			{
				GenericArrow.this.editor().connect( target, sources );
			}

		}
	}

}
