package Arrows.Impl;

import Shared.Collection0.BasicSet0;
import Shared.Collection0.Set0;
import Arrows.*;
import Arrows.Utils.ArrowUtils;
import java.util.*;

public class UnionArrow implements ArrowView
{
	private Diagram diagram;
	private List<ArrowView> arrows = new ArrayList<>();

	private ArrowView inverseArrow = new InverseUnionArrow();
	private int id = ID_NOT_SET;

	public UnionArrow( Diagram diagram, ArrowView... arrows ) throws IllegalArgumentException
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
		Class domain = inverse ? codomain() : domain();

		Set0 unionSources = new BasicSet0( new HashSet<>(), domain );
		for( ArrowView arrowInList : arrows )
		{
			ArrowView arrow = arrowInList;
			if( inverse )
			{
				arrow = arrowInList.inverse();
			}
			unionSources.addAll( arrow.sources() );
		}
		return unionSources;
	}

	private Set0 unionTargets( boolean inverse )
	{
		Class domain = inverse ? domain() : codomain();

		Set0 unionTargets = new BasicSet0( new HashSet<>(), domain );
		for( ArrowView arrowInList : arrows )
		{
			ArrowView arrow = arrowInList;
			if( inverse )
			{
				arrow = arrowInList.inverse();
			}
			unionTargets.addAll( arrow.targets() );
		}
		return unionTargets;
	}

	private Set0 unionTargets( Object source, boolean inverse )
	{
		Class domain = inverse ? domain() : codomain();

		Set0 unionTargets = new BasicSet0( new HashSet<>(), domain );
		for( ArrowView arrowInList : arrows )
		{
			ArrowView arrow = arrowInList;
			if( inverse )
			{
				arrow = arrowInList.inverse();
			}
			unionTargets.addAll( arrow.targets( source ) );
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
	public Object target( Object source )
	{
		return ArrowUtils.target( this, source );
	}

	@Override
	public Class codomain()
	{
		return arrows.get( 0 ).codomain();
	}

	@Override
	public Class domain()
	{
		return arrows.get( 0 ).domain();
	}

	@Override
	public int id()
	{
		return id;
	}

	@Override
	public void id( int id )
	{
		this.id = id;
	}


	private final class InverseUnionArrow implements ArrowView
	{
		private int id = ID_NOT_SET;

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
		public Object target( Object source )
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

		@Override
		public Class codomain()
		{
			return UnionArrow.this.domain();
		}

		@Override
		public Class domain()
		{
			return UnionArrow.this.codomain();
		}

		@Override
		public int id()
		{
			return id;
		}

		@Override
		public void id( int id )
		{
			this.id = id;
		}


	}

}
