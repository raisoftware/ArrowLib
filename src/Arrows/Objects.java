package Arrows;

import Arrows.ObjectConfig;

public interface Objects {

	void register( Object object );

	void register( Object object, ObjectConfig config );

	void remove( Object obj );
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
