package Shared.MethodBus.Implementation;

import java.lang.reflect.Method;

public class MethodCall<T>
{
	private final Object eventSource;
	private final Class<T> eventListenerClass;
	private final Method method;
	private final Object[] arguments;

	public MethodCall( Object eventSource, Class<T> eventListenerClass, Method method, Object[] arguments )
	{
		this.eventSource = eventSource;
		this.eventListenerClass = eventListenerClass;
		this.method = method;
		this.arguments = arguments;
	}

	public Object eventSource()
	{
		return eventSource;
	}

	public Class<T> eventListenerType()
	{
		return eventListenerClass;
	}

	public Method method()
	{
		return method;
	}

	public Object[] arguments()
	{
		return arguments;
	}

	public String toString()
	{
		return eventListenerClass.getName() + "." + method.getName().toString();
	}

}
