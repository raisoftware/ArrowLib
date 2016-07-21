package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ArrowUtils;
import java.util.*;
import java.util.function.Function;


public class ComputedArrowImpl<K, V> implements ComputedArrow<K, V>
{
	Set<K> sources = new HashSet<>();
	Function<K, Set<V>> function;

	public ComputedArrowImpl( Function<K, Set<V>> function )
	{
		this.function = function;
	}

	@Override
	public void addSource( K source )
	{
		sources.add( source );
	}

	@Override
	public void addSources( Collection<? extends K> source )
	{
		sources.addAll( source );
	}

	@Override
	public void remove( K source )
	{
		sources.remove( source );
	}

	@Override
	public Set<K> sources()
	{
		return sources;
	}

	@Override
	public Set<V> targets()
	{
		Set<V> targets = new HashSet<>();
		for( K source : sources )
		{
			targets.addAll( function.apply( source ) );
		}
		return targets;
	}

	@Override
	public ArrowConfig config()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public V target( K source ) throws Exception
	{
		Set<V> targets = targets( source );
		if( targets.size() != 1 )
			throw new Exception( "Number of targets is " + targets.size() + ". There should only be one target." );
		return targets.iterator().next();
	}

	@Override
	public Set<V> targets( K source )
	{
		return function.apply( source );
	}

	@Override
	public Set<Map.Entry<K, V>> relations()
	{
		return ArrowUtils.generateRelations( this );
	}

	@Override
	public Arrow<V, K> inverse()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

}
