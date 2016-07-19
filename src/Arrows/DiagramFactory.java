package Arrows;

import Arrows.Impl.DiagramImpl;


public class DiagramFactory
{
	public static Diagram create()
	{
		return new DiagramImpl();
	}
}
