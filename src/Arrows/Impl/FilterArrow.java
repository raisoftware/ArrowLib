package Arrows.Impl;



import Arrows.*;
import Arrows.Utils.ArrowUtils;
import Shared.*;
import java.util.*;
import java.util.function.BiPredicate;



public class FilterArrow<K, V> implements ArrowView<K, V>
{
	private final Diagram diagram;

	private final Arrow<K, V> arrow;
	private final BiPredicate<K, V> filter;

	public FilterArrow( Diagram diagram, Arrow<K, V> arrow, BiPredicate<K, V> filter )
	{
		this.diagram = diagram;
		this.arrow = arrow;
		this.filter = filter;
	}

	@Override
	public Set0<Map.Entry<K, V>> relations()
	{
		return ArrowUtils.generateRelations( this );
	}

	@Override
	public Set0<K> sources()
	{
		Set0<K> filteredSources = new BasicSet0( new HashSet<>() );
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
	public Set0<V> targets()
	{
		Set0<V> filteredTargets = new BasicSet0( new HashSet<>() );
		for( K source : arrow.sources() )
		{
			Set0Utils.addAll( filteredTargets, targets( source ) );
		}
		return filteredTargets;
	}

	@Override
	public Set0<V> targets( K source )
	{
		Set0<V> filteredTargets = new BasicSet0( new HashSet<>() );

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

	@Override
	public Class codomain()
	{
		return arrow.codomain();
	}

	@Override
	public Class domain()
	{
		return arrow.domain();
	}
}
