package Arrows.Impl;

import Arrows.*;
import Shared.Collection0.Set0;
import Shared.Collection0.Sets;
import org.junit.*;

import static Arrows.Test.ArrowName.*;
import static Arrows.Utils.TestsUtils.*;
import static org.junit.Assert.*;

public class IntersectArrowTest
{
	private Diagram diagram;
	private Arrow<Character, Character> toUpperCaseArrow1;
	private Arrow<Character, Character> toUpperCaseArrow2;
	private final String word1 = "abcd";
	private final String word2 = "efgh";
	private final String word3 = "ijkl";
	private final String word4 = "mnop";
	private ArrowView intersectArrow;
	String mergedWord12 = word1 + word2;
	String mergedWord34 = word3 + word4;

	public IntersectArrowTest()
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

		intersectArrow = diagram.intersect( toUpperCaseArrow1, toUpperCaseArrow2 );
	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testSources() throws Exception
	{
		Set0 sources = intersectArrow.sources();
		assertEquals( sources.size(), mergedWord34.length() );

		for( int i = 0; i < mergedWord34.length(); ++i )
		{
			char c = mergedWord34.charAt( i );
			assertTrue( sources.contains( c ) );
		}
		assertEquals( 1, sources.domains().size() );
		assertTrue( sources.domains().contains( Character.class ) );

	}

	@Test
	public void testTargets() throws Exception
	{
		Set0 targets = intersectArrow.targets();
		assertEquals( targets.size(), mergedWord34.length() );

		for( int i = 0; i < mergedWord34.length(); ++i )
		{
			char c = Character.toUpperCase( mergedWord34.charAt( i ) );
			assertTrue( targets.contains( c ) );
		}

		assertEquals( 1, targets.domains().size() );
		assertTrue( targets.domains().contains( Character.class ) );
	}

	@Test
	public void testTargetsWithSource() throws Exception
	{
		for( int i = 0; i < mergedWord12.length(); ++i )
		{
			char c = mergedWord12.charAt( i );
			Set0 results = intersectArrow.targets( c );
			assertTrue( results.size() == 0 );
		}

		for( int i = 0; i < mergedWord34.length(); ++i )
		{
			char c = mergedWord34.charAt( i );
			Set0 results = intersectArrow.targets( c );
			assertEquals( results.size(), 1 );
			assertEquals( results.iterator().next(), Character.toUpperCase( c ) );
		}
	}

	@Test
	public void testInverse() throws Exception
	{
		Set0 sources = intersectArrow.sources();
		Set0 targets = intersectArrow.targets();
		Set0 inverseSources = intersectArrow.inverse().sources();
		Set0 inverseTargets = intersectArrow.inverse().targets();

		assertEquals( sources.size(), inverseTargets.size() );
		assertTrue( Sets.containsAll( sources, inverseTargets ) );
		assertEquals( targets.size(), inverseSources.size() );
		assertTrue( Sets.containsAll( targets, inverseSources ) );

		for( int i = 0; i < mergedWord34.length(); ++i )
		{
			char c = Character.toUpperCase( mergedWord34.charAt( i ) );
			Set0 results = intersectArrow.inverse().targets( c );
			assertEquals( results.size(), 1 );
			assertEquals( results.iterator().next(), Character.toLowerCase( c ) );
		}
	}


}
