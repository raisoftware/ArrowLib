package ArrowLib.Utils;

import ArrowLib.*;
import com.google.common.collect.*;
import java.util.*;
import java.util.Map.Entry;

public class ManyToManyMap<K, V> implements Arrow<K, V>
{
	//De ce e SetMultimap in loc de set multimap???
	private final SetMultimap<K, V> keysToValues = HashMultimap.create();

	private final SetMultimap<V, K> valuesToKeys = HashMultimap.create();

	public Set<V> getValues( K key )
	{
		return keysToValues.get( key );
	}

	public Set<K> getKeys( V value )
	{
		return valuesToKeys.get( value );
	}

	public boolean put( K key, V value )
	{
		return keysToValues.put( key, value ) && valuesToKeys.put( value, key );
	}

	public boolean putAll( K key, Iterable<? extends V> values )
	{
		boolean changed = false;
		for( V value : values )
		{
			changed = put( key, value ) || changed;
		}
		return changed;
	}

	public Set<Entry<V, K>> inverseRelations()
	{
		return valuesToKeys.entries();
	}

	@Override
	public ArrowConfig config()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void connect( K source, Collection<? extends V> targets )
	{
		putAll( source, targets );
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
	public Set2 sources()
	{
		//TOFIX
		return new Set2( null, this );
	}

	@Override
	public Set2 targets()
	{
		//TOFIX
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set2 targets( Object source )
	{
		//TOFIX
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Arrow inverse()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}
}
