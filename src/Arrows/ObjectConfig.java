package Arrows;

import Arrows.Impl.ObjectConfigBuilderImpl;

public interface ObjectConfig
{
	static ObjectConfig.Builder createConfig()
	{
		return new ObjectConfigBuilderImpl();
	}

	boolean enabled();

	boolean tracksClass();

	boolean tracksInboundArrows();

	boolean tracksOutboundArrows();

	public static interface Builder
	{
		Builder enabled( boolean enabled );

		Builder tracksClass( boolean tracksClass );

		Builder tracksInboundArrows( boolean tracksInboundArrows );

		Builder tracksOutboundArrows( boolean tracksOutboundArrows );

		ObjectConfig end();
	}


}
