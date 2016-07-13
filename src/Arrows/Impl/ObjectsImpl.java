package Arrows.Impl;

import Arrows.*;

public class ObjectsImpl implements Objects
{

	Arrow object2Arrow;//  = arrows[Objects] // edit

	public ObjectsImpl( Arrow object2Arrow )
	{
		this.object2Arrow = object2Arrow;
	}

	@Override
	public void add( Object object ) // obtains class config from ClassConfig arrow then calls add
	{

	}

	@Override
	public void add( Object object, ObjectConfig config ) //    objectsArrow.connect( name, value )
	{
		object2Arrow.connect( object, null );
	}

	@Override
	public void remove( Object obj ) // remove object and all the standard relations involving it. if arrows are tracked, also delete the relations it is involved
	{

	}

//		// query
//		Set objects()
//		{
//			return arrow.targets();
//		}
//
//		Object[] objects( Class clazz ) // return classes.targets( clazz )
//		{
//			classes.targets( clazz );
//		}
	@Override
	public boolean contains( Object object )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int size()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}
}
