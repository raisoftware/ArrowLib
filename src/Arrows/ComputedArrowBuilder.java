package Arrows;

import Arrows.Impl.ComputedArrowImpl;
import java.util.Set;
import java.util.function.Function;

public class ComputedArrowBuilder<K, V>
{
	private Class domain = Object.class;
	private Class codomain = Object.class;
	private Function<K, Set<V>> function;

	private Diagram diagram = null;

	public ComputedArrowBuilder( Diagram diagram )
	{
		this.diagram = diagram;
	}

	public ComputedArrowBuilder domain( Class allowedClasses )
	{
		this.domain = allowedClasses;
		return this;
	}

	public ComputedArrowBuilder codomain( Class allowedClasses )
	{
		this.codomain = allowedClasses;
		return this;
	}

	public ComputedArrowBuilder function( Function<K, Set<V>> function )
	{
		this.function = function;
		return this;
	}

	public ComputedArrow end()
	{
		assert ( diagram != null );
		ComputedArrow arrow = new ComputedArrowImpl( diagram, function, domain, codomain );

		diagram.arrows().add( arrow );

		return arrow;
	}
}
