package Arrows;

import java.util.*;

import static Arrows.ArrowName.*;

public class Diagram
{
	Arrows arrows = new Arrows();

	public static void main( String args[] )
	{
		Diagram diagram = new Diagram();
	}

	public Diagram()
	{
		ArrowBuilder arrowBuilder = new ArrowBuilder();
		Arrow arrow = arrowBuilder.name( Contains ).inverseName( IsContainedBy ).
			domain( String.class ).codomain( Character.class ).end();

		Arrow squareArrow = arrowBuilder.name( Square ).inverseName( SquareRoot ).
			domain( Float.class ).codomain( Float.class ).end();

		arrows.add( arrow );
		arrows.add( squareArrow );

		ArrayList<Character> chars = new ArrayList<>();
		chars.add( 's' );
		chars.add( 'i' );
		chars.add( 'r' );

		MethodSequence<Arrow> sequence = new MethodSequence();
		//sequence.subscribe( new FirstRule() );
		Arrow class2Object = new Class2Object();
		sequence.subscribe( class2Object );

		Arrow arrowProxy = sequence.createPublisher( arrow, Arrow.class );
		Arrow squareArrowProxy = sequence.createPublisher( squareArrow, Arrow.class );

		arrowProxy.connect( "sir", chars );
		arrowProxy.connect( "irs", chars );

		squareArrowProxy.connect( new Float( 9 ), new Float( 81 ) );
		squareArrowProxy.connect( new Float( 2 ), new Float( 4 ) );
		squareArrowProxy.connect( new Float( 7 ), new Float( 49 ) );

		System.out.println( "Direct:" + squareArrowProxy.inverse().inverse().inverse().inverse().relations() );
		System.out.println( "reverse:" + squareArrowProxy.inverse().relations() );

		System.out.println( "" + class2Object.relations() );

		//sequence.subscribe( new ObjectsArrowsRule() );
		//MethodSequence arrowsSequence = sequence.createPublisher()
	}
}
