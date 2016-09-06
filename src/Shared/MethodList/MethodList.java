package Shared.MethodList;

import java.lang.reflect.*;
import java.util.*;

public class MethodList<ListenerType>
{
	public enum Positioning
	{
		Before,
		After
	}

	private final Class type;
	private final ArrayList<ListenerType> listeners = new ArrayList<>();
	private final ListenerType publisher;

    public static <ListenerType> ListenerType methodList( Class<ListenerType> type, ListenerType... listeners )
    {
		MethodList<ListenerType> codeBlocks = new MethodList<ListenerType>( type );
		for( ListenerType listener : listeners )
		{
			codeBlocks.insert( listener, Positioning.After );
		}

        return codeBlocks.publisher();
    }
	
	public MethodList( Class type )
	{
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

	public void insert( ListenerType target, Positioning positioning )
	{
		if( !listeners.contains( target ) )
		{
			if( positioning == Positioning.After )
			{
				listeners.add( target );
			}
			else
			{
				listeners.add( 0, target );
			}
		}
	}

	public void remove( ListenerType target )
	{
		listeners.remove( target );
	}

	public boolean contains( ListenerType target )
	{
		return listeners.contains( target );
	}

	public int size()
	{
		return listeners.size();
	}

	public Iterator<ListenerType> iterator()
	{
		return listeners.iterator();
	}

	public Object publishEvent( final MethodCall event )
	{
		try
		{
			Object returnValue = null;
			for( ListenerType listener : listeners )
			{
				returnValue = event.method().invoke( listener, event.parameters() );
			}

			return returnValue;
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
