package Shared.MethodBus.Sequence;

import Shared.MethodBus.Sequence.MethodCall;

public interface MethodBus<ListenerType>
{
	@SuppressWarnings( "unchecked" )
	public ListenerType createPublisher( ListenerType targetObject, Class<ListenerType> eventListenerClass );

	@SuppressWarnings( "unchecked" )
	public void subscribe( ListenerType listener, MethodSequence.ExecutionTime executionTime );

	@SuppressWarnings( "unchecked" )
	public void unsubscribe( ListenerType listener );

	//@SuppressWarnings( "unchecked" )
	Object publishEvent( final MethodCall event, final ListenerType targetObject );
}
