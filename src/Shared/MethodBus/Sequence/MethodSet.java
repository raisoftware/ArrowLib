package Shared.MethodBus.Sequence;

import Arrows.Set0;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.*;


public class MethodSet<ListenerType> implements Set0<ListenerType>
{
	private final ListenerType listenedObject;
	private final Class type;
	private final Set<ListenerType> listeners = new HashSet<>();
	private final ListenerType publisher;

	public MethodSet( ListenerType listenedObject, Class type )
	{
		this.listenedObject = listenedObject;
		this.type = type;


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
		listeners.add( target );
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
			Object returnedObject = event.method().invoke( listenedObject, event.parameters() );

			for( ListenerType listener : listeners )
			{
				event.method().invoke( listener, event.parameters() );
			}
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
