package Arrows;

import Arrows.Impl.ObjectsImpl;

public class Diagram
{
	Arrows arrows = new Arrows();
	Objects objects = new ObjectsImpl( null );//TOFIX

	public Diagram()
	{

	}

	public Arrows arrows()
	{
		return arrows;
	}

	public Objects objects()
	{
		return objects;
	}
}
