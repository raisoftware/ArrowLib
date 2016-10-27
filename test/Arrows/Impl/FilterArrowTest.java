package Arrows.Impl;

import Arrows.*;
import Shared.Collection0.Set0;
import Shared.Collection0.Sets;
import java.util.function.BiPredicate;
import org.junit.*;

import static Arrows.Test.ArrowName.*;
import static Arrows.Utils.TestsUtils.*;
import static org.junit.Assert.*;

public class FilterArrowTest
{
	private Diagram diagram;
	private Arrow<String, Character> containsArrow;
	private ArrowView<String, Character> filterArrow;
	private final String word1 = "something";
	private final String word2 = "extra";
	private final String word3 = "stuff";
	private final String word4 = "over";

	public FilterArrowTest()
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
		diagram = Diagram.create();

		containsArrow = diagram.createGeneric().name( Contains ).inverseName( IsContainedBy ).domain( String.class ).codomain( Character.class ).end();
		connectWordToLetters( containsArrow, word1 );
		connectWordToLetters( containsArrow, word2 );
		connectWordToLetters( containsArrow, word3 );
		connectWordToLetters( containsArrow, word4 );

		BiPredicate<String, Character> filter = (String source, Character target) -> !target.equals( 'o' ) && !target.equals( 'v' ) && !target.equals( 'e' ) && !target.equals( 'r' );

		filterArrow = diagram.filter( containsArrow, filter );
	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testFilterArrow()
	{

		Set0<String> sources = filterArrow.sources();
		assertEquals( sources.size(), 3 );
		assertTrue( sources.contains( word1 ) );
		assertTrue( sources.contains( word2 ) );
		assertTrue( sources.contains( word3 ) );

		Set0<Character> targets = filterArrow.targets();
		assertEquals( targets.size(), 11 );
		assertFalse( targets.contains( 'o' ) );
		assertFalse( targets.contains( 'v' ) );
		assertFalse( targets.contains( 'e' ) );
		assertFalse( targets.contains( 'r' ) );
		assertTrue( targets.contains( 's' ) );
		assertTrue( targets.contains( 'm' ) );
		assertTrue( targets.contains( 'x' ) );

		Set0<Character> word1Results = filterArrow.targets( word1 );// "something"
		assertEquals( word1Results.size(), 7 );
		assertFalse( word1Results.contains( 'o' ) );
		assertFalse( word1Results.contains( 'e' ) );
		assertTrue( word1Results.contains( 's' ) );
		assertTrue( word1Results.contains( 'm' ) );
		assertTrue( word1Results.contains( 't' ) );
		assertTrue( word1Results.contains( 'h' ) );
		assertTrue( word1Results.contains( 'i' ) );
		assertTrue( word1Results.contains( 'n' ) );
		assertTrue( word1Results.contains( 'g' ) );

		Set0<Character> word2Results = filterArrow.targets( word2 );// "extra"
		assertEquals( word2Results.size(), 3 );
		assertTrue( word2Results.contains( 'x' ) );
		assertTrue( word2Results.contains( 't' ) );
		assertTrue( word2Results.contains( 'a' ) );

		Set0<Character> word3Results = filterArrow.targets( word3 );// "stuff"
		assertEquals( word3Results.size(), 4 );
		assertTrue( word3Results.contains( 's' ) );
		assertTrue( word3Results.contains( 't' ) );
		assertTrue( word3Results.contains( 'u' ) );
		assertTrue( word3Results.contains( 'f' ) );

		Set0<Character> word4Results = filterArrow.targets( word4 );//"over"
		assertTrue( Sets.isEmpty( word4Results ) );

		{
			Set0 words = Sets.create( String.class );
			words.add( word2 );
			words.add( word3 );
			words.add( word4 );
			Set0<Character> letters = filterArrow.targets( words );


			assertEquals( 6, letters.size() );

			//extra
			assertTrue( letters.contains( 'x' ) );
			assertTrue( letters.contains( 't' ) );
			assertTrue( letters.contains( 'a' ) );

			//stuff
			assertTrue( letters.contains( 's' ) );
			assertTrue( letters.contains( 'f' ) );
			assertTrue( letters.contains( 'u' ) );

		}
	}
}
