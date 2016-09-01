package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ArrowUtils;
import Shared.*;
import java.util.*;

public class JoinArrow implements ArrowView
{
	private final ArrowDiagram diagram;

	private final List<ArrowView> arrows = new ArrayList<>();
	private final List<ArrowView> arrowsInverse = new LinkedList<>();

	private final ArrowView inverseArrow = new InverseJoinArrow();


	public JoinArrow( ArrowDiagram diagram, ArrowView... arrows ) throws IllegalArgumentException
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

		if( !arrows.isEmpty() )
		{
			ArrowView last = arrows.get( arrows.size() - 1 );
			if( !last.codomain().equals( arrow.domain() ) )
				throw new IllegalArgumentException( "Domain type of the arrow does not match codomain type of the last arrow" );
		}

		arrows.add( arrow );
		arrowsInverse.add( 0, arrow.inverse() );
	}

	public List<ArrowView> arrows()
	{
		return arrows;
	}

	@Override
	public Set0<Map.Entry> relations()
	{
		return ArrowUtils.generateRelations( this );
	}

	@Override
	public Set0 sources()
	{
		return arrows.get( 0 ).sources();
	}

	@Override
	public Set0 targets()
	{
		return arrows.get( arrows.size() - 1 ).targets();
	}

	@Override
	public ArrowView inverse()
	{
		return inverseArrow;
	}

	@Override
	public Set0 targets( Object source )
	{
		return targets( arrows, source );
	}

	@Override
	public Object target( Object source ) throws Exception
	{
		return ArrowUtils.target( this, source );
	}

//	@Override
//	public String toString()
//	{
//		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append( "JoinArrow:" );
//		stringBuilder.append( arrows.toString() );
//		stringBuilder.append( " Relations:" );
//		stringBuilder.append( relations() );
//		return stringBuilder.toString();
//	}

	@Override
	public Class codomain()
	{
		return arrows.get( arrowsInverse.size() - 1 ).codomain();
	}

	@Override
	public Class domain()
	{
		return arrows.get( 0 ).domain();
	}

	private final class InverseJoinArrow implements ArrowView
	{
		@Override
		public Set0 sources()
		{
			return arrowsInverse.get( 0 ).sources();
		}

		@Override
		public Set0 targets()
		{
			return arrowsInverse.get( arrowsInverse.size() - 1 ).targets();
		}

		@Override
		public Object target( Object source ) throws Exception
		{
			return ArrowUtils.target( this, source );
		}

		@Override
		public Set0 targets( Object source )
		{
			return JoinArrow.targets( arrowsInverse, source );
		}

		@Override
		public Set0 relations()
		{
			return ArrowUtils.generateRelations( this );
		}

		@Override
		public ArrowView inverse()
		{
			return JoinArrow.this;
		}

//		@Override
//		public String toString()
//		{
//			StringBuilder stringBuilder = new StringBuilder();
//			stringBuilder.append( "JoinArrow:" );
//			stringBuilder.append( arrowsInverse.toString() );
//			stringBuilder.append( " Relations:" );
//			stringBuilder.append( relations() );
//			return stringBuilder.toString();
//		}

		@Override
		public Class codomain()
		{
			return JoinArrow.this.domain();
		}

		@Override
		public Class domain()
		{
			return JoinArrow.this.codomain();
		}
	}


	private static Set0 targets( List<ArrowView> arrowsList, Object source )
	{
		List<Object> oldResults = new ArrayList<>();

		List<Object> newResults = new ArrayList<>();
		newResults.add( source );

		Iterator<ArrowView> arrowIt = arrowsList.iterator();

		while( arrowIt.hasNext() )
		{
			List<Object> aux = oldResults;
			oldResults = newResults;
			newResults = aux;
			newResults.clear();

			ArrowView arrow = arrowIt.next();

			for( Object input : oldResults )
			{
				for( Object target : arrow.targets( input ) )
				{
					newResults.add( target );
				}
			}
		}

		Set0 results = new BasicSet0( new HashSet( newResults ) );
		return results;
	}
}
