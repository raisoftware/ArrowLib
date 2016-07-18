package Arrows;

import java.util.Iterator;

public interface Objects// extends Set2
{
	public static enum StandardObjectName
	{
		Unnamed
	};

	ObjectConfigBuilder create();

	void addNewObject( Object object, ObjectConfig objectConfig ) throws Exception;

	//@Override
	void add( Object object );

	void add( Object object, ObjectConfig config );

	//@Override
	boolean contains( Object object );

	//@Override
	void remove( Object object, boolean cascade );

	Iterator objects( Class clazz );

	Iterator objects();

}
