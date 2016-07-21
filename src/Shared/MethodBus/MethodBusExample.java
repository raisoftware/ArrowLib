package Shared.MethodBus;

import Shared.MethodBus.Implementation.PersistentMethodBus;
import Shared.MethodBus.Implementation.PersistentMethodBus.Mode;

public class MethodBusExample
{

	public static void main( String[] args ) throws Throwable
	{
		MethodBusExample eventSource = new MethodBusExample();

		MethodBus eventBus = new PersistentMethodBus( Mode.Recording );
		SignalType subscriber1 = new SignalType()
		{
			public void start()
			{
				System.out.println( "start1" );
			}

			public void stop()
			{
				System.out.println( "stop1" );
			}
		};
		eventBus.subscribe( subscriber1 );
		eventBus.subscribe( subscriber1 );

		eventBus.subscribe( new SignalType()
		{
			public void start()
			{
				System.out.println( "start2" );
			}

			public void stop()
			{
				System.out.println( "stop2" );
			}
		} );

		SignalType publisher = (SignalType) eventBus.createPublisher( eventSource, SignalType.class );
		System.out.println( "fire start()" );
		publisher.start();

		System.out.println( "fire start()" );
		publisher.start();

		System.out.println( "fire stop()" );
		publisher.stop();

		eventBus.subscribe( new OtherSignalType()
		{
			public void signal( String x )
			{
				System.out.println( "Other signal:" + x );
			}
		} );

		OtherSignalType otherPublisher = (OtherSignalType) eventBus.createPublisher( eventSource, OtherSignalType.class );
		otherPublisher.signal( "incercare1" );
		otherPublisher.signal( "incercare2" );

		System.out.println( "----------------Playback" );

		MethodBus eventBus2 = new PersistentMethodBus( Mode.Playback );
		( (PersistentMethodBus) eventBus2 ).playback();
	}



	public static interface OtherSignalType
	{
		void signal( String x );
	}

	public static interface SignalType
	{
		void start();

		void stop();
	}

}
