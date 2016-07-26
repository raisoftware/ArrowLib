package Arrows.Impl.Rule;

import Arrows.*;
import Arrows.Arrows.StandardArrowName;
import java.util.*;
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

		Arrow<Integer, String> arrow = diagram.arrows().createGeneric().end();
		diagram.arrows().name( arrow, Stringify, Destringify );
		arrow.editor().connect( 1, "unu" );
		arrow.editor().connect( 2, "doi" );
		arrow.editor().connect( 3, "trei" );
		arrow.editor().connect( 4, "patru" );
	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testClass2ObjectRule() throws Exception
	{
		Arrows arrows = diagram.arrows();
		ArrowView<Enum, Arrow> name2Arrow = arrows.arrow( StandardArrowName.Name2Arrow );

		//Test name2Arrow
		Arrow<Integer, String> stringifyArrow = name2Arrow.target( Stringify );
		assertEquals( stringifyArrow.target( 1 ), "unu" );
		assertEquals( name2Arrow.target( Destringify ).target( "doi" ), 2 );

		//Test class2Object
		ArrowView class2Object = arrows.arrow( StandardArrowName.Class2Object );
		Set<String> strings = class2Object.targets( String.class );

		for( String target : stringifyArrow.targets() )
		{
			assertTrue( strings.contains( target ) );
		}

		Set<Integer> ints = class2Object.targets( Integer.class );
		for( Integer source : stringifyArrow.sources() )
		{
			assertTrue( ints.contains( source ) );
		}
	}
}
