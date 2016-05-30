package Shared.MethodBus.Implementation;

import Shared.MethodBus.MethodBus;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class SyncMethodBus<ListenerType> implements MethodBus<ListenerType>
{

	private final List<ListenerType> eventListeners = new ArrayList<ListenerType>();

	public SyncMethodBus()
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
	public synchronized void unsubscribe( ListenerType listener )
	{
		eventListeners.remove( listener );
	}

	@SuppressWarnings( "unchecked" )
	public ListenerType createPublisher( Object eventSource, Class<ListenerType> eventListenerClass )
	{
		PublisherProxy handler = new PublisherProxy<ListenerType>( this, eventSource, eventListenerClass );

		return (ListenerType) Proxy.newProxyInstance(
			eventListenerClass.getClassLoader(),
			new Class[]
			{
				eventListenerClass
			},
			handler );
	}

	public synchronized void publishEvent( final MethodCall event )
	{
		try
		{
			if( eventListeners.size() == 0 )
				deadMethodHandler();

			for( ListenerType listener : eventListeners )
				event.method().invoke( listener, event.arguments() );
		}
		catch( Throwable e )
		{
			throw new RuntimeException( e.getMessage(), e );
		}

	}
}
