package Arrows;

import Arrows.Impl.*;
import Shared.MethodBus.Sequence.MethodSequence;
import Shared.MethodBus.Sequence.MethodSequence.ExecutionTime;

import static Arrows.Arrows.StandardArrowName.*;

public class Diagram
{
	Arrows arrows;
	Objects objects;
	MethodSequence<Arrow> sequence;

	public Diagram()
	{
		sequence = new MethodSequence();
		arrows = new Arrows( sequence );
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
	public void addClass2ObjectRule()
	{
		Class2ObjectRule class2ObjectRule = new Class2ObjectRule( arrows );
		sequence.subscribe( class2ObjectRule, ExecutionTime.ExecuteBefore );
	}

	public void objectRegistrarRule()
	{
		ObjectRegistrarRule objectRegistrarRule = new ObjectRegistrarRule( objects );
		sequence.subscribe( objectRegistrarRule, ExecutionTime.ExecuteBefore );
	}
}
