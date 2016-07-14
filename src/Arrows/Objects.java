package Arrows;

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
	void remove( Object object );
	//		// query
	//		Iterator objects()
	//		{
	//			return arrow.targets();
	//		}
	//
	//		Iterator objects( Class clazz ) // return classes.eval( clazz )
	//		{
	//			classes.eval( clazz );
	//		}

}
