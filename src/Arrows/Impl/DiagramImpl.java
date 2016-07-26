package Arrows.Impl;

import Arrows.*;

import static Arrows.Arrows.StandardArrowName.*;

public class DiagramImpl implements Diagram
{
	ArrowsImpl arrows;
	Objects objects;

	public DiagramImpl()
	{
		arrows = new ArrowsImpl();


		ArrowBuilder class2objectBuilder = arrows.create( Class2Object, Object2Class );
		class2objectBuilder.domain( Class.class ).codomain( Object.class ).listenable( false ).end();

		ArrowBuilder name2objectBuilder = arrows.create( Name2Object, Object2Name );
		name2objectBuilder.domain( Enum.class ).codomain( Object.class ).listenable( false ).end();

		ArrowBuilder object2configBuilder = arrows.create( Object2Config, Config2Object );
		object2configBuilder.domain( Object.class ).codomain( ObjectConfig.class ).listenable( false ).end();

		ArrowBuilder inboundArrowsBuilder = arrows.create( InboundArrow2Object, Object2InboundArrow );
		inboundArrowsBuilder.domain( Arrow.class ).codomain( Object.class ).listenable( false ).end();

		ArrowBuilder outboundArrowsBuilder = arrows.create( OutboundArrow2Object, Object2OutboundArrow );
		outboundArrowsBuilder.domain( Arrow.class ).codomain( Object.class ).listenable( false ).end();

		//TOFIX fix this ugly stuff
		objects = new ObjectsImpl( arrows );
		arrows.objects( objects );


//		addClass2ObjectRule();
//		addObjectRegistrarRule();
//		addArrow2ObjectRule();
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

	@Override
	public <K, V> Set0<V> set0( K source, Arrow<K, V> arrow )
	{
		return new Set0Impl( source, arrow );
	}

//	private void addClass2ObjectRule()
//	{
//		Class2ObjectRule class2ObjectRule = new Class2ObjectRule( arrows );
//		arrows.subscribe( class2ObjectRule );
//	}
//
//	private void addObjectRegistrarRule()
//	{
//		ObjectRegistrarRule objectRegistrarRule = new ObjectRegistrarRule( arrows, objects );
//		arrows.subscribe( objectRegistrarRule );
//	}
//
//	private void addArrow2ObjectRule()
//	{
//		Arrow2ObjectRule arrow2ObjectRule = new Arrow2ObjectRule( arrows );
//		arrows.subscribe( arrow2ObjectRule );
//	}

}
