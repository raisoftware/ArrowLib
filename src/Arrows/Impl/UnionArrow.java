package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ArrowUtils;
import java.util.*;

public class UnionArrow implements ArrowView
{
	List<Arrow> arrows = new ArrayList<>();

	ArrowView inverseArrow = new InverseUnionArrow();

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
		return ArrowUtils.generateRelations( this );
	}

	private Set unionSources( boolean inverse )
	{
		Set unionSources = new HashSet<>();
		for( Arrow arrowInList : arrows )
		{
			Arrow arrow = arrowInList;
			if( inverse )
			{
				arrow = arrowInList.inverse();
			}
			unionSources.addAll( arrow.sources() );
		}
		return unionSources;
	}

	private Set unionTargets( boolean inverse )
	{
		Set unionTargets = new HashSet<>();
		for( Arrow arrowInList : arrows )
		{
			Arrow arrow = arrowInList;
			if( inverse )
			{
				arrow = arrowInList.inverse();
			}
			unionTargets.addAll( arrow.targets() );
		}
		return unionTargets;
	}

	private Set unionTargets( Object source, boolean inverse )
	{
		Set unionTargets = new HashSet<>();
		for( Arrow arrowInList : arrows )
		{
			Arrow arrow = arrowInList;
			if( inverse )
			{
				arrow = arrowInList.inverse();
			}
			unionTargets.addAll( arrow.targets( source ) );
		}
		return unionTargets;
	}


	@Override
	public Set sources()
	{
		return unionSources( false );
	}

	@Override
	public Set targets()
	{
		return unionTargets( false );
	}

	@Override
	public ArrowView inverse()
	{
		return inverseArrow;
	}

	@Override
	public Set targets( Object source )
	{
		return unionTargets( source, false );
	}

	@Override
	public Object target( Object source ) throws Exception
	{
		return ArrowUtils.target( this, source );
	}

	private final class InverseUnionArrow implements ArrowView
	{

		@Override
		public ArrowConfig config()
		{
			throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public Set sources()
		{
			return unionSources( true );
		}

		@Override
		public Set targets()
		{
			return unionTargets( true );
		}

		@Override
		public Object target( Object source ) throws Exception
		{
			return ArrowUtils.target( this, source );
		}

		@Override
		public Set targets( Object source )
		{
			return unionTargets( source, true );
		}

		@Override
		public Set relations()
		{
			return ArrowUtils.generateRelations( this );
		}

		@Override
		public ArrowView inverse()
		{
			return UnionArrow.this;
		}

	}

}
