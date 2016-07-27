package Arrows;

import Shared.Set0;
import java.util.Iterator;

public interface Objects extends Set0
{
	void remove( Object obj, boolean cascade );

	Iterator iterator( Class clazz );

	void config( Object object, ObjectConfig config );

	ObjectConfig config( Object object );

	void name( Object obj, Object name );
}
