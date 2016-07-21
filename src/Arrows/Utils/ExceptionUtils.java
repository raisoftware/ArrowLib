package Arrows.Utils;


public class ExceptionUtils
{
	public static Exception targetsNumberException( int number )
	{
		return new Exception( "Number of targets is " + number + ". There should only be one target." );
	}

	public static RuntimeException multipleSourcesTargetsException( Object sources, Object targets )
	{
		return new RuntimeException( "Arrow does not allow multiple sources or does not allow multiple targets! source/sources:" + sources + "  target/targets: " + targets );
	}
}
