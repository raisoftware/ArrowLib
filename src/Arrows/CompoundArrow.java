package Arrows;

import java.util.*;

public class CompoundArrow implements Arrow
{
	List<Arrow> arrows = new ArrayList<>();

	public CompoundArrow( List<Arrow> arrows ) throws Exception
	{
		if( arrows == null || arrows.isEmpty() )
			throw new IllegalArgumentException( "Empty arrow list" );

		for( Arrow arrow : arrows )
		{
			addArrow( arrow );
		}
	}

	public final void addArrow( Arrow arrow ) throws Exception
	{
		if( arrow == null )
			throw new IllegalArgumentException( "Arrow is null" );

		if( !arrows.isEmpty() )
		{
			Arrow last = arrows.get( arrows.size() - 1 );
			if( !last.config().getCodomain().equals( arrow.config().getDomain() ) )
				throw new Exception( "Domain type of the arrow does not match codomain type of the last arrow" );
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
	public Set eval( Object source )
	{
		return null;
//		Object input = source;
//		for( Arrow arrow : arrows )
//		{
//			Set<Object> returedValues = arrow.eval( input );
//		}
//		return input;
	}

}
