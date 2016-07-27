package Arrows;

public interface ArrowBuilder
{
	ArrowBuilder allowsMultipleTargets( boolean allow );

	ArrowBuilder allowsMultipleSources( boolean allow );

	ArrowBuilder codomain( Class allowedClasses );

	ArrowBuilder domain( Class allowedClasses );

	Arrow end();
}
