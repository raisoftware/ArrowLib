package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ArrowUtils;
import java.util.*;

public class JoinArrow implements ArrowView
{
	List<Arrow> arrows = new ArrayList<>();
	List<Arrow> arrowsInverse = new LinkedList<>();

	ArrowView inverseArrow = new InverseJoinArrow();

	public JoinArrow( Arrow... arrows ) throws IllegalArgumentException
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

		if( !arrows.isEmpty() )
		{
			Arrow last = arrows.get( arrows.size() - 1 );
			if( !last.codomain().equals( arrow.domain() ) )
				throw new IllegalArgumentException( "Domain type of the arrow does not match codomain type of the last arrow" );
		}

		arrows.add( arrow );
		arrowsInverse.add( 0, arrow.inverse() );
	}

	@Override
	public Set<Map.Entry> relations()
	{
		return ArrowUtils.generateRelations( this );
	}

	@Override
	public Set sources()
	{
		return arrows.get( 0 ).sources();
	}

	@Override
	public Set targets()
	{
		return arrows.get( arrows.size() - 1 ).targets();
	}

	@Override
	public ArrowView inverse()
	{
		return inverseArrow;
	}

	@Override
	public Set targets( Object source )
	{
		return targets( arrows, source );
	}


	private static Set targets( List<Arrow> arrowsList, Object source )
	{
		List<Object> oldResults = new ArrayList<>();

		List<Object> newResults = new ArrayList<>();
		newResults.add( source );

		Iterator<Arrow> arrowIt = arrowsList.iterator();

		while( arrowIt.hasNext() )
		{
			List<Object> aux = oldResults;
			oldResults = newResults;
			newResults = aux;
			newResults.clear();

			Arrow arrow = arrowIt.next();

			for( Object input : oldResults )
			{
				newResults.addAll( arrow.targets( input ) );
			}
		}

		Set results = new HashSet( newResults );
		return results;
	}

	@Override
	public Object target( Object source ) throws Exception
	{
		return ArrowUtils.target( this, source );
	}

	@Override
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append( "JoinArrow:" );
		stringBuilder.append( arrows.toString() );
		stringBuilder.append( " Relations:" );
		stringBuilder.append( relations() );
		return stringBuilder.toString();
	}

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
		public Set sources()
		{
			return arrowsInverse.get( 0 ).sources();
		}

		@Override
		public Set targets()
		{
			return arrowsInverse.get( arrowsInverse.size() - 1 ).targets();
		}

		@Override
		public Object target( Object source ) throws Exception
		{
			return ArrowUtils.target( this, source );
		}

		@Override
		public Set targets( Object source )
		{
			return JoinArrow.targets( arrowsInverse, source );
		}

		@Override
		public Set relations()
		{
			return ArrowUtils.generateRelations( this );
		}

		@Override
		public ArrowView inverse()
		{
			return JoinArrow.this;
		}

		@Override
		public String toString()
		{
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append( "JoinArrow:" );
			stringBuilder.append( arrowsInverse.toString() );
			stringBuilder.append( " Relations:" );
			stringBuilder.append( relations() );
			return stringBuilder.toString();
		}

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

}
