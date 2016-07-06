package Arrows;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import java.util.*;

public class Class2Object implements Arrow
{
	private final SetMultimap<Class, Object> class2Obj = HashMultimap.create();

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
			class2Obj.put( source.getClass(), source );
		}

		for( Object target : targets )
		{
			class2Obj.put( target.getClass(), target );
		}
	}

	@Override
	public void connect( Object source, Object target )
	{
		class2Obj.put( source.getClass(), source );
		class2Obj.put( target.getClass(), target );
	}

	@Override
	public Set relations()
	{
		return class2Obj.entries();
	}

	@Override
	public void remove( Object source, Object target )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set sources()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set targets()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Arrow inverse()
	{
		return null;
	}

	@Override
	public Set eval( Object source )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

}
