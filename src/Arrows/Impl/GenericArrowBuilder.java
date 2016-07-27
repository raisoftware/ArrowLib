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
	private Diagram diagram = null;

	public GenericArrowBuilder( Diagram diagram )
	{
		this.diagram = diagram;
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
		assert ( diagram.arrows() != null );
		Arrow arrow = new GenericArrow( diagram, domain, codomain, allowsMultipleSources, allowsMultipleTargets, /*listenable=*/ true );

		diagram.arrows().add( arrow );

		return arrow;
	}
}
