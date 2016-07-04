package ArrowLib.Utils;

import java.util.*;
import org.junit.*;

import static org.junit.Assert.*;

public class ManyToManyMapTest
{

	public ManyToManyMapTest()
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
	}

	@After
	public void tearDown()
	{
	}

	/**
	 * Test of put method, of class ManyToManyMap.
	 */
	@Test
	public void testPut()
	{
		System.out.println( "put" );
		Object key = new Object();
		Object value = new Integer( 10 );
		ManyToManyMap instance = new ManyToManyMap();
		boolean expResult = true;
		boolean result = instance.put( key, value );
		assertEquals( expResult, result );
	}

	/**
	 * Test of putAll method, of class ManyToManyMap.
	 */
	@Test
	public void testPutAll()
	{
		System.out.println( "putAll" );
		Object key = new Object();
		List<Integer> targets = new ArrayList();
		for( int i = 0; i < 10; ++i )
			targets.add( new Integer( i ) );

		Iterable values = targets;

		ManyToManyMap instance = new ManyToManyMap();
		boolean expResult = true;
		boolean result = instance.putAll( key, values );
		assertEquals( expResult, result );

		Set<Object> keySet = instance.getKeys( targets.get( 0 ) );
		assertTrue( keySet.contains( key ) );

		Set<Integer> set = instance.getValues( key );
		boolean containsAll = set.containsAll( targets );
		assertTrue( containsAll );

	}

}
