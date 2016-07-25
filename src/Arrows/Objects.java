package Arrows;

import java.util.Iterator;

public interface Objects extends Set2
{
	public static enum StandardObjectName
	{
		Unnamed
	};

	ObjectConfigBuilder create();

	void addNewObject( Object object, ObjectConfig objectConfig ) throws Exception;

	void add( Object object, ObjectConfig config );

	void remove( Object object, boolean cascade );

	Iterator iterator( Class clazz );
}
