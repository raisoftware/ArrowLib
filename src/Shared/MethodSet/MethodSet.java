package Shared.MethodSet;

import Arrows.Property;
import Shared.Set0;
import java.lang.reflect.*;
import java.util.*;

public class MethodSet<ListenerType> implements Set0<ListenerType>
{
	private final Class type;
	private final ArrayList<ListenerType> listeners = new ArrayList<>();
	private final ListenerType publisher;

    public static <ListenerType> ListenerType methodList( Class<ListenerType> type, ListenerType... listeners )
    {
        MethodSet<ListenerType> codeBlocks = new MethodSet<ListenerType>( type );
        for( ListenerType listener : listeners )
            codeBlocks.add( listener );
        
        return codeBlocks.publisher();
    }
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
    
    
    public static void main( String[] args )
    {
        Property<String> targetProperty = new Property<String>()
        {
            String value = "targetProperty.initialValue";
            @Override
            public String get()
            {
                return value;
            }

            @Override
            public void set(String newValue)
            {
                value = newValue;
            }
            
        };
        Property<String> ms = methodList( Property.class, targetProperty,
            new Property<String>()
            {
                String value = "rule.initialValue";
                @Override
                public String get()
                {
                    return value;
                }

                @Override
                public void set(String newValue)
                {
                    value = newValue;
                }

            }
            );
        System.out.println( ms.get() );
        ms.set( "newVal");
        System.out.println( ms.get() );
        
    }
}
