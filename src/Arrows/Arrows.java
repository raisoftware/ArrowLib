package Arrows;

import Arrows.Impl.ManyToManyArrow;

public class Arrows
{
	public static enum StandardArrowName
	{
		Class2Object, Object2Class, Object2Arrow, Arrow2Object
	}

	Arrow<Enum, Arrow> arrows = new ManyToManyArrow();

	public Arrows()
	{

	}

	public ArrowBuilder create()
	{
		return new ArrowBuilder( this );
	}

	public void add( Enum arrowName, Enum arrowInverseName, Arrow arrow )
	{
		arrows.connect( arrowName, arrow );
		arrows.connect( arrowInverseName, arrow.inverse() );
	}

	public Arrow<Enum, Arrow> arrow( Enum arrowName ) throws Exception
	{
		return arrows.target( arrowName );
	}

}
