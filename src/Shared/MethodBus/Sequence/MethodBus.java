package Shared.MethodBus.Sequence;

public interface MethodBus<TargetObjectType, ListenerType>
{
	@SuppressWarnings( "unchecked" )
	public ListenerType createPublisher( TargetObjectType targetObject, Class<ListenerType> eventListenerClass );

	@SuppressWarnings( "unchecked" )
	public void subscribe( ListenerType listener, MethodSequence.ExecutionTime executionTime );

	@SuppressWarnings( "unchecked" )
	public void unsubscribe( ListenerType listener );

	//@SuppressWarnings( "unchecked" )
	Object publishEvent( final MethodCall event, final TargetObjectType targetObject );
}
