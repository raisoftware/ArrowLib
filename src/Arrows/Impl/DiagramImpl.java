package Arrows.Impl;

import Arrows.*;
import Arrows.Impl.Rule.*;
import Shared.MethodBus.Sequence.MethodSequence;
import Shared.MethodBus.Sequence.MethodSequence.ExecutionTime;

import static Arrows.Arrows.StandardArrowName.*;

public class DiagramImpl implements Diagram
{
	Arrows arrows;
	Objects objects;
	MethodSequence<Arrow, ArrowListener> sequence;

	public DiagramImpl()
	{
		sequence = new MethodSequence();
		arrows = new ArrowsImpl( sequence );

		ArrowBuilder class2objectBuilder = arrows.create( Class2Object, Object2Class );
		class2objectBuilder.domain( Enum.class ).codomain( Arrow.class ).listenable( false ).end();

		ArrowBuilder name2objectBuilder = arrows.create( Name2Object, Object2Name );
		name2objectBuilder.domain( Enum.class ).codomain( Object.class ).listenable( false ).end();

		ArrowBuilder object2configBuilder = arrows.create( Object2Config, Config2Object );
		object2configBuilder.domain( Object.class ).codomain( ObjectConfig.class ).listenable( false ).end();

		ArrowBuilder inboundArrowsBuilder = arrows.create( InboundArrow2Object, Object2InboundArrow );
		inboundArrowsBuilder.domain( Arrow.class ).codomain( Object.class ).listenable( false ).end();

		ArrowBuilder outboundArrowsBuilder = arrows.create( OutboundArrow2Object, Object2OutboundArrow );
		outboundArrowsBuilder.domain( Arrow.class ).codomain( Object.class ).listenable( false ).end();

		objects = new ObjectsImpl( arrows );

	}

	@Override
	public final Arrows arrows()
	{
		return arrows;
	}

	@Override
	public Objects objects()
	{
		return objects;
	}

//	reference( nick : Enum, domain : Arrow ) : Reference
//	Set2 set2( Object source, arrow : Arrow ) :
	@Override
	public void addClass2ObjectHook()
	{
		Class2ObjectHook class2ObjectHook = new Class2ObjectHook( arrows );
		sequence.subscribe( class2ObjectHook, ExecutionTime.ExecuteBefore );
	}

	@Override
	public void addObjectRegistrarHook()
	{
		ObjectRegistrarHook objectRegistrarHook = new ObjectRegistrarHook( arrows, objects );
		sequence.subscribe( objectRegistrarHook, ExecutionTime.ExecuteBefore );
	}

	@Override
	public void addArrow2ObjectHook()
	{
		Arrow2ObjectHook arrow2ObjectHook = new Arrow2ObjectHook( arrows, objects );
		sequence.subscribe( arrow2ObjectHook, ExecutionTime.ExecuteBefore );
	}

	@Override
	public <K, V> Set2<K, V> set2( K source, Arrow<K, V> arrow )
	{
		return new Set2Impl( source, arrow );
	}
}
