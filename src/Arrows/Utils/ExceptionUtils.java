package Arrows.Utils;


public class ExceptionUtils
{
	public static Exception targetsNumberException( int number )
	{
		return new Exception( "Number of targets is " + number + ". There should only be one target." );
	}
}
