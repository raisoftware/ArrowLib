package Arrows;

import Arrows.Impl.ManyToManyArrow;

public class ArrowBuilder
{
	ArrowConfig arrowConfig;

	public ArrowBuilder( Arrows arrows )
	{
		arrowConfig = new ArrowConfig( arrows );
		//add mandatory params
	}

	public ArrowBuilder name( Enum name )
	{
		arrowConfig.setName( name );
		return this;
	}

	public ArrowBuilder inverseName( Enum inverseName )
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

	public ArrowBuilder arrows( Arrows arrows )
	{
		arrowConfig.setArrows( arrows );
		return this;
	}

	public Arrow end()
	{
		Arrow arrow = new ManyToManyArrow();
		Arrows arrows = arrowConfig.getArrows();
		if( arrows != null )
		{
			arrows.add( arrowConfig.getName(), arrowConfig.getInverseName(), arrow );
		}
		return arrow;
	}
}
