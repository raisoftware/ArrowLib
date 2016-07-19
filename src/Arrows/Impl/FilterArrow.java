package Arrows.Impl;



import Arrows.*;
import java.util.*;
import java.util.function.BiPredicate;



public class FilterArrow<K, V> implements Arrow<K, V>
{
	private Arrow<K, V> arrow;
	private BiPredicate<K, V> bipredicate;



	public FilterArrow( Arrow<K, V> arrow, BiPredicate<K, V> bipredicate )
	{
		this.arrow = arrow;
		this.bipredicate = bipredicate;
	}



	@Override
	public ArrowConfig config()
	{
		return arrow.config();
	}



	@Override
	public void connect( K source, Collection<? extends V> targets )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}



	@Override
	public void connect( K source, V target )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}



	@Override
	public Set<Map.Entry<K, V>> relations()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}



	@Override
	public void remove( K source, V target )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}



	@Override
	public Set<K> sources()
	{
		Set<K> filteredSources = new HashSet<>();
		for( K source : arrow.sources() )
		{
			for( V target : arrow.targets( source ) )
			{
				if( bipredicate.test( source, target ) )
				{
					filteredSources.add( source );
					break;
				}
			}
		}
		return filteredSources;
	}



	@Override
	public Set<V> targets()
	{
		Set<V> filteredTargets = new HashSet<>();
		for( K source : arrow.sources() )
		{
			filteredTargets.addAll( targets( source ) );
		}
		return filteredTargets;
	}



	@Override
	public Set<V> targets( K source )
	{
		Set<V> filteredTargets = new HashSet<>();

		for( V target : arrow.targets( source ) )
		{
			if( bipredicate.test( source, target ) )
			{
				filteredTargets.add( target );
			}
		}

		return filteredTargets;
	}



	@Override
	public V target( K source ) throws Exception
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}



	@Override
	public Arrow<V, K> inverse()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

}
