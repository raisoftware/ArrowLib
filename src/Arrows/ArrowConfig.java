package Arrows;

public interface ArrowConfig
{

	boolean allowsMultipleSources();

	boolean allowsMultipleTargets();

	Arrows arrows();

	Class codomain();

	Class domain();

	boolean enabled();

	boolean invertible();

	boolean canBeListenedTo();

	ArrowConfig inverse();

}
