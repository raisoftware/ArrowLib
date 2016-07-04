/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrowLib;

import java.util.Collection;
import java.util.Set;

import org.junit.*;

import static org.junit.Assert.*;

/**
 *
 * @author gabrielburceanu
 */
public class ArrowTest
{

	public ArrowTest()
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
	 * Test of connect method, of class Arrow.
	 */
	@Test
	public void testConnect_GenericType_Collection()
	{
		System.out.println( "connect" );
		Object source = null;
		Collection targets = null;
		Arrow instance = null;
		instance.connect( source, targets );
		// TODO review the generated test code and remove the default call to fail.
		fail( "The test case is a prototype." );
	}

	/**
	 * Test of connect method, of class Arrow.
	 */
	@Test
	public void testConnect_GenericType_GenericType()
	{
		System.out.println( "connect" );
		Object source = null;
		Object target = null;
		Arrow instance = null;
		instance.connect( source, target );
		// TODO review the generated test code and remove the default call to fail.
		fail( "The test case is a prototype." );
	}

	/**
	 * Test of sources method, of class Arrow.
	 */
	@Test
	public void testSources()
	{
		System.out.println( "sources" );
		Arrow instance = null;
		Set expResult = null;
		Set result = instance.sources();
		assertEquals( expResult, result );
		// TODO review the generated test code and remove the default call to fail.
		fail( "The test case is a prototype." );
	}

	/**
	 * Test of targets method, of class Arrow.
	 */
	@Test
	public void testTargets_0args()
	{
		System.out.println( "targets" );
		Arrow instance = null;
		Set expResult = null;
		Set result = instance.targets();
		assertEquals( expResult, result );
		// TODO review the generated test code and remove the default call to fail.
		fail( "The test case is a prototype." );
	}

	/**
	 * Test of relations method, of class Arrow.
	 */
	@Test
	public void testRelations()
	{
//		System.out.println( "relations" );
//		Arrow instance = null;
//		Set<Pair<K, V>> expResult = null;
//		Set<Pair<K, V>> result = instance.relations();
//		assertEquals( expResult, result );
//		// TODO review the generated test code and remove the default call to fail.
		fail( "The test case is a prototype." );
	}

	/**
	 * Test of names method, of class Arrow.
	 */
	@Test
	public void testNames()
	{
		System.out.println( "names" );
		Arrow instance = null;
		instance.names();
		// TODO review the generated test code and remove the default call to fail.
		fail( "The test case is a prototype." );
	}

	/**
	 * Test of targets method, of class Arrow.
	 */
	@Test
	public void testTargets_GenericType()
	{
		System.out.println( "targets" );
		Object source = null;
		Arrow instance = null;
		instance.targets( source );
		// TODO review the generated test code and remove the default call to fail.
		fail( "The test case is a prototype." );
	}

	/**
	 * Test of inverse method, of class Arrow.
	 */
	@Test
	public void testInverse()
	{
		System.out.println( "inverse" );
		Arrow instance = null;
		instance.inverse();
		// TODO review the generated test code and remove the default call to fail.
		fail( "The test case is a prototype." );
	}

	/**
	 * Test of remove method, of class Arrow.
	 */
	@Test
	public void testRemove()
	{
		System.out.println( "remove" );
		Object source = null;
		Object target = null;
		Arrow instance = null;
		instance.remove( source, target );
		// TODO review the generated test code and remove the default call to fail.
		fail( "The test case is a prototype." );
	}

	/**
	 * Test of config method, of class Arrow.
	 */
	@Test
	public void testConfig()
	{
		System.out.println( "config" );
		Arrow instance = null;
		ArrowConfig expResult = null;
		ArrowConfig result = instance.config();
		assertEquals( expResult, result );
		// TODO review the generated test code and remove the default call to fail.
		fail( "The test case is a prototype." );
	}

	//Make sure adding the pair (source, target) twice, will only add it once
}
