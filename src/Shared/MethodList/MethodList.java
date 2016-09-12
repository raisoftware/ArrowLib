package Shared.MethodList;

import Shared.Collection0.OrderedSet0;
import Shared.Collection0.OrderedSet0.Position;
import java.lang.reflect.*;
import java.util.*;

import static Arrows.Utils.ExceptionUtils.*;
import static Shared.Collection0.OrderedSet0.Position.*;

public class MethodList<ListenerType> implements OrderedSet0<ListenerType>
{
	private final Class type;
	private final ArrayList<ListenerType> listeners = new ArrayList<>();
	private final ListenerType publisher;

	public static <ListenerType> ListenerType methodList( Class<ListenerType> type, ListenerType... listeners )
	{
		MethodList<ListenerType> codeBlocks = new MethodList<>( type );
		for( ListenerType listener : listeners )
		{
			codeBlocks.add( listener );
		}

		return codeBlocks.publisher();
	}

	public MethodList( Class type )
	{
		this.type = type;

		MethodProxy<ListenerType> handler = new MethodProxy<>( this );

		publisher = (ListenerType) Proxy.newProxyInstance(
			type.getClassLoader(),
			new Class[]
			{
				type
			},
			handler );
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

	@Override
	public void insert( ListenerType item, Position position, ListenerType insertPoint )
	{
		if( contains( item ) )
			return;

		switch( position )
		{
			case Beginning:
				add( 0, item );
				break;
			case End:
				add( item );
				break;
			case After:
			{
				int index = indexOf( insertPoint );
				checkInsertPoint( index );
				add( index + 1, item );
				break;
			}
			case Before:
			{
				int index = indexOf( insertPoint );
				checkInsertPoint( index );
				add( index, item );
				break;
			}
		}

	}

	public ListenerType publisher()
	{
		return publisher;
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

	@Override
	public Class domain()
	{
		return type;
	}

	@Override
	public void add( ListenerType target )
	{
		listeners.add( target );
	}

	private void add( int index, ListenerType item )
	{
		listeners.add( index, item );
	}

	@Override
	public int indexOf( ListenerType item )
	{
		return listeners.indexOf( item );
	}
}
