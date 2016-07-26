package Arrows.Impl;



import Arrows.*;
import Arrows.Utils.ArrowUtils;
import java.util.*;
import java.util.function.BiPredicate;



public class FilterArrow<K, V> implements ArrowView<K, V>
{
	private final Arrow<K, V> arrow;
	private final BiPredicate<K, V> filter;



	public FilterArrow( Arrow<K, V> arrow, BiPredicate<K, V> filter )
	{
		this.arrow = arrow;
		this.filter = filter;
	}

	@Override
	public ArrowConfig config()
	{
		return arrow.config();
	}

	@Override
	public Set<Map.Entry<K, V>> relations()
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
				if( filter.test( source, target ) )
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
			if( filter.test( source, target ) )
			{
				filteredTargets.add( target );
			}
		}

		return filteredTargets;
	}

	@Override
	public V target( K source ) throws Exception
	{
		return (V) ArrowUtils.target( this, source );
	}

	@Override
	public Arrow<V, K> inverse()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}
}
