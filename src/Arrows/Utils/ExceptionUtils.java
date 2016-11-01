package Arrows.Utils;


public class ExceptionUtils
{
	private ExceptionUtils()
	{
	}

	public static RuntimeException targetsNumberException( int number )
	{
		return new RuntimeException( "Number of targets is " + number + ". There should only be one target." );
	}

	public static RuntimeException multipleSourcesTargetsException( Object sources, Object targets )
	{
		return new RuntimeException( "Arrow does not allow multiple sources or does not allow multiple targets! source/sources:" + sources + "  target/targets: " + targets );
	}

	public static void checkInsertPoint( int index )
	{
		if( index < 0 )
			throw new RuntimeException( "InsertPoint not found in list." );
	}
}
