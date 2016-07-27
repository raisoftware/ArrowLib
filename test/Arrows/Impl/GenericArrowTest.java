package Arrows.Impl;

import Arrows.Diagram;
import Shared.Set0;
import java.util.*;
import org.junit.*;

import static Arrows.Test.ArrowName.*;
import static org.junit.Assert.*;

public class GenericArrowTest
{
	GenericArrow<String, Character> arrow;

	private final String word1 = "Something";
	private final String word2 = "altceva";
	private final String word3 = "classes";
	private final String word3Again = "classes";

	public GenericArrowTest()
	{
	}

	@BeforeClass
	public static void setUpClass()
	{
	}

	@AfterClass
	public static void tearDownClass()
	{
	}

	@Before
	public void setUp()
	{
		Diagram diagram = Diagram.create();

		arrow = new GenericArrow( diagram, String.class, Character.class, true, true, true );
		diagram.arrows().add( arrow );
		diagram.arrows().name( arrow, Contains, IsContainedBy );
		List<Character> chars = new ArrayList();
		chars.add( 'S' );
		chars.add( 'o' );
		chars.add( 'm' );
		chars.add( 'e' );
		chars.add( 't' );
		chars.add( 'h' );
		chars.add( 'i' );
		chars.add( 'n' );
		chars.add( 'g' );

		arrow.connect( word1, chars );
		for( int i = 0; i < word2.length(); ++i )
		{
			arrow.connect( word2, word2.charAt( i ) );
		}

		for( int i = 0; i < word3.length(); ++i )
		{
			arrow.connect( word3, word3.charAt( i ) );
		}

		for( int i = 0; i < word3Again.length(); ++i )
		{
			arrow.connect( word3Again, word3Again.charAt( i ) );
		}

		System.out.println( arrow );
		System.out.println( arrow.inverse() );
		System.out.println( "" );

	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void stressTest()
	{
		Set0<Character> allTargets = arrow.targets();

		for( String word : arrow.sources() )
		{
			Set0<Character> image = arrow.targets( word );
			for( int i = 0; i < word.length(); ++i )
			{
				Character letter = word.charAt( i );
				assertTrue( allTargets.contains( letter ) );
				assertTrue( image.contains( letter ) );

				assertTrue( arrow.inverse().targets( letter ).contains( word ) );
			}
		}
	}

	@Test
	public void testRelations()
	{

		Set0<Map.Entry<String, Character>> entries = arrow.relations();
		Set0<Map.Entry<Character, String>> inverseEntries = arrow.inverse().relations();

		assertEquals( entries.size(), inverseEntries.size() );

		for( Map.Entry<String, Character> entry : entries )
		{
			boolean found = false;
			for( Map.Entry<Character, String> inverseEntry : inverseEntries )
			{
				boolean foundFirst = inverseEntry.getKey().equals( entry.getValue() );
				boolean foundSecond = inverseEntry.getValue().equals( entry.getKey() );
				found = foundFirst && foundSecond;
				if( found )
					break;
			}
			assertTrue( found );
		}
	}

	@Test
	public void test1()
	{
		assertEquals( arrow.inverse().targets( 'i' ).size(), 1 );
		assertEquals( arrow.inverse().targets( 'e' ).size(), 3 );
		assertEquals( arrow.inverse().targets( 'v' ).size(), 1 );
		assertEquals( arrow.inverse().targets( 'S' ).size(), 1 );
		assertEquals( arrow.inverse().targets( 't' ).size(), 2 );

		assertEquals( arrow.targets( word1 ).size(), word1.length() );
		assertEquals( arrow.inverse().targets(), arrow.inverse().inverse().inverse().targets() );

		assertTrue( arrow.targets( word3 ).contains( 's' ) );

		assertEquals( arrow.sources(), arrow.inverse().targets() );
		assertTrue( arrow.sources().contains( word1 ) );
		assertTrue( arrow.sources().contains( word2 ) );
		assertTrue( arrow.sources().contains( word3 ) );
		assertTrue( arrow.sources().contains( "classes" ) );
		assertEquals( arrow.sources().size(), 3 );
	}

	@Test
	public void testDuplicates()
	{
		//check that 's' appears in a single word
		assertEquals( arrow.inverse().targets( 's' ).size(), 1 );

		//check that duplicate letters don't matter and that targets returns the right result
		Set0<Character> letters = arrow.targets( word3 );
		assertEquals( letters.size(), 5 );
		assertTrue( letters.contains( 'c' ) );
		assertTrue( letters.contains( 'l' ) );
		assertTrue( letters.contains( 'a' ) );
		assertTrue( letters.contains( 's' ) );
		assertTrue( letters.contains( 'e' ) );
	}

	@Test
	public void testRemoval()
	{
		arrow.inverse().editor().remove( 's', null );

		assertEquals( arrow.inverse().targets( 's' ).size(), 0 );

		arrow.remove( word1, 'e' );
		assertFalse( arrow.targets( word1 ).contains( 'e' ) );
		assertEquals( arrow.inverse().targets( 'e' ).size(), 2 );

		arrow.remove( word3, null );
		assertTrue( arrow.targets( word3 ).size() == 0 );

	}

}
