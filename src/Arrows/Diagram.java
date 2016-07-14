package Arrows;

import Arrows.Impl.*;

import static Arrows.Arrows.StandardArrowName.*;

public class Diagram
{
	Arrows arrows;
	Objects objects;

	public Diagram()
	{
		arrows = new Arrows();
		arrows().add( Class2Object, Object2Class, new ManyToManyArrow( arrows.defaultArrowConfig() ) );
		arrows().add( Name2Object, Object2Name, new ManyToManyArrow( arrows.defaultArrowConfig() ) );
		arrows().add( Object2Config, Config2Object, new ManyToManyArrow( arrows.defaultArrowConfig() ) );

		objects = new ObjectsImpl( arrows );
	}

	public final Arrows arrows()
	{
		return arrows;
	}

	public Objects objects()
	{
		return objects;
	}

//	reference( nick : Enum, domain : Arrow ) : Reference
//	Set2 set2( Object source, arrow : Arrow ) :
	public Class2ObjectRule class2ObjectRule()
	{
		return new Class2ObjectRule( arrows );
	}

	public ObjectRegistrarRule objectRegistrarRule()
	{

		return new ObjectRegistrarRule( objects );
	}
}
