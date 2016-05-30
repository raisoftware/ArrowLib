package Shared.MethodBus.Implementation;

import Shared.MethodBus.MethodBus;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.*;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import org.objenesis.strategy.StdInstantiatorStrategy;

public class PersistentMethodBus<ListenerType> implements MethodBus<ListenerType>
{
	public static enum Mode
	{
		Recording, Playback
	}

	protected final Kryo kryo;
	protected final Mode mode;

	private List<ListenerType> eventListeners = new ArrayList<ListenerType>();

	public PersistentMethodBus( Mode mode )
	{
		this.mode = mode;
		kryo = new Kryo();
		kryo.setInstantiatorStrategy( new StdInstantiatorStrategy() );
	}

	public void deadMethodHandler()
	{

	}

//    public void setExceptionHandler(Thread.UncaughtExceptionHandler exceptionHandler)
//    {
//        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
//    }
//
	@SuppressWarnings( "unchecked" )
	public synchronized void subscribe( ListenerType listener )
	{
		if( !eventListeners.contains( listener ) )
			eventListeners.add( listener );
	}

	@SuppressWarnings( "unchecked" )
	public synchronized void unsubscribe( ListenerType listener )
	{
		eventListeners.remove( listener );
	}

	@SuppressWarnings( "unchecked" )
	public ListenerType createPublisher( Object eventSource, Class<ListenerType> eventListenerClass )
	{
		PublisherProxy handler = new PublisherProxy<ListenerType>( this, eventSource, eventListenerClass );

		return (ListenerType) Proxy.newProxyInstance(
			eventListenerClass.getClassLoader(),
			new Class[]
			{
				eventListenerClass
			},
			handler );
	}

	public synchronized void playback() throws FileNotFoundException
	{
		Input input = new Input( new FileInputStream( "file.bin" ) );
		MethodCall call = null;

		while( !input.eof() )
		{
			call = kryo.readObject( input, MethodCall.class );

			Integer size = kryo.readObject( input, Integer.class );
			this.eventListeners = new ArrayList<>( size );
			for( int i = 0; i < size; ++i )
			{
				ListenerType listener = (ListenerType) kryo.readClassAndObject( input );
				this.eventListeners.add( listener );
			}

			publishEvent( call );
		}
		input.close();

	}

	public synchronized void publishEvent( final MethodCall event )
	{
		try
		{

			if( mode == Mode.Recording )
			{
				Output output = new Output( new FileOutputStream( "file.bin", true ) );

				kryo.writeObject( output, event );
				kryo.writeObject( output, new Integer( eventListeners.size() ) );
				for( ListenerType listener : eventListeners )
					kryo.writeClassAndObject( output, listener );
				output.close();
			}

			if( eventListeners.size() == 0 )
				deadMethodHandler();

			for( ListenerType listener : eventListeners )
			{
				if( event.method().getDeclaringClass().isInstance( listener ) )
				{
					event.method().invoke( listener, event.arguments() );
				}
			}
		}
		catch( Throwable e )
		{
			e.printStackTrace();
			throw new RuntimeException( e.getMessage(), e );
		}

	}

}
