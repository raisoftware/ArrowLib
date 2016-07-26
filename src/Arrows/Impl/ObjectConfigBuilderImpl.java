package Arrows.Impl;

import Arrows.*;

public class ObjectConfigBuilderImpl implements ObjectConfig, ObjectConfigBuilder
{
	private boolean enabled = true;
	private Object name = null;
	private boolean tracksInboundArrows = true;
	private boolean tracksOutboundArrows = true;
	private boolean tracksClass = true;

	public ObjectConfigBuilderImpl( Object name )
	{
		this.name = name;
	}

	@Override
	public ObjectConfigBuilder enabled( boolean enabled )
	{
		this.enabled = enabled;
		return this;
	}

	@Override
	public ObjectConfigBuilder tracksInboundArrows( boolean tracksInboundArrows )
	{
		this.tracksInboundArrows = tracksInboundArrows;
		return this;
	}

	@Override
	public ObjectConfigBuilder tracksOutboundArrows( boolean tracksOutboundArrows )
	{
		this.tracksOutboundArrows = tracksOutboundArrows;
		return this;
	}

	@Override
	public ObjectConfigBuilder tracksClass( boolean tracksClass )
	{
		this.tracksClass = tracksClass;
		return this;
	}

	@Override
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
	public Object name()
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
