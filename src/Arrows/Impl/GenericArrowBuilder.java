package Arrows.Impl;

import Arrows.*;
import Arrows.Impl.Rule.*;

public class GenericArrowBuilder implements ArrowBuilder
{
	private Enum name = null;
	private Enum inverseName = null;
	private boolean enabled = true;
	private boolean invertible = true;
	private Class domain = Object.class;
	private Class codomain = Object.class;
	private boolean allowsMultipleSources = true;
	private boolean allowsMultipleTargets = true;
	private boolean listenable = true;
	private boolean readOnly = false;
	private Arrows arrows = null;
	private Objects objects = null;

	public GenericArrowBuilder()
	{
		this.listenable = false;
	}

	public GenericArrowBuilder( Arrows arrows, Objects objects, Enum name, Enum inverseName )
	{
		this.arrows = arrows;
		this.objects = objects;
		this.name = name;
		this.inverseName = inverseName;
	}

	// if disabled returns the NullArrow() arrow which ignores all changes done to the arrow
	@Override
	public ArrowBuilder enabled( boolean enabled )
	{
		this.enabled = enabled;
		return this;
	}

	@Override
	public ArrowBuilder invertible( boolean enabled )
	{
		this.invertible = enabled;
		return this;
	}

	@Override
	public ArrowBuilder domain( Class allowedClasses )
	{
		this.domain = allowedClasses;
		return this;
	}

	@Override
	public ArrowBuilder codomain( Class allowedClasses )
	{
		this.codomain = allowedClasses;
		return this;
	}

	@Override
	public ArrowBuilder allowsMultipleSources( boolean allow )
	{
		this.allowsMultipleSources = allow;
		return this;
	}

	@Override
	public ArrowBuilder allowsMultipleTargets( boolean allow )
	{
		this.allowsMultipleTargets = allow;
		return this;
	}

	@Override
	public Arrow end()
	{
		assert ( name != null && inverseName != null && arrows != null );
		Arrow arrow = new GenericArrow( domain, codomain, allowsMultipleSources, allowsMultipleTargets, listenable );

		if( listenable )
		{
			Class2ObjectRule class2ObjectRule = new Class2ObjectRule( arrows );
			arrow.listeners().add( class2ObjectRule );
			ObjectRegistrarRule objectRegistrarRule = new ObjectRegistrarRule( arrows, objects );
			arrow.listeners().add( objectRegistrarRule );
			Arrow2ObjectRule arrow2ObjectRule = new Arrow2ObjectRule( arrow, arrows );
			arrow.listeners().add( arrow2ObjectRule );
		}
		arrows.add( name, inverseName, arrow );


		return arrow;
	}


	@Override
	public ArrowBuilder listenable( boolean enabled )
	{
		this.listenable = enabled;
		return this;
	}

	@Override
	public ArrowBuilder readOnly( boolean enabled )
	{
		this.readOnly = enabled;
		return this;
	}
}
