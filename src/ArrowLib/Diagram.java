package ArrowLib;

import java.util.*;

import static ArrowLib.ArrowName.*;

public class Diagram
{
	Map<ArrowName, Arrow> arrows = new HashMap();

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

		arrows.put( arrow.config().getName(), arrow );
		arrows.put( arrow.config().getInverseName(), arrow.inverse() );
		arrows.put( squareArrow.config().getName(), squareArrow );
		arrows.put( squareArrow.config().getInverseName(), squareArrow.inverse() );

		ArrayList<Character> chars = new ArrayList<>();
		chars.add( 's' );
		chars.add( 'i' );
		chars.add( 'r' );

		MethodSequence<Arrow> sequence = new MethodSequence();
		sequence.subscribe( new FirstRule() );
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

	class Objects
	{
		Arrow objects2Arrows;//  = arrows[Objects] // edit

		public Objects()
		{
			objects2Arrows = new ArrowBuilder().name( ArrowName.Objects2Arrows ).end();
		}

		void register( Object object ) // obtains class config from ClassConfig arrow then calls register
		{

		}

		void register( Object object, ObjectConfig config ) //    objectsArrow.connect( name, value )
		{
			objects2Arrows.connect( object, null );
		}

		void remove( Object obj ) // remove object and all the standard relations involving it. if arrows are tracked, also delete the relations it is involved
		{

		}

//		// query
//		Set objects()
//		{
//			return arrow.targets();
//		}
//
//		Object[] objects( Class clazz ) // return classes.eval( clazz )
//		{
//			classes.eval( clazz );
//		}
	}

}
