package Arrows;

public interface MethodBus<ListenerType>
{
	@SuppressWarnings( "unchecked" )
	public ListenerType createPublisher( ListenerType targetObject, Class<ListenerType> eventListenerClass );

	@SuppressWarnings( "unchecked" )
	public void subscribe( ListenerType listener );

	@SuppressWarnings( "unchecked" )
	public void unsubscribe( ListenerType listener );

	//@SuppressWarnings( "unchecked" )
	void publishEvent( final MethodCall event );
}
