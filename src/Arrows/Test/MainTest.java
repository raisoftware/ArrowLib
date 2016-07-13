package Arrows.Test;

import Arrows.*;

import static Arrows.Arrows.StandardArrowName.*;

import Arrows.Impl.Class2ObjectRule;
import Arrows.Impl.ManyToManyArrow;
import Shared.MethodBus.Sequence.MethodSequence;

import static Shared.MethodBus.Sequence.MethodSequence.*;

import java.util.ArrayList;

import static Arrows.Test.ArrowName.*;

public class MainTest
{
	public static void main( String args[] ) throws Exception
	{
		Diagram diagram = new Diagram();

		ArrowBuilder arrowBuilder = diagram.arrows().create();
		Arrow arrow = arrowBuilder.name( Contains ).inverseName( IsContainedBy ).
			domain( String.class ).codomain( Character.class ).end();

		Arrow squareArrow = arrowBuilder.name( Square ).inverseName( SquareRoot ).
			domain( Float.class ).codomain( Float.class ).end();

		ArrayList<Character> chars = new ArrayList<>();
		chars.add( 's' );
		chars.add( 'i' );
		chars.add( 'r' );

		diagram.arrows().add( Class2Object, Object2Class, new ManyToManyArrow() );
		MethodSequence<Arrow> sequence = new MethodSequence();
		//sequence.subscribe( new FirstRule() );

		//new ArrowBuilder().name( StandardArrow.Object2Arrow ).end();
		Arrow class2Object = diagram.arrows().arrow( Class2Object );

		Class2ObjectRule class2ObjectRule = new Class2ObjectRule( class2Object );
		sequence.subscribe( class2ObjectRule, ExecutionTime.ExecuteBefore );

		Arrow arrowProxy = sequence.createPublisher( arrow, Arrow.class );
		Arrow squareArrowProxy = sequence.createPublisher( squareArrow, Arrow.class );

		arrowProxy.connect( "sir", chars );
		arrowProxy.connect( "irs", chars );

		squareArrowProxy.connect( new Float( 9 ), new Float( 81 ) );
		squareArrowProxy.connect( new Float( 2 ), new Float( 4 ) );
		squareArrowProxy.connect( new Float( 7 ), new Float( 49 ) );

		System.out.println( "Direct:" + squareArrowProxy.inverse().inverse().inverse().inverse().relations() );
		System.out.println( "reverse:" + squareArrowProxy.inverse().relations() );

		System.out.println( "class2Object.relations()" + class2Object.relations() );
		System.out.println( "class2Object.inverse()" + class2Object.inverse().relations() );

		Arrow containsArrow = diagram.arrows().arrow( Contains );

		System.out.println( "" + containsArrow.relations() );

		//sequence.subscribe( new ObjectsArrowsRule() );
		//MethodSequence arrowsSequence = sequence.createPublisher()
	}

}
