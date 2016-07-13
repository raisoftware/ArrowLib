package Arrows;

public class ObjectConfig
{
	boolean enabled;
	Enum name;
	boolean trackInboundArrows;
	boolean trackOutboundArrows;
	boolean trackClass;

	public ObjectConfig( boolean enabled,
		Enum name,
		boolean trackInboundArrows,
		boolean trackOutboundArrows,
		boolean trackClass )
	{
		this.enabled = enabled;
		this.name = name;
		this.trackInboundArrows = trackInboundArrows;
		this.trackOutboundArrows = trackOutboundArrows;
		this.trackClass = trackClass;
	}

	boolean enabled()
	{
		return enabled;
	}

	Enum name()
	{
		return name;
	}

	boolean trackInboundArrows()
	{
		return trackInboundArrows;
	}

	boolean trackOutboundArrows()
	{
		return trackOutboundArrows;
	}

	boolean trackClass()
	{
		return trackClass;
	}

}
