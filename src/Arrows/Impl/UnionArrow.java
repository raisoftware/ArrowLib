package Arrows.Impl;

import Arrows.Arrow;
import Arrows.ArrowConfig;
import java.util.*;

public class UnionArrow implements Arrow
{
	List<Arrow> arrows = new ArrayList<>();

	public UnionArrow( Arrow... arrows ) throws IllegalArgumentException
	{
		if( arrows == null || arrows.length == 0 )
			throw new IllegalArgumentException( "Empty arrow list" );

		for( int i = 0; i < arrows.length; ++i )
		{
			addArrow( arrows[i] );
		}
	}

	public final void addArrow( Arrow arrow ) throws IllegalArgumentException
	{
		if( arrow == null )
			throw new IllegalArgumentException( "Arrow is null" );

		arrows.add( arrow );
	}

	@Override
	public ArrowConfig config()
	{
		//might need to get one as parameter on creation
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set relations()
	{
		//expensive
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set sources()
	{
		Set unionSources = new HashSet<>();
		for( Arrow arrow : arrows )
		{
			unionSources.addAll( arrow.sources() );
		}
		return unionSources;
	}

	@Override
	public Set targets()
	{
		Set unionTargets = new HashSet<>();
		for( Arrow arrow : arrows )
		{
			unionTargets.addAll( arrow.targets() );
		}
		return unionTargets;
	}

	@Override
	public Arrow inverse()
	{
		// this one might be easy
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set targets( Object source )
	{
		Set unionTargets = new HashSet<>();
		for( Arrow arrow : arrows )
		{
			unionTargets.addAll( arrow.targets( source ) );
		}
		return unionTargets;
	}

	@Override
	public Object target( Object source ) throws Exception
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

}
