package Arrows.Test;

import Arrows.*;
import Arrows.Impl.*;

import static Arrows.Test.ObjectName.*;
import static Arrows.Arrows.StandardArrowName.*;

import java.util.ArrayList;

import static Arrows.Test.ArrowName.*;

public class MainTest
{
	public static void main( String args[] ) throws Exception
	{
		Diagram diagram = new Diagram();

		//Create class2ObjectRule
		diagram.addClass2ObjectRule();
		//------------------------------------------------

		//Create ObjectRegistrarRule
		diagram.objectRegistrarRule();
		//------------------------------

		//create custom arrows
		ArrowBuilder containsArrowBuilder = diagram.arrows().create( Contains, IsContainedBy );
		Arrow arrow = containsArrowBuilder.domain( String.class ).codomain( Character.class ).end();

		ArrowBuilder squareArrowBuilder = diagram.arrows().create( Square, SquareRoot );
		Arrow squareArrow = squareArrowBuilder.domain( Float.class ).codomain( Float.class ).end();

		ArrayList<Character> chars = new ArrayList<>();
		chars.add( 's' );
		chars.add( 'i' );
		chars.add( 'r' );
		//----------------------------------------------------

		//test arrows
		arrow.connect( "sir", chars );
		squareArrow.connect( "irs", chars );

		squareArrow.connect( 9, 81 );
		squareArrow.connect( 2, 4 );
		squareArrow.connect( 7, 49 );

		System.out.println( "Direct:" + squareArrow.inverse().inverse().inverse().inverse().relations() );
		System.out.println( "reverse:" + squareArrow.inverse().relations() );
		//----------------------------------------------------

		//Test class2ObjectRule
		Arrow class2Object = diagram.arrows().arrow( Class2Object );
		System.out.println( "class2Object.relations()" + class2Object.relations() );
		System.out.println( "class2Object.inverse()" + class2Object.inverse().relations() );
		//----------

		//Test ObjectRegistrarRule
		ObjectConfig rootObjConfig = diagram.objects().create().name( RootObject ).end();
		diagram.objects().add( 's', rootObjConfig );
		diagram.objects().add( "sir", rootObjConfig );
		diagram.objects().add( 'r', rootObjConfig );
		Arrow name2Object = diagram.arrows().arrow( Name2Object );
		Arrow object2Config = diagram.arrows().arrow( Object2Config );
		System.out.println( "\n\n\nname2Object.inverse().relations()\n" + name2Object.inverse().relations() + "\n\n\n" );
		System.out.println( "object2Config.relations()" + object2Config.relations() );
		//----------

		Arrow containsArrow = diagram.arrows().arrow( Contains );

		System.out.println( "" + containsArrow.relations() );

		//sequence.subscribe( new ObjectsArrowsRule() );
		//MethodSequence arrowsSequence = sequence.createPublisher()
		diagram.arrows().add( Class2Object, Object2Class, new ManyToManyArrow( diagram.arrows().defaultArrowConfig() ) );
	}

}
