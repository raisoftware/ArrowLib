package Shared.MethodSet;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MethodProxy<T> implements InvocationHandler
{
	private final MethodSet<T> methodSet;

	public MethodProxy( MethodSet<T> methodSet )
	{
		this.methodSet = methodSet;
	}

	@Override
	public Object invoke( Object proxy, Method method, Object[] arguments ) throws Throwable
	{
		return methodSet.publishEvent( new MethodCall( method, arguments ) );
	}
}
