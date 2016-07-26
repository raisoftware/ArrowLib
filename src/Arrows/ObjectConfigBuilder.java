package Arrows;

public interface ObjectConfigBuilder
{

	ObjectConfigBuilder enabled( boolean enabled );

	ObjectConfigBuilder tracksClass( boolean tracksClass );

	ObjectConfigBuilder tracksInboundArrows( boolean tracksInboundArrows );

	ObjectConfigBuilder tracksOutboundArrows( boolean tracksOutboundArrows );

	ObjectConfig end();
}
