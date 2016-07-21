package Shared.MethodBus.Sequence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class MethodSequence<TargetObjectType, ListenerType extends MethodProxy<TargetObjectType>> implements MethodBus<TargetObjectType, ListenerType>
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
	public ListenerType createPublisher( TargetObjectType targetObject, Class<ListenerType> eventListenerClass )
	{
//		if( !( targetObject instanceof eventListenerClass ) )
//			throw new Exception( "Create Publisher failed" );

		TargetProxy handler = new TargetProxy<ListenerType>( this, targetObject );

		return (ListenerType) Proxy.newProxyInstance(
			eventListenerClass.getClassLoader(),
			new Class[]
			{
				eventListenerClass
			},
			handler );
	}

	private void invokeOnListeners( final List<ListenerType> eventListeners, final MethodCall event, final TargetObjectType targetObject ) throws Throwable
	{
		for( ListenerType listener : eventListeners )
		{
			listener.setTargetObject( targetObject );
			event.method().invoke( listener, event.parameters() );
		}
	}

	@Override
	public Object publishEvent( final MethodCall event, final TargetObjectType targetObject )
	{
		try
		{
			if( preEventListeners.isEmpty() && postEventListeners.isEmpty() )
				deadMethodHandler();

			invokeOnListeners( preEventListeners, event, targetObject );

			Object returnedObject = event.method().invoke( targetObject, event.parameters() );

			invokeOnListeners( postEventListeners, event, targetObject );

			return returnedObject;
		}
		catch( InvocationTargetException e )
		{
			if( e.getCause() instanceof Exception )
			{
				throw new RuntimeException( e.getCause().getMessage(), e.getCause() );
			}
			else if( e.getTargetException() != null )
			{
				throw new RuntimeException( e.getTargetException().getMessage(), e.getCause() );
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
