package Shared.MethodSet;

import Shared.Set0;
import java.lang.reflect.*;
import java.util.*;

public class MethodSet<ListenerType> implements Set0<ListenerType>
{
	private final Class type;
	private final ArrayList<ListenerType> listeners = new ArrayList<>();
	private final ListenerType publisher;

	public MethodSet( Class type )
	{
		this.type = type;

		Method[] methods = type.getDeclaredMethods();
		for( int i = 0; i < methods.length; ++i )
		{
			assert ( methods[i].getReturnType().equals( Void.TYPE ) );
		}


		MethodProxy handler = new MethodProxy<ListenerType>( this );

		publisher = (ListenerType) Proxy.newProxyInstance(
			type.getClassLoader(),
			new Class[]
			{
				type
			},
			handler );
	}

	public ListenerType publisher()
	{
		return publisher;
	}

	@Override
	public void add( ListenerType target )
	{
		if( !listeners.contains( target ) )
		{
			listeners.add( target );
		}
	}

	@Override
	public void remove( ListenerType target )
	{
		listeners.remove( target );
	}

	@Override
	public boolean contains( ListenerType target )
	{
		return listeners.contains( target );
	}

	@Override
	public int size()
	{
		return listeners.size();
	}

	@Override
	public Iterator<ListenerType> iterator()
	{
		return listeners.iterator();
	}

	public Object publishEvent( final MethodCall event )
	{
		try
		{
			for( ListenerType listener : listeners )
			{
				event.method().invoke( listener, event.parameters() );
			}

			return null;
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
