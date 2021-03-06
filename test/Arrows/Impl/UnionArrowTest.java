package Arrows.Impl;

import Arrows.*;
import Shared.Collection0.Set0;
import Shared.Collection0.Sets;
import org.junit.*;

import static Arrows.Test.ArrowName.*;
import static Arrows.Utils.TestsUtils.*;
import static org.junit.Assert.*;

public class UnionArrowTest
{
	private Diagram diagram;
	private Arrow<Character, Character> toUpperCaseArrow1;
	private Arrow<Character, Character> toUpperCaseArrow2;
	private final String word1 = "abcd";
	private final String word2 = "efgh";
	private final String word3 = "ijkl";
	private final String word4 = "mnop";
	private final String otherLetters = "qrst";

	private final String unionString = word1 + word2 + word3 + word4;

	private ArrowView unionArrow;

	public UnionArrowTest()
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

		toUpperCaseArrow1 = diagram.createGeneric().name( ToUpperCase ).inverseName( ToLowerCase ).domain( Character.class ).codomain( Character.class ).end();
		connectLettersToUpperCaseLetters( toUpperCaseArrow1, word1 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1, word2 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1, word3 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1, word4 );


		toUpperCaseArrow2 = diagram.createGeneric().name( ToUpperCase2 ).inverseName( ToLowerCase2 ).domain( Character.class ).codomain( Character.class ).end();
		connectLettersToUpperCaseLetters( toUpperCaseArrow2, word3 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow2, word4 );

		unionArrow = diagram.union( toUpperCaseArrow1, toUpperCaseArrow2 );

	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testSources() throws Exception
	{
		Set0 sources = unionArrow.sources();
		assertEquals( sources.size(), unionString.length() );

		for( int i = 0; i < unionString.length(); ++i )
		{
			char c = unionString.charAt( i );
			assertTrue( sources.contains( c ) );
		}
	}

	@Test
	public void testTargets() throws Exception
	{
		Set0 targets = unionArrow.targets();
		assertEquals( targets.size(), unionString.length() );

		for( int i = 0; i < unionString.length(); ++i )
		{
			char c = Character.toUpperCase( unionString.charAt( i ) );
			assertTrue( targets.contains( c ) );
		}
	}

	@Test
	public void testTargetsWithSource() throws Exception
	{
		for( int i = 0; i < otherLetters.length(); ++i )
		{
			char c = otherLetters.charAt( i );
			Set0 results = unionArrow.targets( c );
			assertTrue( results.size() == 0 );
		}

		for( int i = 0; i < unionString.length(); ++i )
		{
			char c = unionString.charAt( i );
			Set0 results = unionArrow.targets( c );
			assertEquals( results.size(), 1 );
			assertEquals( results.iterator().next(), Character.toUpperCase( c ) );
		}
	}

	@Test
	public void testInverse() throws Exception
	{
		Set0 sources = unionArrow.sources();
		Set0 targets = unionArrow.targets();
		Set0 inverseSources = unionArrow.inverse().sources();
		Set0 inverseTargets = unionArrow.inverse().targets();

		assertEquals( sources.size(), inverseTargets.size() );
		assertTrue( Sets.containsAll( sources, inverseTargets ) );
		assertEquals( targets.size(), inverseSources.size() );
		assertTrue( Sets.containsAll( targets, inverseSources ) );

		for( int i = 0; i < unionString.length(); ++i )
		{
			char c = Character.toUpperCase( unionString.charAt( i ) );
			Set0 results = unionArrow.inverse().targets( c );
			assertEquals( results.size(), 1 );
			assertEquals( results.iterator().next(), Character.toLowerCase( c ) );
		}

		assertEquals( 1, sources.domains().size() );
		assertTrue( sources.domains().contains( Character.class ) );

		assertEquals( 1, targets.domains().size() );
		assertTrue( targets.domains().contains( Character.class ) );

		assertEquals( 1, inverseSources.domains().size() );
		assertTrue( inverseSources.domains().contains( Character.class ) );

		assertEquals( 1, inverseTargets.domains().size() );
		assertTrue( inverseTargets.domains().contains( Character.class ) );
	}

	public void testDomain()
	{
		//TOFIX - make some better tests, where domain != codomain
		assertEquals( Character.class, unionArrow.domain() );
		assertEquals( Character.class, unionArrow.codomain() );
	}
}
