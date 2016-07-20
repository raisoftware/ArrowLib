package Arrows.Impl;

import Arrows.*;
import java.util.Set;
import org.junit.*;

import static Arrows.Test.ArrowName.*;
import static Arrows.Utils.TestsUtils.*;
import static org.junit.Assert.*;

public class IntersectArrowTest
{
	private Diagram diagram;
	private EditableArrow<Character, Character> toUpperCaseArrow1;
	private EditableArrow<Character, Character> toUpperCaseArrow2;
	private final String word1 = "abcd";
	private final String word2 = "efgh";
	private final String word3 = "ijkl";
	private final String word4 = "mnop";
	private Arrow intersectArrow;
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
		diagram = DiagramFactory.create();

		toUpperCaseArrow1 = diagram.arrows().create( ToUpperCase, ToLowerCase ).end();
		connectLettersToUpperCaseLetters( toUpperCaseArrow1, word1 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1, word2 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1, word3 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1, word4 );


		toUpperCaseArrow2 = diagram.arrows().create( ToUpperCase2, ToLowerCase2 ).end();
		connectLettersToUpperCaseLetters( toUpperCaseArrow2, word3 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow2, word4 );

		intersectArrow = Arrows.intersect( toUpperCaseArrow1, toUpperCaseArrow2 );
	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testSources() throws Exception
	{
		Set sources = intersectArrow.sources();
		assertEquals( sources.size(), mergedWord34.length() );

		for( int i = 0; i < mergedWord34.length(); ++i )
		{
			char c = mergedWord34.charAt( i );
			assertTrue( sources.contains( c ) );
		}
	}

	@Test
	public void testTargets() throws Exception
	{
		Set targets = intersectArrow.targets();
		assertEquals( targets.size(), mergedWord34.length() );

		for( int i = 0; i < mergedWord34.length(); ++i )
		{
			char c = Character.toUpperCase( mergedWord34.charAt( i ) );
			assertTrue( targets.contains( c ) );
		}
	}

	@Test
	public void testTargetsWithSource() throws Exception
	{
		for( int i = 0; i < mergedWord12.length(); ++i )
		{
			char c = mergedWord12.charAt( i );
			Set results = intersectArrow.targets( c );
			assertTrue( results.isEmpty() );
		}

		for( int i = 0; i < mergedWord34.length(); ++i )
		{
			char c = mergedWord34.charAt( i );
			Set results = intersectArrow.targets( c );
			assertEquals( results.size(), 1 );
			assertEquals( results.iterator().next(), Character.toUpperCase( c ) );
		}
	}

}
