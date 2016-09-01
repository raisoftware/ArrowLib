package Arrows.Impl;

import Arrows.Arrow;
import Arrows.ArrowDiagram;

public class GenericArrowBuilder
{
	private Class domain = Object.class;
	private Class codomain = Object.class;
	private boolean allowsMultipleSources = true;
	private boolean allowsMultipleTargets = true;
	private ArrowDiagram diagram = null;

	public GenericArrowBuilder( ArrowDiagram diagram )
	{
		this.diagram = diagram;
	}

	public GenericArrowBuilder domain( Class allowedClasses )
	{
		this.domain = allowedClasses;
		return this;
	}

	public GenericArrowBuilder codomain( Class allowedClasses )
	{
		this.codomain = allowedClasses;
		return this;
	}

	public GenericArrowBuilder allowsMultipleSources( boolean allow )
	{
		this.allowsMultipleSources = allow;
		return this;
	}

	public GenericArrowBuilder allowsMultipleTargets( boolean allow )
	{
		this.allowsMultipleTargets = allow;
		return this;
	}

	public Arrow end()
	{
		Arrow arrow = new GenericArrow( diagram, domain, codomain, allowsMultipleSources, allowsMultipleTargets, /*listenable=*/ true );

		diagram.arrows().add( arrow );

		return arrow;
	}
}
