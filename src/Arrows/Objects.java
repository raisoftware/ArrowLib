package Arrows;

public interface Objects extends Set2
{

	@Override
	void add( Object object );

	void add( Object object, ObjectConfig config );

	@Override
	boolean contains( Object object );

	@Override
	void remove( Object obj );
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
