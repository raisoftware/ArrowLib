package Arrows.Impl.Rule;

import Arrows.*;
import Arrows.Arrows.Names;
import Arrows.Impl.ObjectConfigBuilderImpl;
import Arrows.Objects;

import static Arrows.Arrows.Names.*;

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

		ObjectConfig rootObjConfig = new ObjectConfigBuilderImpl().end();
		ObjectConfig coolestObjConfig = new ObjectConfigBuilderImpl().end();
		ObjectConfig bestObjConfig = new ObjectConfigBuilderImpl().end();

		Objects objects = diagram.objects();

		objects.add( "two" );// id 0
		objects.config( "two", rootObjConfig );
		objects.name( "two", RootObject );

		objects.add( "unu" ); // id 1
		objects.config( "unu", coolestObjConfig );
		objects.name( "unu", CoolestObject );


		Arrow<String, String> arrow = diagram.createGeneric().end();
		diagram.arrows().name( arrow, English2Rom, Rom2English );
		arrow.editor().aim( "one", "unu" );// id 2 and 1
		arrow.editor().aim( "two", "doi" );// ids 0 and 3
		arrow.editor().aim( "three", "trei" );// ids are 4 and 5
		arrow.editor().aim( "four", "patru" );// ids are 6 and 7

		diagram.objects().config( "three", bestObjConfig );
		objects.name( "three", BestObject );

	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testObjectRegistrarRule() throws Exception
	{
		Arrows arrows = diagram.arrows();

		ArrowView<Object, Object> name2Object = arrows.arrowView( Names.Name_Object );
		ArrowView<Object, Object> id2Object = arrows.arrowView( Names.Id_Object );
		ArrowView<Object, ObjectConfig> object2Config = arrows.arrowView( Object_Config );

		assertEquals( name2Object.target( RootObject ), "two" );
		assertEquals( name2Object.target( CoolestObject ), "unu" );
		assertEquals( name2Object.target( BestObject ), "three" );

		List unnamedObjects = new ArrayList();
		unnamedObjects.add( 1 );
		unnamedObjects.add( 4 );
		unnamedObjects.add( "doi" );
		unnamedObjects.add( "trei" );
		unnamedObjects.add( "patru" );
		assertEquals( id2Object.target( 2 ), "one" );
		assertEquals( id2Object.target( 3 ), "doi" );
		assertEquals( id2Object.target( 5 ), "trei" );
		assertEquals( id2Object.target( 6 ), "four" );
		assertEquals( id2Object.target( 7 ), "patru" );

		ArrowView<Object, Enum> object2Name = arrows.arrowView( Names.Object_Name );

		assertEquals( object2Name.target( "two" ), RootObject );
		assertEquals( object2Name.target( "unu" ), CoolestObject );
		assertEquals( object2Name.target( "three" ), BestObject );


		System.out.println( name2Object.inverse() );
		System.out.println( object2Config );
	}
}
