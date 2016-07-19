package Arrows;

public interface Diagram
{
	void addArrow2ObjectRule();

	//	reference( nick : Enum, domain : Arrow ) : Reference
	//	Set2 set2( Object source, arrow : Arrow ) :
	void addClass2ObjectRule();

	void addObjectRegistrarRule();

	Arrows arrows();

	Objects objects();

}
