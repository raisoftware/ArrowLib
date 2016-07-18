package Shared.MethodBus.Sequence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class MethodSequence<ListenerType> implements MethodBus<ListenerType>
{

	public static enum ExecutionTime
	{
		ExecuteBefore, ExecuteAfter
	};

	private final List<ListenerType> preEventListeners = new ArrayList<>();
	private final List<ListenerType> postEventListeners = new ArrayList<>();

	public MethodSequence()
	{
	}

	private void deadMethodHandler()
	{

	}

//    public void setExceptionHandler(Thread.UncaughtExceptionHandler exceptionHandler)
//    {
//        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
//    }
//
	@SuppressWarnings( "unchecked" )
	@Override
	public synchronized void subscribe( ListenerType listener, ExecutionTime executionTime )
	{
		if( executionTime == ExecutionTime.ExecuteBefore && !preEventListeners.contains( listener ) )
		{
			preEventListeners.add( listener );
		}
		else if( executionTime == ExecutionTime.ExecuteAfter && !postEventListeners.contains( listener ) )
		{
			postEventListeners.add( listener );
		}
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public void unsubscribe( ListenerType listener )
	{
		preEventListeners.remove( listener );
		postEventListeners.remove( listener );
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
	public Object publishEvent( final MethodCall event, final ListenerType targetObject )
	{
		try
		{
			if( preEventListeners.isEmpty() && postEventListeners.isEmpty() )
				deadMethodHandler();

			for( ListenerType listener : preEventListeners )
				event.method().invoke( listener, event.parameters() );

			Object returnedObject = event.method().invoke( targetObject, event.parameters() );

			for( ListenerType listener : postEventListeners )
				event.method().invoke( listener, event.parameters() );

			return returnedObject;
		}
		catch( InvocationTargetException e )
		{
			if( e.getCause() instanceof Exception )
			{
				throw new RuntimeException( e.getCause().getMessage(), e.getCause() );
			}
			else
			{
				throw new RuntimeException( e.getMessage(), e.getCause() );

			}
		}
		catch( Throwable e )
		{
			throw new RuntimeException( e.getMessage(), e );
		}
	}
}
