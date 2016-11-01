package Arrows.Impl;

import Arrows.Arrow;
import Arrows.Diagram;

public class GenericArrowBuilder
{
	private Object name = null;
	private Object inverseName = null;
	private Class domain = Object.class;
	private Class codomain = Object.class;
	private boolean allowsMultipleSources = true;
	private boolean allowsMultipleTargets = true;
	private Diagram diagram = null;

	public GenericArrowBuilder( Diagram diagram )
	{
		this.diagram = diagram;
	}

	public GenericArrowBuilder name( Enum name )
	{
		this.name = name;
		return this;
	}

	public GenericArrowBuilder name( String name )
	{
		this.name = name;
		return this;
	}

	public GenericArrowBuilder inverseName( String inverseName )
	{
		this.inverseName = inverseName;
		return this;
	}

	public GenericArrowBuilder inverseName( Enum inverseName )
	{
		this.inverseName = inverseName;
		return this;
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
		String arrowName = name != null ? name.toString() : "GenericArrow";
		String arrowNameInverse = inverseName != null ? inverseName.toString() : "InversesGenericArrow";

		Arrow arrow = new GenericArrow( diagram, arrowName, arrowNameInverse, domain, codomain, allowsMultipleSources, allowsMultipleTargets, /*listenable=*/ true );

		diagram.arrows().add( arrow );
		diagram.arrows().name( arrow, arrowName, arrowNameInverse );
		return arrow;
	}
}
