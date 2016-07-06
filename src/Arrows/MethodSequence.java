package Arrows;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class MethodSequence<ListenerType> implements MethodBus<ListenerType>
{

	private final List<ListenerType> eventListeners = new ArrayList<ListenerType>();

	public MethodSequence()
	{
	}

	public void deadMethodHandler()
	{

	}

//    public void setExceptionHandler(Thread.UncaughtExceptionHandler exceptionHandler)
//    {
//        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
//    }
//
	@SuppressWarnings( "unchecked" )
	public synchronized void subscribe( ListenerType listener )
	{
		if( !eventListeners.contains( listener ) )
			eventListeners.add( listener );
	}

	@SuppressWarnings( "unchecked" )
	public void unsubscribe( ListenerType listener )
	{
		eventListeners.remove( listener );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public ListenerType createPublisher( ListenerType targetObject, Class<ListenerType> eventListenerClass )
	{
		//if( !( targetObject instanceof eventListenerClass ) )
		//	throw new Exception( "Create Publisher failed" );

		TargetProxy handler = new TargetProxy<ListenerType>( this, targetObject );

		return (ListenerType) Proxy.newProxyInstance(
			eventListenerClass.getClassLoader(),
			new Class[]
			{
				eventListenerClass
			},
			handler );
	}

	@Override
	public void publishEvent( final MethodCall event )
	{
		try
		{
			if( eventListeners.size() == 0 )
				deadMethodHandler();

			for( ListenerType listener : eventListeners )
				event.method().invoke( listener, event.parameters() );
		}
		catch( Throwable e )
		{
			throw new RuntimeException( e.getMessage(), e );
		}

	}
}
