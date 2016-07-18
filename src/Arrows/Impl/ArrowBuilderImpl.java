package Arrows.Impl;

import Arrows.*;
import Shared.MethodBus.Sequence.MethodSequence;

public class ArrowBuilderImpl implements ArrowConfig, ArrowBuilder
{
	private final Enum name;
	private final Enum inverseName;
	private boolean enabled = true;
	private boolean invertible = true;
	private Class domain = Object.class;
	private Class codomain = Object.class;
	private boolean allowsMultipleSources = true;
	private boolean allowsMultipleTargets = true;
	private boolean canBeListenedTo = true;
	private Arrows arrows = null;

	InverseArrowConfig inverseConfig = new InverseArrowConfig();

	@Override
	public ArrowConfig arrowConfig()
	{
		return this;
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
	public ArrowBuilder allowssMultipleSources( boolean allow )
	{
		this.allowsMultipleSources = allow;
		return this;
	}

	@Override
	public ArrowBuilder allowMultipleTargets( boolean allow )
	{
		this.allowsMultipleTargets = allow;
		return this;
	}

	@Override
	public Arrow end()
	{
		Arrow arrow = new ManyToManyArrow( this );
		if( canBeListenedTo )
		{
			MethodSequence<Arrow, ArrowListener> methodSequence = arrows.methodSequence();
			Arrow arrowProxy = methodSequence.createPublisher( arrow, ArrowListener.class );
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
	public boolean canBeListenedTo()
	{
		return canBeListenedTo;
	}

	@Override
	public ArrowBuilder canBeListenedTo( boolean enabled )
	{
		this.canBeListenedTo = enabled;
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
		public boolean canBeListenedTo()
		{
			return canBeListenedTo;
		}
	}
}
