package Arrows.Test;

import Arrows.*;
import Arrows.Impl.ObjectConfigBuilderImpl;
import Arrows.Utils.ArrowUtils;
import Shared.Collection0.Set0;
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

		arrow.aim( "sir", chars );

		arrow.aim( "irs", chars );
		arrow.aim( "ra", 'r' );
		arrow.aim( "ra", 'a' );

		squareArrow.aim( 9.f, 81.f );
		squareArrow.aim( 2.f, 4.f );
		squareArrow.aim( 7.f, 49.f );

		//----------------------------------------------------

		//Test class2ObjectRule
		Arrow class2Object = diagram.arrows().arrow( Class_Object );
		System.out.println( class2Object );
		System.out.println( class2Object.inverse() );
		System.out.println( "class2Object.relations()" + class2Object.relations() );
		System.out.println( "class2Object.inverse()" + class2Object.inverse().relations() );
		//----------

		//Test ObjectRegistrarRule
		ObjectConfig rootObjConfig = new ObjectConfigBuilderImpl().end();
		diagram.objects().config( 's', rootObjConfig );
		diagram.objects().name( 's', RootObject );
		Arrow name2Object = diagram.arrows().arrow( Name_Object );
		Arrow object2Config = diagram.arrows().arrow( Object_Config );
		//----------

		Arrow<String, Character> containsArrow = diagram.arrows().arrow( Contains );

		ArrowView joinArrow = diagram.join( containsArrow.inverse() );
		Set0 results = joinArrow.targets( 'r' );

		ArrowUtils.generateGraph( diagram, "graph/" );
	}

}
