package Shared.MethodBus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MethodProxy<T> implements InvocationHandler
{
	private final MethodBus<T> methodSet;

	public MethodProxy( MethodBus<T> methodSet )
	{
		this.methodSet = methodSet;
	}

	@Override
	public Object invoke( Object proxy, Method method, Object[] arguments ) throws Throwable
	{
		return methodSet.publishEvent( new MethodCall( method, arguments ) );
	}
}
