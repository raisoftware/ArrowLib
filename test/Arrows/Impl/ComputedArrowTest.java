package Arrows.Impl;

import Arrows.*;
import Shared.Set0;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import org.junit.*;

import static org.junit.Assert.*;

public class ComputedArrowTest
{

	private Diagram diagram;
	private final String word1 = "abcd";
	private final String word2 = "efgh";
	private final String word3 = "ijkl";
	private final String word4 = "mnop";
	private ComputedArrow computedArrow;
	String mergedWord12 = word1 + word2;
	String mergedWord34 = word3 + word4;
	private final String unionString = word1 + word2 + word3 + word4;

	public ComputedArrowTest()
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

		Function<String, Set<Character>> function = (String word) ->
		{
			Set<Character> chars = new HashSet<>();
			for( char c : word.toCharArray() )
			{
				chars.add( c );
			}
			return chars;
		};

		computedArrow = diagram.arrows().createComputed().function( function ).domain( String.class ).codomain( Character.class ).end();
		computedArrow.addSource( word1 );
		computedArrow.addSource( word2 );
		computedArrow.addSource( word3 );
		computedArrow.addSource( word4 );
	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testSources() throws Exception
	{

		Set0 sources = computedArrow.sources();
		assertEquals( sources.size(), 4 );
		assertTrue( sources.contains( word1 ) );
		assertTrue( sources.contains( word2 ) );
		assertTrue( sources.contains( word3 ) );
		assertTrue( sources.contains( word4 ) );

		computedArrow.remove( word3 );
		assertEquals( sources.size(), 3 );
		assertTrue( sources.contains( word1 ) );
		assertTrue( sources.contains( word2 ) );
		assertTrue( sources.contains( word4 ) );
	}

	@Test
	public void testTargets() throws Exception
	{
		Set0 targets = computedArrow.targets();
		assertEquals( targets.size(), unionString.length() );

		for( int i = 0; i < unionString.length(); ++i )
		{
			char c = unionString.charAt( i );
			assertTrue( targets.contains( c ) );
		}
	}

	void checkContainsAllLetters( Set0 set, String word )
	{
		for( char c : word.toCharArray() )
			assertTrue( set.contains( c ) );
	}

	@Test
	public void testTargetsWithSource() throws Exception
	{
		Set0 targets1 = computedArrow.targets( word1 );
		Set0 targets2 = computedArrow.targets( word2 );
		Set0 targets3 = computedArrow.targets( word3 );
		Set0 targets4 = computedArrow.targets( word4 );

		assertEquals( targets1.size(), word1.length() );
		checkContainsAllLetters( targets1, word1 );

		assertEquals( targets2.size(), word2.length() );
		checkContainsAllLetters( targets2, word2 );

		assertEquals( targets3.size(), word3.length() );
		checkContainsAllLetters( targets3, word3 );

		assertEquals( targets4.size(), word4.length() );
		checkContainsAllLetters( targets4, word4 );
	}

}
