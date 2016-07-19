package Arrows.Impl;

import Arrows.Arrow;
import Arrows.ArrowConfig;
import java.util.*;


public class IntersectArrow implements Arrow
{
	List<Arrow> arrows = new ArrayList<>();

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
		Iterator<Arrow> arrowIt = arrows.iterator();
		Set intersectSources = new HashSet<>();

		Arrow firstArrow = arrowIt.next();
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

	@Override
	public Set targets()
	{
		Set intersectTargets = new HashSet<>();

		Iterator<Arrow> arrowIt = arrows.iterator();
		Arrow firstArrow = arrowIt.next();
		Set firstArrowSources = firstArrow.sources();


		for( Object source : firstArrowSources )
		{
			intersectTargets.addAll( targets( source ) );
		}
		return intersectTargets;
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
		Iterator<Arrow> arrowIt = arrows.iterator();
		Arrow firstArrow = arrowIt.next();


		Set intersectTargets = new HashSet<>();
		intersectTargets.addAll( firstArrow.targets( source ) );

		while( arrowIt.hasNext() )
		{
			Arrow arrow = arrowIt.next();
			intersectTargets.retainAll( arrow.targets( source ) );
		}

		return intersectTargets;
	}

	@Override
	public Object target( Object source ) throws Exception
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

}
