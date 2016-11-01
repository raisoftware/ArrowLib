package Arrows.Impl;

import Arrows.*;

public class ObjectConfigBuilderImpl implements ObjectConfig, ObjectConfig.Builder
{
	private boolean enabled = true;
	private boolean tracksInboundArrows = true;
	private boolean tracksOutboundArrows = true;
	private boolean tracksClass = true;

	@Override
	public Builder enabled( boolean enabled )
	{
		this.enabled = enabled;
		return this;
	}

	@Override
	public Builder tracksInboundArrows( boolean tracksInboundArrows )
	{
		this.tracksInboundArrows = tracksInboundArrows;
		return this;
	}

	@Override
	public Builder tracksOutboundArrows( boolean tracksOutboundArrows )
	{
		this.tracksOutboundArrows = tracksOutboundArrows;
		return this;
	}

	@Override
	public Builder tracksClass( boolean tracksClass )
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
    
    public String toString()
    {
        return String.format("ObjectConfig(enabled=%s, in=%s, out=%s, cls=%s)", enabled, tracksInboundArrows, tracksOutboundArrows, tracksClass);
    }
}
