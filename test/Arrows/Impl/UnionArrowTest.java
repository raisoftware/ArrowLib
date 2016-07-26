package Arrows.Impl;

import Arrows.*;
import java.util.Set;
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

		toUpperCaseArrow1 = diagram.arrows().create( ToUpperCase, ToLowerCase ).end();
		connectLettersToUpperCaseLetters( toUpperCaseArrow1.editor(), word1 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1.editor(), word2 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1.editor(), word3 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow1.editor(), word4 );


		toUpperCaseArrow2 = diagram.arrows().create( ToUpperCase2, ToLowerCase2 ).end();
		connectLettersToUpperCaseLetters( toUpperCaseArrow2.editor(), word3 );
		connectLettersToUpperCaseLetters( toUpperCaseArrow2.editor(), word4 );

		unionArrow = Arrows.union( toUpperCaseArrow1, toUpperCaseArrow2 );

	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testSources() throws Exception
	{
		Set sources = unionArrow.sources();
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
		Set targets = unionArrow.targets();
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
			Set results = unionArrow.targets( c );
			assertTrue( results.isEmpty() );
		}

		for( int i = 0; i < unionString.length(); ++i )
		{
			char c = unionString.charAt( i );
			Set results = unionArrow.targets( c );
			assertEquals( results.size(), 1 );
			assertEquals( results.iterator().next(), Character.toUpperCase( c ) );
		}
	}

	@Test
	public void testInverse() throws Exception
	{
		Set sources = unionArrow.sources();
		Set targets = unionArrow.targets();
		Set inverseSources = unionArrow.inverse().sources();
		Set inverseTargets = unionArrow.inverse().targets();

		assertEquals( sources.size(), inverseTargets.size() );
		assertTrue( sources.containsAll( inverseTargets ) );
		assertEquals( targets.size(), inverseSources.size() );
		assertTrue( targets.containsAll( inverseSources ) );

		for( int i = 0; i < unionString.length(); ++i )
		{
			char c = Character.toUpperCase( unionString.charAt( i ) );
			Set results = unionArrow.inverse().targets( c );
			assertEquals( results.size(), 1 );
			assertEquals( results.iterator().next(), Character.toLowerCase( c ) );
		}
	}

}
