package Arrows;

import Arrows.Impl.Rule.ObjectRegistrarRule;
import Arrows.Impl.Rule.Class2ObjectRule;
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

		ArrowBuilder class2objectBuilder = arrows.create( Class2Object, Object2Class );
		class2objectBuilder.domain( Enum.class ).codomain( Arrow.class ).canBeListenedTo( false ).end();

		ArrowBuilder name2objectBuilder = arrows.create( Name2Object, Object2Name );
		name2objectBuilder.domain( Enum.class ).codomain( Object.class ).canBeListenedTo( false ).end();

		ArrowBuilder object2configBuilder = arrows.create( Object2Config, Config2Object );
		object2configBuilder.domain( Object.class ).codomain( ObjectConfig.class ).canBeListenedTo( false ).end();

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
