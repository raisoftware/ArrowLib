package MethodList;

import Arrows.Property;
import Shared.Collection0.OrderedSet0.Position;
import Shared.MethodList.MethodList;
import java.util.*;
import org.junit.*;

import static org.junit.Assert.*;




public class MethodListTest
{
	List<String> history = new ArrayList<String>();
	MethodList<DummyEvent> methodList;

	DummyFlushLogger dummyFlushLogger;
	DummyEndEventLogger dummyEndEventLogger;
	DummyEventLogger dummyEventLogger;


	public interface DummyEvent
	{
		void start( String name );
		void stop( String name );

		default Class getThisClass()
		{
			return this.getClass();
		}
	}

	class DummyEventLogger implements DummyEvent
	{
		@Override
		public void start( String name )
		{
			history.add( "start-" + name );
		}

		@Override
		public void stop( String name )
		{
			history.add( "stop-" + name );
		}
	}

	class DummyEndEventLogger implements DummyEvent
	{
		@Override
		public void start( String name )
		{
			history.add( "finished:start-" + name );
		}

		@Override
		public void stop( String name )
		{
			history.add( "finished:stop-" + name );
		}
	}

	class DummyFlushLogger implements DummyEvent
	{
		@Override
		public void start( String name )
		{
			history.add( "flush" );
		}

		@Override
		public void stop( String name )
		{
			history.add( "flush" );
		}
	}

	@Before
	public void setUp()
	{
		methodList = new MethodList<>( DummyEvent.class );
		dummyFlushLogger = new DummyFlushLogger();
		dummyEndEventLogger = new DummyEndEventLogger();
		dummyEventLogger = new DummyEventLogger();

		methodList.insert( dummyFlushLogger, Position.End, null );
		methodList.insert( dummyEndEventLogger, Position.Beginning, null );
		methodList.insert( dummyEventLogger, Position.Beginning, null );
		methodList.insert( dummyEventLogger, Position.End, null );
	}
	@After
	public void tearDown()
	{
		history.clear();
	}


	@Test
	public void testListenersInsertionOrder() throws Exception
	{
		assertEquals( methodList.size(), 3 );
		Iterator<DummyEvent> it = methodList.iterator();
		assertEquals( it.next(), dummyEventLogger );
		assertEquals( it.next(), dummyEndEventLogger );
		assertEquals( it.next(), dummyFlushLogger );
	}

	@Test
	public void testMethodCalls() throws Exception
	{
		DummyEvent event = methodList.publisher();
		event.start( "call1" );
		event.start( "call2" );
		event.stop( "call2" );
		event.stop( "call1" );

		assertEquals( 12, history.size() );
		assertEquals( "start-call1", history.get( 0 ) );
		assertEquals( "finished:start-call1", history.get( 1 ) );
		assertEquals( "flush", history.get( 2 ) );

		assertEquals( "start-call2", history.get( 3 ) );
		assertEquals( "finished:start-call2", history.get( 4 ) );
		assertEquals( "flush", history.get( 5 ) );

		assertEquals( "stop-call2", history.get( 6 ) );
		assertEquals( "finished:stop-call2", history.get( 7 ) );
		assertEquals( "flush", history.get( 8 ) );

		assertEquals( "stop-call1", history.get( 9 ) );
		assertEquals( "finished:stop-call1", history.get( 10 ) );
		assertEquals( "flush", history.get( 11 ) );
	}

	@Test
	public void testReturnValue() throws Exception
	{
		DummyEvent event = methodList.publisher();
		assertEquals( event.getThisClass(), DummyFlushLogger.class );
	}

	@Test
	public void testMethodList() throws Exception
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
			public void set( String newValue )
			{
				value = newValue;
			}

		};

		Property<String> ms = MethodList.methodList( Property.class, targetProperty,
			new Property<String>()
		{
			String value = "rule.initialValue";
			@Override
			public String get()
			{
				return value;
			}

			@Override
			public void set( String newValue )
			{
				value = newValue;
			}

		}
		);

		assertEquals( ms.get(), "rule.initialValue" );
		ms.set( "newVal" );
		assertEquals( ms.get(), "newVal" );

	}
}
