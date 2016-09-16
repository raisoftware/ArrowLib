package Shared.Collection0;

import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Set0Test
{
	Set0<Integer> oddNumbers;
	Set0<Integer> evenNumbers;
	Set0<String> words;
	Set0<String> words2;

	@Before
	public void setUp()
	{
		oddNumbers = new BasicSet0<>( new HashSet(), Integer.class );
		evenNumbers = new BasicSet0<>( new HashSet(), Integer.class );
		words = new BasicSet0<>( new HashSet(), String.class );
		words2 = new BasicSet0<>( new HashSet(), String.class );

		for( int i = 0; i < 10; i += 2 )
		{
			evenNumbers.add( i );
		}

		for( int i = 1; i < 10; i += 2 )
		{
			oddNumbers.add( i );
		}

		words.add( "Have" );
		words.add( "a" );
		words.add( "nice" );
		words.add( "day" );

		words2.add( "Some" );
		words2.add( "other" );
		words2.add( "words" );

	}

	@Test
	public void testSet0() throws Exception
	{
		assertEquals( 5, evenNumbers.size() );

		for( int i = 0; i < 10; i += 2 )
		{
			assertTrue( evenNumbers.contains( i ) );
		}

		assertEquals( 5, oddNumbers.size() );

		for( int i = 1; i < 10; i += 2 )
		{
			assertTrue( oddNumbers.contains( i ) );
		}

		oddNumbers.remove( 1 );

		for( int i = 3; i < 10; i += 2 )
		{
			assertTrue( oddNumbers.contains( i ) );
		}



		assertEquals( 4, words.size() );
		assertTrue( words.contains( "Have" ) );
		assertTrue( words.contains( "a" ) );
		assertTrue( words.contains( "nice" ) );
		assertTrue( words.contains( "day" ) );

	}

	@Test
	public void testCompoundSets() throws Exception
	{
		Set0<Integer> voidIntersection = Sets.intersect( evenNumbers, oddNumbers );
		assertEquals( 0, voidIntersection.size() );
		assertEquals( 1, voidIntersection.domains().size() );
		assertTrue( voidIntersection.domains().contains( Integer.class ) );


		Set0<Integer> numbers = Sets.union( evenNumbers, oddNumbers );
		assertEquals( 10, numbers.size() );
		for( int i = 0; i < 10; ++i )
		{
			assertTrue( numbers.contains( i ) );
		}

		assertEquals( 1, numbers.domains().size() );
		assertTrue( numbers.domains().contains( Integer.class ) );


		Set0<Integer> diff = Sets.difference( evenNumbers, oddNumbers );
		assertEquals( evenNumbers.size(), diff.size() );
		assertTrue( Sets.containsAll( diff, evenNumbers ) );
		assertEquals( 1, diff.domains().size() );
		assertTrue( diff.domains().contains( Integer.class ) );

		Set0 union = Sets.union( evenNumbers, words, words2 );
		assertEquals( words.size() + words2.size() + evenNumbers.size(), union.size() );
		assertEquals( 2, union.domains().size() );
		assertTrue( union.domains().contains( Integer.class ) );
		assertTrue( union.domains().contains( String.class ) );

		assertTrue( Sets.containsAll( union, evenNumbers ) );
		assertTrue( Sets.containsAll( union, words ) );
		assertTrue( Sets.containsAll( union, words2 ) );


		Set0<Integer> evenNumbersDiff = Sets.difference( numbers, oddNumbers );
		assertEquals( evenNumbers.size(), evenNumbersDiff.size() );
		assertEquals( 1, evenNumbersDiff.domains().size() );
		assertTrue( evenNumbersDiff.domains().contains( Integer.class ) );
	}


}
