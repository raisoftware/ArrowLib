package Arrows;

public interface ArrowBuilder
{
	ArrowConfig arrowConfig();

	ArrowBuilder allowMultipleTargets( boolean allow );

	ArrowBuilder allowssMultipleSources( boolean allow );

	ArrowBuilder codomain( Class allowedClasses );

	ArrowBuilder domain( Class allowedClasses );

	// if disabled returns the NullArrow() arrow which ignores all changes done to the arrow
	ArrowBuilder enabled( boolean enabled );

	Arrow end();

	ArrowBuilder invertible( boolean enabled );

	ArrowBuilder listenable( boolean enabled );

	ArrowBuilder readOnly( boolean enabled );
}
