package Arrows.Test;

import Arrows.*;
import Arrows.Impl.ObjectConfigBuilderImpl;
import Arrows.Utils.ArrowUtils;
import Shared.Set0;
import java.util.*;

import static Arrows.Arrows.Names.*;
import static Arrows.Test.ArrowName.*;
import static Arrows.Test.ObjectName.*;

public class MainTest
{
	public static void main( String args[] ) throws Exception
	{
		Diagram diagram = Diagram.create();

		//create custom arrows
		Arrow arrow = diagram.createGeneric().domain( String.class ).codomain( Character.class ).end();
		diagram.arrows().name( arrow, Contains, IsContainedBy );

		Arrow squareArrow = diagram.createGeneric().domain( Float.class ).codomain( Float.class ).end();
		diagram.arrows().name( squareArrow, Square, SquareRoot );

		ArrayList<Character> chars = new ArrayList<>();
		chars.add( 's' );
		chars.add( 'i' );
		chars.add( 'r' );
		//----------------------------------------------------

		//test arrows

		arrow.editor().connect( "sir", chars );

		arrow.editor().connect( "irs", chars );
		arrow.editor().connect( "ra", 'r' );
		arrow.editor().connect( "ra", 'a' );

		squareArrow.editor().connect( 9.f, 81.f );
		squareArrow.editor().connect( 2.f, 4.f );
		squareArrow.editor().connect( 7.f, 49.f );

		//----------------------------------------------------

		//Test class2ObjectRule
		Arrow class2Object = (Arrow) diagram.arrows().arrow( Class_Object );
		System.out.println( class2Object );
		System.out.println( class2Object.inverse() );
		System.out.println( "class2Object.relations()" + class2Object.relations() );
		System.out.println( "class2Object.inverse()" + class2Object.inverse().relations() );
		//----------

		//Test ObjectRegistrarRule
		ObjectConfig rootObjConfig = new ObjectConfigBuilderImpl().end();
		diagram.objects().config( 's', rootObjConfig );
		diagram.objects().name( 's', RootObject );
		Arrow name2Object = (Arrow) diagram.arrows().arrow( Name_Object );
		Arrow object2Config = (Arrow) diagram.arrows().arrow( Object_Config );
		//----------

		Arrow<String, Character> containsArrow = (Arrow) diagram.arrows().arrow( Contains );

		ArrowView joinArrow = diagram.join( containsArrow.inverse() );
		Set0 results = joinArrow.targets( 'r' );

		ArrowUtils.generateGraph( diagram, "graph/" );
	}

}
