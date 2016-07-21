package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ExceptionUtils;
import com.google.common.collect.*;
import java.util.*;
import java.util.Map.Entry;

public class ManyToManyArrow<K, V> implements EditableArrow<K, V>
{
	private final SetMultimap<K, V> keysToValues = HashMultimap.create();
	private final SetMultimap<V, K> valuesToKeys = HashMultimap.create();

	EditableArrow<V, K> inverseArrow = new InverseManyToManyArrow();

	private final ArrowConfig config;

	public ManyToManyArrow( ArrowConfig config )
	{
		this.config = config;
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
	public ArrowConfig config()
	{
		return config;
	}

	@Override
	public void connect( Collection<? extends K> sources, V target )
	{
		inverse().connect( target, sources() );
	}

	@Override
	public void connect( K source, Collection<? extends V> targets )
	{
		assert ( config().domain().isInstance( source ) ) : source + " not of type " + config().domain();
		for( V target : targets )
		{
			assert ( config().codomain().isInstance( target ) ) : target + " not of type " + config().codomain();
		}

		checkforMultipleSourcesTargets( this, source, targets );

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
		assert ( config().domain().isInstance( source ) ) : source + " not of type " + config().domain();
		assert ( config().codomain().isInstance( target ) ) : target + " not of type " + config().codomain();

		if( ( !config.allowsMultipleTargets() && sources().contains( source ) )
			|| ( !config.allowsMultipleSources() && sources().contains( source ) ) )
		{
			throw ExceptionUtils.multipleSourcesTargetsException( source, target );
		}

		put( source, target );
	}

	@Override
	public Set<Entry<K, V>> relations()
	{
		return keysToValues.entries();
	}

	@Override
	public Set<K> sources()
	{
		return keysToValues.keySet();
	}

	@Override
	public Set<V> targets()
	{
		return valuesToKeys.keySet();
	}

	@Override
	public Set<V> targets( K source )
	{
		return keysToValues.get( source );
	}

	@Override
	public V target( K source ) throws Exception
	{
		Set<V> targets = keysToValues.get( source );
		if( targets.size() != 1 )
			throw ExceptionUtils.targetsNumberException( targets.size() );
		return targets.iterator().next();
	}

	@Override
	public String toString()
	{
		return "Arrow<" + config().domain() + "," + config().codomain() + ">  Relations:" + relations();
	}

	@Override
	public EditableArrow<V, K> inverse()
	{
		return inverseArrow;
	}

	private static void checkforMultipleSourcesTargets( Arrow arrow, Object source, Collection<? extends Object> targets )
	{
		ArrowConfig arrowConfig = arrow.config();
		if( !arrowConfig.allowsMultipleTargets() && arrow.sources().contains( source ) )
		{
			throw ExceptionUtils.multipleSourcesTargetsException( source, targets );
		}
		if( !arrowConfig.allowsMultipleTargets() )
		{
			for( Object target : targets )
			{
				if( arrow.targets().contains( target ) )
				{
					throw ExceptionUtils.multipleSourcesTargetsException( source, targets );
				}
			}
		}
	}


	private final class InverseManyToManyArrow implements EditableArrow<V, K>
	{
		@Override
		public Set<V> sources()
		{
			return valuesToKeys.keySet();
		}

		@Override
		public Set<K> targets()
		{
			return keysToValues.keySet();
		}

		@Override
		public Set<K> targets( V source )
		{
			return valuesToKeys.get( source );
		}

		@Override
		public EditableArrow<K, V> inverse()
		{
			return ManyToManyArrow.this;
		}

		@Override
		public ArrowConfig config()
		{
			return config.inverse();
		}

		@Override
		public void connect( V source, Collection<? extends K> targets )
		{
			assert ( config().domain().isInstance( source ) );
			for( K target : targets )
			{
				assert ( config().codomain().isInstance( target ) );
			}

			checkforMultipleSourcesTargets( this, source, targets );

			boolean mapChanged = valuesToKeys.putAll( source, targets );

			for( K target : targets )
			{
				boolean changed = keysToValues.put( target, source );
				mapChanged |= changed;
			}
		}

		@Override
		public void connect( V source, K target )
		{
			ManyToManyArrow.this.connect( target, source );
		}

		@Override
		public Set<Entry<V, K>> relations()
		{
			return valuesToKeys.entries();
		}

		@Override
		public void remove( V source, K target )
		{
			ManyToManyArrow.this.remove( target, source );
		}

		@Override
		public K target( V source ) throws Exception
		{
			Set<K> targets = valuesToKeys.get( source );
			if( targets.size() != 1 )
				throw ExceptionUtils.targetsNumberException( targets.size() );
			return targets.iterator().next();
		}

		@Override
		public String toString()
		{
			return "Arrow<" + config().domain() + "," + config().codomain() + ">  Relations:" + relations();
		}

		@Override
		public void connect( Collection<? extends V> sources, K target )
		{
			inverse().connect( target, sources );
		}
	}
}
