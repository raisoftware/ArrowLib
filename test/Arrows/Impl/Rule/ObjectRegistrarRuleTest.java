package Arrows.Impl.Rule;

import Arrows.*;
import Arrows.Arrows.StandardArrowName;
import Arrows.Impl.ObjectConfigBuilderImpl;

import static Arrows.Arrows.StandardArrowName.*;

import java.util.*;
import org.junit.*;

import static Arrows.Test.ArrowName.*;
import static Arrows.Test.ObjectName.*;
import static org.junit.Assert.*;

public class ObjectRegistrarRuleTest
{

	public ObjectRegistrarRuleTest()
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

		ObjectConfig rootObjConfig = new ObjectConfigBuilderImpl( RootObject ).end();
		ObjectConfig coolestObjConfig = new ObjectConfigBuilderImpl( CoolestObject ).end();
		ObjectConfig bestObjConfig = new ObjectConfigBuilderImpl( BestObject ).end();

		diagram.objects().add( "two" );
		diagram.objects().config( "two", rootObjConfig );// id was 0 and was overwritten
		diagram.objects().add( "unu" );
		diagram.objects().config( "unu", coolestObjConfig );// id was 1 and was overwritten

		Arrow<String, String> arrow = diagram.arrows().create( English2Rom, Rom2English ).end();
		arrow.editor().connect( "one", "unu" );// ids are 2 and CoolestObject
		arrow.editor().connect( "two", "doi" );// ids are RootObject and 3
		arrow.editor().connect( "three", "trei" );// ids are 4 and 5
		arrow.editor().connect( "four", "patru" );// ids are 6 and 7

		diagram.objects().config( "three", bestObjConfig );// id was 6 and was overwritten

	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testObjectRegistrarRule() throws Exception
	{
		Arrows arrows = diagram.arrows();

		Arrow<Object, Object> name2Object = arrows.arrow( StandardArrowName.Name2Object );
		Arrow<Object, ObjectConfig> object2Config = arrows.arrow( Object2Config );

		assertEquals( name2Object.target( RootObject ), "two" );
		assertEquals( name2Object.target( CoolestObject ), "unu" );
		assertEquals( name2Object.target( BestObject ), "three" );

		List unnamedObjects = new ArrayList();
		unnamedObjects.add( 1 );
		unnamedObjects.add( 4 );
		unnamedObjects.add( "doi" );
		unnamedObjects.add( "trei" );
		unnamedObjects.add( "patru" );
		assertEquals( name2Object.target( 2 ), "one" );
		assertEquals( name2Object.target( 3 ), "doi" );
		assertEquals( name2Object.target( 5 ), "trei" );
		assertEquals( name2Object.target( 6 ), "four" );
		assertEquals( name2Object.target( 7 ), "patru" );

		Arrow<Object, Enum> object2Name = arrows.arrow( StandardArrowName.Object2Name );

		assertEquals( object2Name.target( "two" ), RootObject );
		assertEquals( object2Name.target( "unu" ), CoolestObject );
		assertEquals( object2Name.target( "three" ), BestObject );


		System.out.println( name2Object.inverse() );
		System.out.println( object2Config );
	}
}
