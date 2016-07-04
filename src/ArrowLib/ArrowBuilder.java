package ArrowLib;

enum ArrowName
{
	Contains,
	IsContainedBy
}

public class ArrowBuilder
{
	ArrowConfig arrowConfig;

	public ArrowBuilder()
	{
		arrowConfig = new ArrowConfig();
		//add mandatory params
	}

	public ArrowBuilder name( ArrowName name )
	{
		arrowConfig.setName( name );
		return this;
	}

	public ArrowBuilder inverseName( ArrowName inverseName )
	{
		arrowConfig.setInverseName( inverseName );
		return this;
	}

	// if disabled returns the NullArrow() arrow which ignores all changes done to the arrow
	public ArrowBuilder enable( boolean enabled )
	{
		arrowConfig.setEnabled( enabled );
		return this;
	}

	public ArrowBuilder invertible( boolean enabled )
	{
		arrowConfig.setInvertible( enabled );
		return this;
	}

	public ArrowBuilder domain( Class allowedClasses )
	{
		arrowConfig.setDomain( allowedClasses );
		return this;
	}

	public ArrowBuilder codomain( Class allowedClasses )
	{
		arrowConfig.setCodomain( allowedClasses );
		return this;
	}

	public ArrowBuilder allowMultipleSources( boolean allow )
	{
		arrowConfig.setAllowMultipleSources( allow );
		return this;
	}

	public ArrowBuilder allowMultipleTargets( boolean allow )
	{
		arrowConfig.setAllowMultipleTargets( allow );
		return this;
	}

	public Arrow end()
	{
		return new ArrowImpl( arrowConfig );
	}
}
