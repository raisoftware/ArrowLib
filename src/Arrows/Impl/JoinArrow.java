package Arrows.Impl;

import Arrows.Arrow;
import Arrows.ArrowConfig;
import java.util.*;

public class JoinArrow implements Arrow
{
	List<Arrow> arrows = new ArrayList<>();

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
	}

	@Override
	public ArrowConfig config()
	{
		//might need to get one as parameter on creation
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void connect( Object source, Collection targets )
	{
		//actually unsupported
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void connect( Object source, Object target )
	{
		//actually unsupported
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set relations()
	{
		//expensive
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void remove( Object source, Object target )
	{
		//dfs sau bfs. pretty expensive but needed
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
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
		// this one might be easy
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set targets( Object source )
	{
		List<Object> oldResults = new ArrayList<>();

		List<Object> newResults = new ArrayList<>();
		newResults.add( source );

		Iterator<Arrow> arrowIt = arrows.iterator();

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
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

}
