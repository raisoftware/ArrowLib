package Arrows;

public interface ArrowBuilder
{
	ArrowBuilder allowsMultipleTargets( boolean allow );

	ArrowBuilder allowsMultipleSources( boolean allow );

	ArrowBuilder codomain( Class allowedClasses );

	ArrowBuilder domain( Class allowedClasses );

	// if disabled returns the NullArrow() arrow which ignores all changes done to the arrow
	ArrowBuilder enabled( boolean enabled );

	ArrowBuilder invertible( boolean enabled );

	ArrowBuilder listenable( boolean enabled );

	ArrowBuilder readOnly( boolean enabled );

	Arrow end();
}
