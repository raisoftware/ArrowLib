package Arrows;

import org.junit.Before;
import org.junit.Test;

import static Arrows.Test.ArrowName.*;
import static org.junit.Assert.*;

public class ArrowPropertyTest
{
	private ArrowDiagram diagram;
	private Arrow<String, Integer> destringify;

	@Before
	public void setUp()
	{
		diagram = ArrowDiagram.create();

		destringify = diagram.createGeneric().allowsMultipleSources( true ).allowsMultipleTargets( false ).end();
		diagram.arrows().name( destringify, Destringify, Stringify );
		destringify.editor().connect( "one", 1 );
		destringify.editor().connect( "two", 2 );
		destringify.editor().connect( "three", 3 );
		destringify.editor().connect( "four", 4 );
	}

	@Test
	public void testProperties() throws Exception
	{
		Property<Integer> property1 = new ArrowProperty<>( destringify, "five", 5 );
		assertEquals( property1.get(), Integer.valueOf( 5 ) );
		property1.set( 6 );
		assertEquals( property1.get(), Integer.valueOf( 6 ) );
		property1.set( 5 );

		ArrowProperty<String, Integer> property2 = new ArrowProperty<>( destringify, "cinq", 5 );
		assertEquals( property2.similar().size(), 2 );
		assertTrue( property2.similar().contains( "five" ) );
		assertTrue( property2.similar().contains( "cinq" ) );
		assertEquals( property2.owner(), "cinq" );
		assertEquals( property2.arrow(), destringify );

		assertEquals( destringify.target( "five" ), Integer.valueOf( 5 ) );
		assertEquals( destringify.target( "cinq" ), Integer.valueOf( 5 ) );

		assertEquals( destringify.inverse().targets( 5 ).size(), 2 );
		assertTrue( destringify.inverse().targets( 5 ).contains( "five" ) );
		assertTrue( destringify.inverse().targets( 5 ).contains( "cinq" ) );

	}
}
