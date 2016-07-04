package ArrowLib;

public class ArrowConfig
{
	private ArrowName name;
	private ArrowName inverseName;
	private boolean enabled = true;
	private boolean invertible = true;
	private Class domain = Object.class;
	private Class codomain = Object.class;
	private boolean allowMultipleSources = true;
	private boolean allowMultipleTargets = true;

	public ArrowConfig( ArrowName name,
		ArrowName inverseName,
		boolean enabled,
		boolean invertible,
		Class domain,
		Class codomain,
		boolean allowMultipleSources,
		boolean allowMultipleTargets )
	{
		this.name = name;
		this.inverseName = inverseName;
		this.enabled = enabled;
		this.invertible = invertible;
		this.domain = domain;
		this.codomain = codomain;
		this.allowMultipleSources = allowMultipleSources;
		this.allowMultipleTargets = allowMultipleTargets;
	}

	public ArrowConfig()
	{
	}

	public ArrowName getName()
	{
		return name;
	}

	public void setName( ArrowName name )
	{
		this.name = name;
	}

	public ArrowName getInverseName()
	{
		return inverseName;
	}

	public void setInverseName( ArrowName inverseName )
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

}
