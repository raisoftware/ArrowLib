package Arrows;

enum ObjectName
{
	RootObject
};

public class ObjectConfig
{
	boolean enabled;
	ObjectName name;
	boolean trackInboundArrows;
	boolean trackOutboundArrows;
	boolean trackClass;

	public ObjectConfig( boolean enabled,
		ObjectName name,
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

	ObjectName name()

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
