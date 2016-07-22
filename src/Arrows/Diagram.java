package Arrows;

import Arrows.Impl.DiagramImpl;

public interface Diagram
{
	//	reference( nick : Enum, domain : Arrow ) : Reference

	Arrows arrows();

	Objects objects();

	<K, V> Set2<K, V> set2( K source, EditableArrow<K, V> arrow );

	static Diagram create()
	{
		return new DiagramImpl();
	}
}
