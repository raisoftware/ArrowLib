package Arrows;

public interface Diagram
{
	void addArrow2ObjectRule();

	//	reference( nick : Enum, domain : Arrow ) : Reference
	void addClass2ObjectRule();

	void addObjectRegistrarRule();

	Arrows arrows();

	Objects objects();

	<K, V> Set2<K, V> set2( K source, EditableArrow<K, V> arrow );

}
