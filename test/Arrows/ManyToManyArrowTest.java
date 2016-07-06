package Arrows;

import Arrows.Impl.ManyToManyArrow;
import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;

public class ManyToManyArrowTest
{
	ManyToManyArrow<String, Character> arrow;

	String word1 = "Something";
	String word2 = "altceva";
	String word3 = "classes";
	String word3Again = "classes";

	public ManyToManyArrowTest()
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
		arrow = new ManyToManyArrow();

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

	}

	@After
	public void tearDown()
	{

	}

	@Test
	public void stressTest()
	{
		Set<Character> allTargets = arrow.targets();

		for( String word : arrow.sources() )
		{
			Set<Character> image = arrow.eval( word );
			for( int i = 0; i < word.length(); ++i )
			{
				Character letter = word.charAt( i );
				assertTrue( allTargets.contains( letter ) );
				assertTrue( image.contains( letter ) );

				assertTrue( arrow.inverse().eval( letter ).contains( word ) );
			}
		}
	}

	@Test
	public void testRelations()
	{

		Set<Map.Entry<String, Character>> entries = arrow.relations();
		Set<Map.Entry<Character, String>> inverseEntries = arrow.inverse().relations();

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
		assertEquals( arrow.inverse().eval( 'i' ).size(), 1 );
		assertEquals( arrow.inverse().eval( 'e' ).size(), 3 );
		assertEquals( arrow.inverse().eval( 'v' ).size(), 1 );
		assertEquals( arrow.inverse().eval( 'S' ).size(), 1 );
		assertEquals( arrow.inverse().eval( 't' ).size(), 2 );

		assertEquals( arrow.eval( word1 ).size(), word1.length() );
		assertEquals( arrow.inverse().targets(), arrow.inverse().inverse().inverse().targets() );

		assertTrue( arrow.eval( word3 ).contains( 's' ) );

		assertEquals( arrow.sources(), arrow.inverse().targets() );
		assertTrue( arrow.sources().contains( word1 ) );
		assertTrue( arrow.sources().contains( word2 ) );
		assertTrue( arrow.sources().contains( word3 ) );
		assertTrue( arrow.sources().contains( "classes" ) );
		assertEquals( arrow.sources().size(), 3 );
	}

	@Test
	public void checkDuplicates()
	{
		//check that 's' appears in a single word
		assertEquals( arrow.inverse().eval( 's' ).size(), 1 );

		//check that duplicate letters don't matter and that eval returns the right result
		Set<Character> letters = arrow.eval( word3 );
		assertEquals( letters.size(), 5 );
		assertTrue( letters.contains( 'c' ) );
		assertTrue( letters.contains( 'l' ) );
		assertTrue( letters.contains( 'a' ) );
		assertTrue( letters.contains( 's' ) );
		assertTrue( letters.contains( 'e' ) );

	}
}
