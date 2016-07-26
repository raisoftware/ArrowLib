package Arrows;

public interface ObjectConfig
{

	boolean enabled();

	Object name();

	boolean tracksClass();

	boolean tracksInboundArrows();

	boolean tracksOutboundArrows();

}
