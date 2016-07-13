package Arrows;

public class ArrowConfig
{
	private Enum name;
	private Enum inverseName;
	private boolean enabled = true;
	private boolean invertible = true;
	private Class domain = Object.class;
	private Class codomain = Object.class;
	private boolean allowMultipleSources = true;
	private boolean allowMultipleTargets = true;
	private Arrows arrows = null;

	public ArrowConfig( Enum name,
		Enum inverseName,
		boolean enabled,
		boolean invertible,
		Class domain,
		Class codomain,
		boolean allowMultipleSources,
		boolean allowMultipleTargets,
		Arrows arrows )
	{
		this.name = name;
		this.inverseName = inverseName;
		this.enabled = enabled;
		this.invertible = invertible;
		this.domain = domain;
		this.codomain = codomain;
		this.allowMultipleSources = allowMultipleSources;
		this.allowMultipleTargets = allowMultipleTargets;
		this.arrows = arrows;
	}

	public ArrowConfig( Arrows arrows )
	{
		this.arrows = arrows;
	}

	public Enum getName()
	{
		return name;
	}

	public void setName( Enum name )
	{
		this.name = name;
	}

	public Enum getInverseName()
	{
		return inverseName;
	}

	public void setInverseName( Enum inverseName )
	{
		this.inverseName = inverseName;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled( boolean enabled )
	{
		this.enabled = enabled;
	}

	public boolean isInvertible()
	{
		return invertible;
	}

	public void setInvertible( boolean invertible )
	{
		this.invertible = invertible;
	}

	public Class getDomain()
	{
		return domain;
	}

	public void setDomain( Class domain )
	{
		this.domain = domain;
	}

	public Class getCodomain()
	{
		return codomain;
	}

	public void setCodomain( Class codomain )
	{
		this.codomain = codomain;
	}

	public boolean isAllowMultipleSources()
	{
		return allowMultipleSources;
	}

	public void setAllowMultipleSources( boolean allowMultipleSources )
	{
		this.allowMultipleSources = allowMultipleSources;
	}

	public boolean isAllowMultipleTargets()
	{
		return allowMultipleTargets;
	}

	public void setAllowMultipleTargets( boolean allowMultipleTargets )
	{
		this.allowMultipleTargets = allowMultipleTargets;
	}

	public void setArrows( Arrows arrows )
	{
		this.arrows = arrows;
	}

	public Arrows getArrows()
	{
		return this.arrows;
	}

}
