package Arrows.Test;

import Arrows.*;
import Arrows.Impl.*;

import static Arrows.Test.ObjectName.*;
import static Arrows.Arrows.StandardArrowName.*;

import Shared.MethodBus.Sequence.MethodSequence;

import static Shared.MethodBus.Sequence.MethodSequence.*;

import java.util.ArrayList;

import static Arrows.Test.ArrowName.*;

public class MainTest
{
	public static void main( String args[] ) throws Exception
	{
		Diagram diagram = new Diagram();

		MethodSequence<Arrow> sequence = new MethodSequence();

		//Create class2ObjectRule
		Class2ObjectRule class2ObjectRule = diagram.class2ObjectRule();
		sequence.subscribe( class2ObjectRule, ExecutionTime.ExecuteBefore );
		//------------------------------------------------

		//Create ObjectRegistrarRule
		ObjectRegistrarRule objectRegistrarRule = diagram.objectRegistrarRule();
		sequence.subscribe( objectRegistrarRule, ExecutionTime.ExecuteBefore );
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

		//Add proxies over the custom arrows
		Arrow arrowProxy = sequence.createPublisher( arrow, Arrow.class );
		Arrow squareArrowProxy = sequence.createPublisher( squareArrow, Arrow.class );

		arrowProxy.connect( "sir", chars );
		arrowProxy.connect( "irs", chars );

		squareArrowProxy.connect( 9, 81 );
		squareArrowProxy.connect( 2, 4 );
		squareArrowProxy.connect( 7, 49 );

		System.out.println( "Direct:" + squareArrowProxy.inverse().inverse().inverse().inverse().relations() );
		System.out.println( "reverse:" + squareArrowProxy.inverse().relations() );
		//----------------------------------------------------

		//Test class2ObjectRule
		Arrow class2Object = diagram.arrows().arrow( Class2Object );
		System.out.println( "class2Object.relations()" + class2Object.relations() );
		System.out.println( "class2Object.inverse()" + class2Object.inverse().relations() );
		//----------

		//Test ObjectRegistrarRule
		ObjectConfig rootObjConfig = new ObjectConfigBuilder().name( RootObject ).end();
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
