/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arrows;

import static Arrows.Arrows.StandardArrowName.*;

import Arrows.Arrows.StandardArrowName;

import static Arrows.Objects.StandardObjectName.*;

import java.util.*;

import static Arrows.Test.ObjectName.*;

import static Arrows.Test.ArrowName.*;

import org.junit.*;

import static org.junit.Assert.*;

public class DiagramTest
{

	public DiagramTest()
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

	Diagram diagram;

	@Before
	public void setUp()
	{
		diagram = new Diagram();

		diagram.addClass2ObjectRule();
		diagram.addObjectRegistrarRule();
		diagram.addArrow2ObjectRule();

		ObjectConfig rootObjConfig = diagram.objects().create().name( RootObject ).end();
		ObjectConfig coolestObjConfig = diagram.objects().create().name( CoolestObject ).end();
		ObjectConfig bestObjConfig = diagram.objects().create().name( BestObject ).end();

		diagram.objects().add( 2, rootObjConfig );
		diagram.objects().add( "unu", coolestObjConfig );

		Arrow<Integer, String> arrow = diagram.arrows().create( Stringify, Destringify ).end();
		arrow.connect( 1, "unu" );
		arrow.connect( 2, "doi" );
		arrow.connect( 3, "trei" );
		arrow.connect( 4, "patru" );

		diagram.objects().add( 3, bestObjConfig );

	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testClass2ObjectRule() throws Exception
	{
		Arrows arrows = diagram.arrows();
		Arrow<Enum, Arrow> name2Arrow = arrows.arrow( StandardArrowName.Name2Arrow );

		//Test name2Arrow
		Arrow<Integer, String> stringifyArrow = name2Arrow.target( Stringify );
		assertEquals( stringifyArrow.target( 1 ), "unu" );
		assertEquals( name2Arrow.target( Destringify ).target( "doi" ), 2 );

		//Test class2Object
		Arrow class2Object = arrows.arrow( StandardArrowName.Class2Object );
		Set<Integer> ints = class2Object.targets( String.class );

		for( String target : stringifyArrow.targets() )
		{
			assertTrue( ints.contains( target ) );
		}

		Set<String> strings = class2Object.targets( Integer.class );
		for( Integer source : stringifyArrow.sources() )
		{
			assertTrue( strings.contains( source ) );
		}
	}

	@Test
	public void testObjectRegistrarRule() throws Exception
	{
		Arrows arrows = diagram.arrows();
		Arrow<Enum, Arrow> name2Arrow = arrows.arrow( StandardArrowName.Name2Arrow );
		Arrow<Integer, String> stringifyArrow = name2Arrow.target( Stringify );

		Arrow<Enum, Object> name2Object = arrows.arrow( StandardArrowName.Name2Object );
		Arrow<Object, ObjectConfig> object2Config = arrows.arrow( Object2Config );

		assertEquals( name2Object.target( RootObject ), 2 );
		assertEquals( name2Object.target( CoolestObject ), "unu" );
		assertEquals( name2Object.target( BestObject ), 3 );

		List unnamedObjects = new ArrayList();
		unnamedObjects.add( 1 );
		unnamedObjects.add( 4 );
		unnamedObjects.add( "doi" );
		unnamedObjects.add( "trei" );
		unnamedObjects.add( "patru" );
		assertEquals( name2Object.targets( Unnamed ).size(), unnamedObjects.size() );
		assertTrue( name2Object.targets( Unnamed ).containsAll( unnamedObjects ) );

		Arrow<Object, Enum> object2Name = arrows.arrow( StandardArrowName.Object2Name );

		assertEquals( object2Name.target( 2 ), RootObject );
		assertEquals( object2Name.target( "unu" ), CoolestObject );
		assertEquals( object2Name.target( 3 ), BestObject );
		assertEquals( object2Name.target( "doi" ), Unnamed );

		System.out.println( name2Object.inverse() );
		System.out.println( object2Config );

	}

	@Test
	public void testArrow2ObjectRule() throws Exception
	{
		Arrows arrows = diagram.arrows();
		Arrow<Arrow, Object> object2outboundArrow = arrows.arrow( StandardArrowName.Object2OutboundArrow );
		Arrow<Object, Arrow> outboundArrow2object = object2outboundArrow.inverse();

		Arrow<Enum, Arrow> name2Arrow = arrows.arrow( StandardArrowName.Name2Arrow );
		Arrow<Integer, String> stringifyArrow = name2Arrow.target( Stringify );

		Set sources = outboundArrow2object.targets( stringifyArrow );
		assertTrue( sources.contains( 1 ) );
		assertFalse( sources.contains( "unu" ) );

		Arrow<Arrow, Object> object2inboundArrow = arrows.arrow( StandardArrowName.Object2InboundArrow );
		Arrow<Object, Arrow> inboundArrow2object = object2inboundArrow.inverse();

		Set targets = inboundArrow2object.targets( stringifyArrow );
		assertTrue( targets.contains( "doi" ) );
		assertFalse( targets.contains( 2 ) );
	}
}
