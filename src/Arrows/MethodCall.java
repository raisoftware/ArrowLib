package Arrows;

import java.lang.reflect.Method;

public class MethodCall
{
	private final Method method;
	private final Object[] parameters;

	public MethodCall( Method method, Object[] parameters )
	{
		this.method = method;
		this.parameters = parameters;
	}

	public Method method()
	{
		return method;
	}

	public Object[] parameters()
	{
		return parameters;
	}

}
