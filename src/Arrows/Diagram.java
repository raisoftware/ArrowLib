package Arrows;

public interface Diagram
{
	void addArrow2ObjectHook();

	//	reference( nick : Enum, domain : Arrow ) : Reference
	void addClass2ObjectHook();

	void addObjectRegistrarHook();

	Arrows arrows();

	Objects objects();

	<K, V> Set2<K, V> set2( K source, Arrow<K, V> arrow );

}
