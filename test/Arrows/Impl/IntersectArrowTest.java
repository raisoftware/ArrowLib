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

		toUpperCaseArrow1 = diagram.createGeneric().domain( Character.class ).codomain( Character.class ).end();
		diagram.arrows().name( toUpperCaseArrow1, ToUpperCase, ToLowerCase );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1.editor(), word1 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1.editor(), word2 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1.editor(), word3 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1.editor(), word4 );


		toUpperCaseArrow2 = diagram.createGeneric().domain( Character.class ).codomain( Character.class ).end();
		diagram.arrows().name( toUpperCaseArrow2, ToUpperCase2, ToLowerCase2 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow2.editor(), word3 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow2.editor(), word4 );

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
		assertEquals( Character.class, sources.domain() );

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
		assertEquals( Character.class, targets.domain() );
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
		assertTrue(Sets.containsAll( sources, inverseTargets ) );
		assertEquals( targets.size(), inverseSources.size() );
		assertTrue(Sets.containsAll( targets, inverseSources ) );

		for( int i = 0; i < mergedWord34.length(); ++i )
		{
			char c = Character.toUpperCase( mergedWord34.charAt( i ) );
			Set0 results = intersectArrow.inverse().targets( c );
			assertEquals( results.size(), 1 );
			assertEquals( results.iterator().next(), Character.toLowerCase( c ) );
		}
	}


}
