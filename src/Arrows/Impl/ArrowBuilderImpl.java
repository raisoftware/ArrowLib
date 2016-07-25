package Arrows.Impl;

import Arrows.*;
import Shared.MethodBus.Sequence.MethodSequence;

public class ArrowBuilderImpl implements ArrowBuilder
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

	InverseArrowConfig inverseConfig = new InverseArrowConfig();

	@Override
	public ArrowConfig arrowConfig()
	{
		return this;
	}

	public ArrowBuilderImpl()
	{
		this.listenable = false;
	}

	public ArrowBuilderImpl( Arrows arrows, Enum name, Enum inverseName )
	{
		this.arrows = arrows;
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
	public EditableArrow end()
	{
		assert ( name != null && inverseName != null && arrows != null );
		EditableArrow arrow = new GenericArrow( this );
		if( listenable )
		{
			MethodSequence<EditableArrow, ArrowListener> rules = arrows.rules();
			EditableArrow arrowProxy = rules.createPublisher( arrow, ArrowListener.class );
			arrow = arrowProxy;
		}
		arrows.add( name, inverseName, arrow );

		return arrow;
	}

	@Override
	public boolean allowsMultipleSources()
	{
		return allowsMultipleSources;
	}

	@Override
	public boolean allowsMultipleTargets()
	{
		return allowsMultipleTargets;
	}

	@Override
	public Arrows arrows()
	{
		return arrows;
	}

	@Override
	public Class codomain()
	{
		return codomain;
	}

	@Override
	public Class domain()
	{
		return domain;
	}

	@Override
	public boolean enabled()
	{
		return enabled;
	}

	@Override
	public boolean invertible()
	{
		return invertible;
	}

	@Override
	public ArrowConfig inverse()
	{
		return inverseConfig;
	}

	@Override
	public boolean listenable()
	{
		return listenable;
	}

	@Override
	public ArrowBuilder listenable( boolean enabled )
	{
		this.listenable = enabled;
		return this;
	}

	@Override
	public boolean readOnly()
	{
		return readOnly;
	}

	@Override
	public ArrowBuilder readOnly( boolean enabled )
	{
		this.readOnly = enabled;
		return this;
	}

	private final class InverseArrowConfig implements ArrowConfig
	{
		@Override
		public boolean allowsMultipleSources()
		{
			return allowsMultipleTargets;
		}

		@Override
		public boolean allowsMultipleTargets()
		{
			return allowsMultipleSources;
		}

		@Override
		public Arrows arrows()
		{
			return arrows;
		}

		@Override
		public Class codomain()
		{
			return domain;
		}

		@Override
		public Class domain()
		{
			return codomain;
		}

		@Override
		public boolean enabled()
		{
			return enabled;
		}

		@Override
		public boolean invertible()
		{
			return invertible;
		}

		@Override
		public ArrowConfig inverse()
		{
			return ArrowBuilderImpl.this;
		}

		@Override
		public boolean listenable()
		{
			return listenable;
		}

		@Override
		public boolean readOnly()
		{
			return readOnly;
		}
	}
}
