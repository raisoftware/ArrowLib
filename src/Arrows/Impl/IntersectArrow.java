package Arrows.Impl;

import Shared.Collection0.Sets;
import Shared.Collection0.BasicSet0;
import Shared.Collection0.Set0;
import Arrows.*;
import Arrows.Utils.ArrowUtils;
import java.util.*;


public class IntersectArrow implements ArrowView
{
	private final Diagram diagram;

	private List<ArrowView> arrows = new ArrayList<>();

	private InverseIntersectArrow inverseArrow = new InverseIntersectArrow();
	private int id = ID_NOT_SET;

	public IntersectArrow( Diagram diagram, ArrowView... arrows ) throws IllegalArgumentException
	{
		this.diagram = diagram;

		if( arrows == null || arrows.length == 0 )
			throw new IllegalArgumentException( "Empty arrow list" );

		for( int i = 0; i < arrows.length; ++i )
		{
			addArrow( arrows[i] );
		}
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



	@Override
	public Set0 sources()
	{
		return intersectSources( false );
	}

	@Override
	public Set0 targets()
	{
		return intersectTargets( false );
	}

	@Override
	public ArrowView inverse()
	{
		return inverseArrow;
	}

	@Override
	public Set0 targets( Object source )
	{
		return intersectTargets( source, false );
	}

	@Override
	public Object target( Object source )
	{
		return ArrowUtils.target( this, source );
	}

	private Set0 intersectSources( boolean inverse )
	{
		Iterator<ArrowView> arrowIt = arrows.iterator();
		Set0 intersectSources = new BasicSet0( new HashSet<>(), domain() );

		ArrowView firstArrow = arrowIt.next();
		if( inverse )
		{
			firstArrow = firstArrow.inverse();
		}
		Set0 firstArrowSources = firstArrow.sources();


		for( Object source : firstArrowSources )
		{
			for( Object target : firstArrow.targets( source ) )
			{
				boolean relationExistsInAllArrows = true;
				if( arrows.iterator().hasNext() )
				{
					Iterator<ArrowView> otherArrows = arrows.iterator();
					otherArrows.next();
					while( otherArrows.hasNext() )
					{
						ArrowView arrow = otherArrows.next();
						if( inverse )
						{
							arrow = arrow.inverse();
						}
						if( !arrow.targets( source ).contains( target ) )
						{
							relationExistsInAllArrows = false;
							break;
						}
					}
					if( relationExistsInAllArrows )
					{
						intersectSources.add( source );
						break;
					}
				}
			}
		}
		return intersectSources;
	}

	private Set0 intersectTargets( boolean inverse )
	{
		Set0 intersectTargets = new BasicSet0( new HashSet<>(), codomain() );

		Iterator<ArrowView> arrowIt = arrows.iterator();
		ArrowView firstArrow = arrowIt.next();
		if( inverse )
		{
			firstArrow = firstArrow.inverse();
		}
		Set0 firstArrowSources = firstArrow.sources();

		ArrowView arrow = this;
		if( inverse )
		{
			arrow = inverseArrow;
		}

		for( Object source : firstArrowSources )
		{
			intersectTargets.addAll( arrow.targets( source ) );
		}
		return intersectTargets;
	}

	private Set0 intersectTargets( Object source, boolean inverse )
	{
		Iterator<ArrowView> arrowIt = arrows.iterator();
		ArrowView firstArrow = arrowIt.next();
		if( inverse )
		{
			firstArrow = firstArrow.inverse();
		}

		Set0 intersectTargets = new BasicSet0( new HashSet<>(), codomain() );
		intersectTargets.addAll( firstArrow.targets( source ) );

		while( arrowIt.hasNext() )
		{
			ArrowView arrow = arrowIt.next();
			if( inverse )
			{
				arrow = arrow.inverse();
			}
			Sets.retainAll( intersectTargets, arrow.targets( source ) );
		}

		return intersectTargets;
	}

	@Override
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "IntersectArrow:" );
		stringBuilder.append( arrows.toString() );
		stringBuilder.append( " Relations:" );
		stringBuilder.append( relations() );
		return stringBuilder.toString();
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

	private final class InverseIntersectArrow implements ArrowView
	{
		private int id = ID_NOT_SET;

		@Override
		public Set0 sources()
		{
			return intersectSources( true );
		}

		@Override
		public Set0 targets()
		{
			return intersectTargets( true );
		}

		@Override
		public Object target( Object source )
		{
			return ArrowUtils.target( this, source );
		}

		@Override
		public Set0 targets( Object source )
		{
			return intersectTargets( source, true );
		}

		@Override
		public Set0 relations()
		{
			return ArrowUtils.generateRelations( this );
		}

		@Override
		public ArrowView inverse()
		{
			return IntersectArrow.this;
		}

		@Override
		public String toString()
		{
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append( "InverseIntersectArrow:" );
			for( ArrowView arrow : arrows )
			{
				stringBuilder.append( arrow.inverse().toString() );
			}
			stringBuilder.append( " Relations:" );
			stringBuilder.append( relations() );
			return stringBuilder.toString();
		}

		@Override
		public Class codomain()
		{
			return IntersectArrow.this.domain();
		}

		@Override
		public Class domain()
		{
			return IntersectArrow.this.codomain();
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
