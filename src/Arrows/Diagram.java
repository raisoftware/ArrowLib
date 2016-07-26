package Arrows;

import Shared.Set0;
import Arrows.Impl.DiagramImpl;

public interface Diagram
{
	//	reference( nick : Enum, domain : Arrow ) : Reference

	Arrows arrows();

	Objects objects();

	<K, V> Set0<V> set0( K source, Arrow<K, V> arrow );

	static Diagram create()
	{
		return new DiagramImpl();
	}
}
