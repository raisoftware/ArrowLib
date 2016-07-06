package Arrows.Impl;

import Arrows.ObjectConfig;
import Arrows.ArrowName;
import Arrows.Arrow;
import Arrows.ArrowBuilder;
import Arrows.Objects;

public class ObjectsImpl implements Objects
{

	Arrow objects2Arrows;//  = arrows[Objects] // edit

	public ObjectsImpl()
	{
		objects2Arrows = new ArrowBuilder().name( ArrowName.Objects2Arrows ).end();
	}

	@Override
	public void register( Object object ) // obtains class config from ClassConfig arrow then calls register
	{

	}

	@Override
	public void register( Object object, ObjectConfig config ) //    objectsArrow.connect( name, value )
	{
		objects2Arrows.connect( object, null );
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
//		Object[] objects( Class clazz ) // return classes.eval( clazz )
//		{
//			classes.eval( clazz );
//		}
}
