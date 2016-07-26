/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arrows.Impl.Rule;

import Arrows.*;
import Arrows.Arrows.StandardArrowName;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;

import static Arrows.Test.ArrowName.*;
import static org.junit.Assert.*;

public class Arrow2ObjectRuleTest
{
	Arrows arrows;
	Arrow<Object, Arrow> object2outboundArrow;
	Arrow<Arrow, Object> outboundArrow2object;
	Arrow<Object, Arrow> object2inboundArrow;
	Arrow<Arrow, Object> inboundArrow2object;
	Arrow<Integer, String> editableStringifyArrow;
	Arrow<Integer, String> stringifyArrow;
	Arrow<Enum, Arrow> name2Arrow;

	@BeforeClass
	public static void setUpClass()
	{
	}

	@AfterClass
	public static void tearDownClass()
	{
	}

	Diagram diagram;

	@Before
	public void setUp()
	{
		try
		{
			diagram = Diagram.create();

			Arrow<Integer, String> arrow = diagram.arrows().createGeneric().end();
			diagram.arrows().name( arrow, Stringify, Destringify );
			arrow.editor().connect( 1, "one" );
			arrow.editor().connect( 2, "two" );
			arrow.editor().connect( 3, "three" );
			arrow.editor().connect( 4, "four" );

			arrows = diagram.arrows();
			object2outboundArrow = (Arrow) arrows.arrow( StandardArrowName.Object2OutboundArrow );
			outboundArrow2object = object2outboundArrow.inverse();
			object2inboundArrow = (Arrow) arrows.arrow( StandardArrowName.Object2InboundArrow );
			inboundArrow2object = object2inboundArrow.inverse();
			name2Arrow = (Arrow) arrows.arrow( StandardArrowName.Name2Arrow );
			stringifyArrow = name2Arrow.target( Stringify );
			editableStringifyArrow = (Arrow) arrows.arrow( Stringify );
		}
		catch( Exception ex )
		{
			Logger.getLogger( Arrow2ObjectRuleTest.class.getName() ).log( Level.SEVERE, null, ex );
		}
	}


	@After
	public void tearDown()
	{
	}

	@Test
	public void testTargets() throws Exception
	{

		Set sourceObjects = outboundArrow2object.targets( stringifyArrow );
		Set targetObjects = inboundArrow2object.targets( stringifyArrow );

		assertEquals( sourceObjects.size(), 4 );
		assertTrue( sourceObjects.contains( 1 ) );
		assertTrue( sourceObjects.contains( 2 ) );
		assertTrue( sourceObjects.contains( 3 ) );
		assertTrue( sourceObjects.contains( 4 ) );

		assertEquals( targetObjects.size(), 4 );
		assertTrue( targetObjects.contains( "one" ) );
		assertTrue( targetObjects.contains( "two" ) );
		assertTrue( targetObjects.contains( "three" ) );
		assertTrue( targetObjects.contains( "four" ) );
	}

	@Test
	public void test1Remove() throws Exception
	{

		Set targets = inboundArrow2object.targets( stringifyArrow );
		assertTrue( targets.contains( "two" ) );
		assertFalse( targets.contains( 2 ) );
		editableStringifyArrow.editor().remove( 2, "two" );

		Set targetsAfterRemove = inboundArrow2object.targets( editableStringifyArrow );

		assertFalse( targetsAfterRemove.contains( 2 ) );
		assertTrue( object2outboundArrow.targets( 2 ).isEmpty() );
		assertFalse( object2outboundArrow.targets( 3 ).isEmpty() );
		assertTrue( object2inboundArrow.targets( "two" ).isEmpty() );
		assertFalse( object2inboundArrow.targets( "three" ).isEmpty() );
	}

	@Test
	public void test2Remove() throws Exception
	{
		String string_5or8 = "fiveOrEight";
		editableStringifyArrow.editor().connect( 5, string_5or8 );
		editableStringifyArrow.editor().connect( 8, string_5or8 );


		assertTrue( outboundArrow2object.targets( editableStringifyArrow ).contains( 5 ) );
		assertTrue( outboundArrow2object.targets( editableStringifyArrow ).contains( 8 ) );
		assertTrue( inboundArrow2object.targets( editableStringifyArrow ).contains( string_5or8 ) );



		editableStringifyArrow.editor().remove( 5, string_5or8 );

		assertFalse( outboundArrow2object.targets( editableStringifyArrow ).contains( 5 ) );
		assertTrue( outboundArrow2object.targets( editableStringifyArrow ).contains( 8 ) );
		assertTrue( inboundArrow2object.targets( editableStringifyArrow ).contains( string_5or8 ) );


		editableStringifyArrow.editor().remove( 8, string_5or8 );

		assertFalse( outboundArrow2object.targets( editableStringifyArrow ).contains( 5 ) );
		assertFalse( outboundArrow2object.targets( editableStringifyArrow ).contains( 8 ) );
		assertFalse( inboundArrow2object.targets( editableStringifyArrow ).contains( string_5or8 ) );
	}



}
