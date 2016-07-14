package Arrows.Impl;

import Arrows.Arrow;
import Arrows.ArrowConfig;
import com.google.common.collect.*;
import java.util.*;
import java.util.Map.Entry;

public class ManyToManyArrow<K, V> implements Arrow<K, V>
{
	private final SetMultimap<K, V> keysToValues = HashMultimap.create();
	private final SetMultimap<V, K> valuesToKeys = HashMultimap.create();

	Arrow<V, K> inverseArrow = new InverseManyToManyMap();

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
	public void connect( K source, Collection<? extends V> targets )
	{
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
	public Arrow inverse()
	{
		return inverseArrow;
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
			throw new Exception( "Number of targets is " + targets.size() + ". There should only be one target." );
		return targets.iterator().next();
	}

	private final class InverseManyToManyMap implements Arrow<V, K>
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
		public Arrow inverse()
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
			put( target, source );
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
				throw new Exception( "Number of targets is " + targets.size() + ". There should only be one target." );
			return targets.iterator().next();
		}
	}
}
