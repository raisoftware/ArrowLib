package Arrows.Impl;

import Arrows.*;
import Shared.Set0;

public class DiagramImpl implements Diagram
{
	ArrowsImpl arrows;
	Objects objects;

	public DiagramImpl()
	{
		arrows = new ArrowsImpl( this );


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
