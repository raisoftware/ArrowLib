package Arrows.Impl.Rule;

import Arrows.*;
import Arrows.Arrows.Names;
import Shared.Collection0.Set0;
import org.junit.*;

import static Arrows.Test.ArrowName.*;
import static org.junit.Assert.*;

public class Class2ObjectRuleTest
{

	public Class2ObjectRuleTest()
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
		diagram = Diagram.create();
		diagram.objects().add( 2 );
		diagram.objects().add( "unu" );

		Arrow<Integer, String> arrow = diagram.createGeneric().name( Stringify ).inverseName( Destringify ).end();
		arrow.aim( 1, "unu" );
		arrow.aim( 2, "doi" );
		arrow.aim( 3, "trei" );
		arrow.aim( 4, "patru" );
	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testClass2ObjectRule()
	{
		Arrows arrows = diagram.arrows();
		ArrowView<String, Arrow> name2Arrow = arrows.arrowView( Names.Name_Arrow );

		//Test name2Arrow
		Arrow<Integer, String> stringifyArrow = name2Arrow.target( Stringify.toString() );
		assertEquals( stringifyArrow.target( 1 ), "unu" );
		assertEquals( name2Arrow.target( Destringify.toString() ).target( "doi" ), 2 );

		//Test class2Object
		ArrowView class2Object = arrows.arrowView( Names.Class_Object );
		Set0<String> strings = class2Object.targets( String.class );

		for( String target : stringifyArrow.targets() )
		{
			assertTrue( strings.contains( target ) );
		}

		Set0<Integer> ints = class2Object.targets( Integer.class );
		for( Integer source : stringifyArrow.sources() )
		{
			assertTrue( ints.contains( source ) );
		}
	}
}
