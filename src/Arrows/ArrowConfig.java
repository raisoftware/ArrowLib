package Arrows;

public interface ArrowConfig
{

	boolean allowsMultipleSources();

	boolean allowsMultipleTargets();

	Arrows arrows();

	Class codomain();

	Class domain();

	boolean enabled();

	//Enum inverseName();
	boolean invertible();

	//Enum name();
	ArrowConfig inverse();

}
