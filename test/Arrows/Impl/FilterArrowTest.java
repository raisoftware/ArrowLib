package Arrows.Impl;

import Arrows.*;
import Shared.Set0;
import Shared.Set0Utils;
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

		containsArrow = diagram.createGeneric().domain( String.class ).codomain( Character.class ).end();
		diagram.arrows().name( containsArrow, Contains, IsContainedBy );
		connectWordToLetters( containsArrow.editor(), word1 );
		connectWordToLetters( containsArrow.editor(), word2 );
		connectWordToLetters( containsArrow.editor(), word3 );
		connectWordToLetters( containsArrow.editor(), word4 );

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
		assertTrue( Set0Utils.isEmpty( word4Results ) );
	}
}