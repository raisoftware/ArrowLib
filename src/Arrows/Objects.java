package Arrows;

import java.util.Iterator;

public interface Objects extends Set0
{
	void remove( Object obj, boolean cascade );

	Iterator iterator( Class clazz );

	void config( Object object, ObjectConfig config );

	ObjectConfig config( Object object );
}
