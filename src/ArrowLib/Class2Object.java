package ArrowLib;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import java.util.*;

public class Class2Object implements Arrow
{
	private final SetMultimap<Class, Object> class2SrcObj = HashMultimap.create();

	@Override
	public ArrowConfig config()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void connect( Object source, Collection targets )
	{
		if( !targets.isEmpty() )
		{
			class2SrcObj.put( source.getClass(), source );
		}
	}

	@Override
	public void connect( Object source, Object target )
	{
		class2SrcObj.put( source.getClass(), source );
	}

	@Override
	public Set relations()
	{
		return class2SrcObj.entries();
	}

	@Override
	public void remove( Object source, Object target )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set2 sources()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set2 targets()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set2 targets( Object source )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set inverseRelations()
	{
		return null;
	}

	@Override
	public Arrow inverse()
	{
		return null;
	}

}
