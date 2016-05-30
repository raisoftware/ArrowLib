package Shared.MethodBus.Implementation;

import Shared.MethodBus.MethodBus;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PublisherProxy<T> implements InvocationHandler
{
	private final MethodBus methodBus;
	private final Object eventSource;
	private final Class<T> eventListenerClass;

	public PublisherProxy( MethodBus methodBus, Object eventSource, Class<T> eventListenerClass )
	{
		this.methodBus = methodBus;
		this.eventSource = eventSource;
		this.eventListenerClass = eventListenerClass;
	}

	@Override
	public Object invoke( Object proxy, Method method, Object[] arguments ) throws Throwable
	{
		methodBus.publishEvent( new MethodCall( eventSource, eventListenerClass, method, arguments ) );
		return null;
	}
}
