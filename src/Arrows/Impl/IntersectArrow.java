package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ArrowUtils;
import java.util.*;


public class IntersectArrow implements ArrowView
{
	List<Arrow> arrows = new ArrayList<>();

	InverseIntersectArrow inverseArrow = new InverseIntersectArrow();

	public IntersectArrow( Arrow... arrows ) throws IllegalArgumentException
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
	public Set relations()
	{
		return ArrowUtils.generateRelations( this );
	}



	@Override
	public Set sources()
	{
		return intersectSources( false );
	}

	@Override
	public Set targets()
	{
		return intersectTargets( false );
	}

	@Override
	public ArrowView inverse()
	{
		return inverseArrow;
	}

	@Override
	public Set targets( Object source )
	{
		return intersectTargets( source, false );
	}

	@Override
	public Object target( Object source ) throws Exception
	{
		return ArrowUtils.target( this, source );
	}

	private Set intersectSources( boolean inverse )
	{
		Iterator<Arrow> arrowIt = arrows.iterator();
		Set intersectSources = new HashSet<>();

		Arrow firstArrow = arrowIt.next();
		if( inverse )
		{
			firstArrow = firstArrow.inverse();
		}
		Set firstArrowSources = firstArrow.sources();


		for( Object source : firstArrowSources )
		{
			for( Object target : firstArrow.targets( source ) )
			{
				boolean relationExistsInAllArrows = true;
				if( arrows.iterator().hasNext() )
				{
					Iterator<Arrow> otherArrows = arrows.iterator();
					otherArrows.next();
					while( otherArrows.hasNext() )
					{
						Arrow arrow = otherArrows.next();
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

	private Set intersectTargets( boolean inverse )
	{
		Set intersectTargets = new HashSet<>();

		Iterator<Arrow> arrowIt = arrows.iterator();
		Arrow firstArrow = arrowIt.next();
		if( inverse )
		{
			firstArrow = firstArrow.inverse();
		}
		Set firstArrowSources = firstArrow.sources();

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

	private Set intersectTargets( Object source, boolean inverse )
	{
		Iterator<Arrow> arrowIt = arrows.iterator();
		Arrow firstArrow = arrowIt.next();
		if( inverse )
		{
			firstArrow = firstArrow.inverse();
		}

		Set intersectTargets = new HashSet<>();
		intersectTargets.addAll( firstArrow.targets( source ) );

		while( arrowIt.hasNext() )
		{
			Arrow arrow = arrowIt.next();
			if( inverse )
			{
				arrow = arrow.inverse();
			}
			intersectTargets.retainAll( arrow.targets( source ) );
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

	private final class InverseIntersectArrow implements ArrowView
	{
		@Override
		public Set sources()
		{
			return intersectSources( true );
		}

		@Override
		public Set targets()
		{
			return intersectTargets( true );
		}

		@Override
		public Object target( Object source ) throws Exception
		{
			return ArrowUtils.target( this, source );
		}

		@Override
		public Set targets( Object source )
		{
			return intersectTargets( source, true );
		}

		@Override
		public Set relations()
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
			for( Arrow arrow : arrows )
			{
				stringBuilder.append( arrow.inverse().toString() );
			}
			stringBuilder.append( " Relations:" );
			stringBuilder.append( relations() );
			return stringBuilder.toString();
		}

	}

}
