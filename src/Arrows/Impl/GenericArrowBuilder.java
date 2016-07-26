package Arrows.Impl;

import Arrows.*;

public class GenericArrowBuilder implements ArrowBuilder
{
	private Enum name = null;
	private Enum inverseName = null;
	private Class domain = Object.class;
	private Class codomain = Object.class;
	private boolean allowsMultipleSources = true;
	private boolean allowsMultipleTargets = true;
	private boolean listenable = true;
	private Arrows arrows = null;
	private Objects objects = null;

	public GenericArrowBuilder()
	{
		this.listenable = false;
	}

	public GenericArrowBuilder( Arrows arrows )
	{
		this.arrows = arrows;
		this.objects = objects;
		this.name = name;
		this.inverseName = inverseName;
	}

	@Override
	public GenericArrowBuilder domain( Class allowedClasses )
	{
		this.domain = allowedClasses;
		return this;
	}

	@Override
	public GenericArrowBuilder codomain( Class allowedClasses )
	{
		this.codomain = allowedClasses;
		return this;
	}

	@Override
	public GenericArrowBuilder allowsMultipleSources( boolean allow )
	{
		this.allowsMultipleSources = allow;
		return this;
	}

	@Override
	public GenericArrowBuilder allowsMultipleTargets( boolean allow )
	{
		this.allowsMultipleTargets = allow;
		return this;
	}

	@Override
	public Arrow end()
	{
		assert ( arrows != null );
		Arrow arrow = new GenericArrow( domain, codomain, allowsMultipleSources, allowsMultipleTargets, listenable );

		arrows.add( arrow );

		return arrow;
	}


	@Override
	public ArrowBuilder listenable( boolean enabled )
	{
		this.listenable = enabled;
		return this;
	}
}
