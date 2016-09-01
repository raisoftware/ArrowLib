package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ArrowUtils;
import Shared.*;
import java.util.*;

public class UnionArrow implements ArrowView
{
	private ArrowDiagram diagram;
	private List<ArrowView> arrows = new ArrayList<>();

	private ArrowView inverseArrow = new InverseUnionArrow();

	public UnionArrow( ArrowDiagram diagram, ArrowView... arrows ) throws IllegalArgumentException
	{
		if( arrows == null || arrows.length == 0 )
			throw new IllegalArgumentException( "Empty arrow list" );

		for( int i = 0; i < arrows.length; ++i )
		{
			addArrow( arrows[i] );
		}
		this.diagram = diagram;
	}

	public final void addArrow( ArrowView arrow ) throws IllegalArgumentException
	{
		if( arrow == null )
			throw new IllegalArgumentException( "Arrow is null" );

		arrows.add( arrow );
	}

	@Override
	public Set0 relations()
	{
		return ArrowUtils.generateRelations( this );
	}

	private Set0 unionSources( boolean inverse )
	{
		Set0 unionSources = new BasicSet0( new HashSet<>() );
		for( ArrowView arrowInList : arrows )
		{
			ArrowView arrow = arrowInList;
			if( inverse )
			{
				arrow = arrowInList.inverse();
			}
			Set0Utils.addAll( unionSources, arrow.sources() );
		}
		return unionSources;
	}

	private Set0 unionTargets( boolean inverse )
	{
		Set0 unionTargets = new BasicSet0( new HashSet<>() );
		for( ArrowView arrowInList : arrows )
		{
			ArrowView arrow = arrowInList;
			if( inverse )
			{
				arrow = arrowInList.inverse();
			}
			Set0Utils.addAll( unionTargets, arrow.targets() );
		}
		return unionTargets;
	}

	private Set0 unionTargets( Object source, boolean inverse )
	{
		Set0 unionTargets = new BasicSet0( new HashSet<>() );
		for( ArrowView arrowInList : arrows )
		{
			ArrowView arrow = arrowInList;
			if( inverse )
			{
				arrow = arrowInList.inverse();
			}
			Set0Utils.addAll( unionTargets, arrow.targets( source ) );
		}
		return unionTargets;
	}


	@Override
	public Set0 sources()
	{
		return unionSources( false );
	}

	@Override
	public Set0 targets()
	{
		return unionTargets( false );
	}

	@Override
	public ArrowView inverse()
	{
		return inverseArrow;
	}

	@Override
	public Set0 targets( Object source )
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
		public Set0 sources()
		{
			return unionSources( true );
		}

		@Override
		public Set0 targets()
		{
			return unionTargets( true );
		}

		@Override
		public Object target( Object source ) throws Exception
		{
			return ArrowUtils.target( this, source );
		}

		@Override
		public Set0 targets( Object source )
		{
			return unionTargets( source, true );
		}

		@Override
		public Set0 relations()
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
