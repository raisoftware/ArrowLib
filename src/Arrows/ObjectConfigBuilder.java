package Arrows;

public class ObjectConfigBuilder
{
	private boolean enabled;
	private ObjectName name;
	private boolean trackInboundArrows;
	private boolean trackOutboundArrows;
	private boolean trackClass;

	public ObjectConfigBuilder()
	{
	}

	public ObjectConfigBuilder enable( boolean enabled )
	{
		this.enabled = enabled;
		return this;
	}

	public ObjectConfigBuilder name( ObjectName name )
	{
		this.name = name;
		return this;
	}

	public ObjectConfigBuilder trackInboundArrows( boolean trackInboundArrows )
	{
		this.trackInboundArrows = trackInboundArrows;
		return this;
	}

	public ObjectConfigBuilder trackOutboundArrows( boolean trackOutboundArrows )
	{
		this.trackOutboundArrows = trackOutboundArrows;
		return this;
	}

	public ObjectConfigBuilder trackClass( boolean trackClass )
	{
		this.trackClass = trackClass;
		return this;
	}

	public ObjectConfig end()
	{
		return new ObjectConfig( enabled, name, trackInboundArrows,
			trackOutboundArrows, trackClass );
	}

}
