package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ArrowUtils;
import Arrows.Utils.ExceptionUtils;
import java.util.*;

public class JoinArrow implements Arrow
{
	List<Arrow> arrows = new ArrayList<>();
	List<Arrow> arrowsInverse = new LinkedList<>();

	Arrow inverseArrow = new InverseJoinArrow();
	EditableArrowConfig arrowConfig = new ArrowBuilderImpl();

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
			if( !last.config().codomain().equals( arrow.config().domain() ) )
				throw new IllegalArgumentException( "Domain type of the arrow does not match codomain type of the last arrow" );
		}

		arrows.add( arrow );

		if( arrow.config().invertible() )
		{
			arrowsInverse.add( 0, arrow.inverse() );
		}
		else
		{
			arrowConfig.invertible( false );
		}

	}

	@Override
	public ArrowConfig config()
	{
		Class domain = arrows.get( 0 ).config().domain();
		Class codomain = arrows.get( arrows.size() - 1 ).config().codomain();
		return arrowConfig.domain( domain ).codomain( codomain ).arrowConfig();
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
	public Arrow inverse()
	{
		assert ( arrowConfig.invertible() );
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
		Set targets = targets( source );
		if( targets.size() != 1 )
			throw ExceptionUtils.targetsNumberException( targets.size() );
		return targets.iterator().next();

	}

	private final class InverseJoinArrow implements Arrow
	{

		@Override
		public ArrowConfig config()
		{
			Class codomain = arrows.get( 0 ).config().domain();
			Class domain = arrows.get( arrows.size() - 1 ).config().codomain();
			return arrowConfig.domain( domain ).codomain( codomain ).arrowConfig();
		}

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
			Set targets = targets( source );
			if( targets.size() != 1 )
				throw ExceptionUtils.targetsNumberException( targets.size() );
			return targets.iterator().next();
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
		public Arrow inverse()
		{
			return JoinArrow.this;
		}

	}

}
