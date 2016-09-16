/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arrows.Impl.Rule;

import Arrows.*;
import Arrows.Arrows.Names;
import Shared.Collection0.Set0;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;

import static Arrows.Test.ArrowName.*;
import static Arrows.Utils.TestsUtils.*;
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

	Arrow<String, Character> containsArrow;

	private final String word1 = "one";
	private final String word2 = "two";
	private final String word3 = "three";
	private final String word4 = "four";

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

			Arrow<Integer, String> arrow = diagram.createGeneric().end();
			diagram.arrows().name( arrow, Stringify, Destringify );
			arrow.aim( 1, "one_" );
			arrow.aim( 2, "two_" );
			arrow.aim( 3, "three_" );
			arrow.aim( 4, "four_" );



			containsArrow = diagram.createGeneric().domain( String.class ).codomain( Character.class ).end();
			diagram.arrows().name( containsArrow, Contains, IsContainedBy );
			connectWordToLetters( containsArrow, word1 );
			connectWordToLetters( containsArrow, word2 );
			connectWordToLetters( containsArrow, word3 );
			connectWordToLetters( containsArrow, word4 );



			arrows = diagram.arrows();
			object2outboundArrow = arrows.arrow( Names.Object_OutboundArrow );
			outboundArrow2object = object2outboundArrow.inverse();
			object2inboundArrow = arrows.arrow( Names.Object_InboundArrow );
			inboundArrow2object = object2inboundArrow.inverse();
			name2Arrow = arrows.arrow( Names.Name_Arrow );
			stringifyArrow = name2Arrow.target( Stringify );
			editableStringifyArrow = arrows.arrow( Stringify );
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

		Set0 sourceObjects = outboundArrow2object.targets( stringifyArrow );
		Set0 targetObjects = inboundArrow2object.targets( stringifyArrow );

		assertEquals( sourceObjects.size(), 4 );
		assertTrue( sourceObjects.contains( 1 ) );
		assertTrue( sourceObjects.contains( 2 ) );
		assertTrue( sourceObjects.contains( 3 ) );
		assertTrue( sourceObjects.contains( 4 ) );

		assertEquals( targetObjects.size(), 4 );
		assertTrue( targetObjects.contains( "one_" ) );
		assertTrue( targetObjects.contains( "two_" ) );
		assertTrue( targetObjects.contains( "three_" ) );
		assertTrue( targetObjects.contains( "four_" ) );
	}

	@Test
	public void test1Remove() throws Exception
	{

		Set0 targets = inboundArrow2object.targets( stringifyArrow );
		assertTrue( targets.contains( "two_" ) );
		assertFalse( targets.contains( 2 ) );

		editableStringifyArrow.remove( 2, "two_" );
		Set0 targetsAfterRemove = inboundArrow2object.targets( editableStringifyArrow );

		assertFalse( targetsAfterRemove.contains( 2 ) );
		assertTrue( object2outboundArrow.targets( 2 ).size() == 0 );
		assertFalse( object2outboundArrow.targets( 3 ).size() == 0 );
		assertTrue( object2inboundArrow.targets( "two_" ).size() == 0 );
		assertFalse( object2inboundArrow.targets( "three_" ).size() == 0 );
	}

	@Test
	public void test2Remove() throws Exception
	{
		String string_5or8 = "fiveOrEight";
		editableStringifyArrow.aim( 5, string_5or8 );
		editableStringifyArrow.aim( 8, string_5or8 );


		assertTrue( outboundArrow2object.targets( editableStringifyArrow ).contains( 5 ) );
		assertTrue( outboundArrow2object.targets( editableStringifyArrow ).contains( 8 ) );
		assertTrue( inboundArrow2object.targets( editableStringifyArrow ).contains( string_5or8 ) );



		editableStringifyArrow.remove( 5, string_5or8 );

		assertFalse( outboundArrow2object.targets( editableStringifyArrow ).contains( 5 ) );
		assertTrue( outboundArrow2object.targets( editableStringifyArrow ).contains( 8 ) );
		assertTrue( inboundArrow2object.targets( editableStringifyArrow ).contains( string_5or8 ) );


		editableStringifyArrow.remove( 8, string_5or8 );

		assertFalse( outboundArrow2object.targets( editableStringifyArrow ).contains( 5 ) );
		assertFalse( outboundArrow2object.targets( editableStringifyArrow ).contains( 8 ) );
		assertFalse( inboundArrow2object.targets( editableStringifyArrow ).contains( string_5or8 ) );
	}


	@Test
	public void test3Remove() throws Exception
	{
		{
			Set0 letters = inboundArrow2object.targets( containsArrow );
			assertEquals( 9, letters.size() );
			Set0 words = outboundArrow2object.targets( containsArrow );
			assertEquals( 4, words.size() );
		}

		containsArrow.remove( word1, 'o' );
		containsArrow.remove( word2, 'o' );
		containsArrow.remove( word4, 'o' );
		{
			Set0 letters = inboundArrow2object.targets( containsArrow );
			assertEquals( 8, letters.size() );
			Set0 words = outboundArrow2object.targets( containsArrow );
			assertEquals( 4, words.size() );
		}

		List toRemove = new ArrayList<>();
		toRemove.add( 'h' );
		toRemove.add( 'e' );
		containsArrow.removeAll( word3, toRemove );

		{
			Set0 letters = inboundArrow2object.targets( containsArrow );
			assertEquals( 7, letters.size() );
			Set0 words = outboundArrow2object.targets( containsArrow );
			assertEquals( 4, words.size() );
		}

		toRemove.clear();
		toRemove.add( word4 );
		toRemove.add( word3 );
		containsArrow.removeAll( toRemove, 'u' );
		containsArrow.removeAll( toRemove, 'f' );
		containsArrow.removeAll( toRemove, 'r' );

		containsArrow.removeAll( word4, toRemove );
		{
			Set0 letters = inboundArrow2object.targets( containsArrow );
			assertEquals( 4, letters.size() );
			Set0 words = outboundArrow2object.targets( containsArrow );
			assertEquals( 3, words.size() );
		}

		containsArrow.removeTargets( word1 );
		{
			Set0 letters = inboundArrow2object.targets( containsArrow );
			assertEquals( 2, letters.size() );
			assertTrue( letters.contains( 'w' ) );
			assertTrue( letters.contains( 't' ) );
			Set0 words = outboundArrow2object.targets( containsArrow );
			assertEquals( 2, words.size() );
			assertTrue( words.contains( word2 ) );
			assertTrue( words.contains( word3 ) );
		}

		containsArrow.removeSources( 't' );
		{
			Set0 letters = inboundArrow2object.targets( containsArrow );
			assertEquals( 1, letters.size() );
			assertTrue( letters.contains( 'w' ) );
			Set0 words = outboundArrow2object.targets( containsArrow );
			assertEquals( 1, words.size() );
			assertTrue( words.contains( word2 ) );
		}
	}

}
