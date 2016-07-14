package Arrows;

public interface ObjectConfig
{

	boolean enabled();

	Enum name();

	boolean tracksClass();

	boolean tracksInboundArrows();

	boolean tracksOutboundArrows();

}
