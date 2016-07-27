package Arrows.Test;

import Arrows.*;
import Arrows.Impl.ObjectConfigBuilderImpl;
import Shared.Set0;
import java.util.*;

import static Arrows.Arrows.StandardArrowName.*;
import static Arrows.Test.ArrowName.*;
import static Arrows.Test.ObjectName.*;

public class MainTest
{
	public static void main( String args[] ) throws Exception
	{
		Diagram diagram = Diagram.create();

		//create custom arrows
		Arrow arrow = diagram.arrows().createGeneric().domain( String.class ).codomain( Character.class ).end();
		diagram.arrows().name( arrow, Contains, IsContainedBy );

		Arrow squareArrow = diagram.arrows().createGeneric().domain( Float.class ).codomain( Float.class ).end();
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

		System.out.println( "Direct:" + squareArrow.inverse().inverse().inverse().inverse().relations() );
		System.out.println( "reverse:" + squareArrow.inverse().relations() );
		//----------------------------------------------------

		//Test class2ObjectRule
		Arrow class2Object = (Arrow) diagram.arrows().arrow( Class2Object );
		System.out.println( "class2Object.relations()" + class2Object.relations() );
		System.out.println( "class2Object.inverse()" + class2Object.inverse().relations() );
		//----------

		//Test ObjectRegistrarRule
		ObjectConfig rootObjConfig = new ObjectConfigBuilderImpl( RootObject ).end();
		diagram.objects().config( 's', rootObjConfig );
		Arrow name2Object = (Arrow) diagram.arrows().arrow( Name2Object );
		Arrow object2Config = (Arrow) diagram.arrows().arrow( Object2Config );
		System.out.println( "\n\n\nname2Object.inverse().relations()\n" + name2Object.inverse().relations() + "\n\n\n" );
		System.out.println( "object2Config.relations()" + object2Config.relations() );
		//----------

		Arrow<String, Character> containsArrow = (Arrow) diagram.arrows().arrow( Contains );

		System.out.println( "" + containsArrow.relations() );

		ArrowView joinArrow = Arrows.join( containsArrow.inverse() );
		Set0 results = joinArrow.targets( 'r' );
		System.out.println( "results:" + results );
	}

}
