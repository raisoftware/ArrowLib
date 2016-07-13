package Shared.MethodBus.Sequence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TargetProxy<T> implements InvocationHandler
{
	private final MethodBus methodBus;

	private final Object targetObject;

	public TargetProxy( MethodBus methodBus, Object targetObject )
	{
		this.methodBus = methodBus;

		this.targetObject = targetObject;
	}

	@Override
	public Object invoke( Object proxy, Method method, Object[] arguments ) throws Throwable
	{
		return methodBus.publishEvent( new MethodCall( method, arguments ), targetObject );
	}
}
