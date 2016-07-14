package Arrows;

public class ObjectConfigBuilder implements ObjectConfig
{
	private boolean enabled = true;
	private Enum name = Objects.StandardObjectName.Unnamed;
	private boolean tracksInboundArrows = true;
	private boolean tracksOutboundArrows = true;
	private boolean tracksClass = true;

	public ObjectConfigBuilder()
	{
	}

	public ObjectConfigBuilder enabled( boolean enabled )
	{
		this.enabled = enabled;
		return this;
	}

	public ObjectConfigBuilder name( Enum name )
	{
		this.name = name;
		return this;
	}

	public ObjectConfigBuilder tracksInboundArrows( boolean tracksInboundArrows )
	{
		this.tracksInboundArrows = tracksInboundArrows;
		return this;
	}

	public ObjectConfigBuilder tracksOutboundArrows( boolean tracksOutboundArrows )
	{
		this.tracksOutboundArrows = tracksOutboundArrows;
		return this;
	}

	public ObjectConfigBuilder tracksClass( boolean tracksClass )
	{
		this.tracksClass = tracksClass;
		return this;
	}

	public ObjectConfig end()
	{
		return this;
	}

	@Override
	public boolean enabled()
	{
		return enabled;
	}

	@Override
	public Enum name()
	{
		return name;
	}

	@Override
	public boolean tracksClass()
	{
		return tracksClass;
	}

	@Override
	public boolean tracksInboundArrows()
	{
		return tracksInboundArrows;
	}

	@Override
	public boolean tracksOutboundArrows()
	{
		return tracksOutboundArrows;
	}

}
