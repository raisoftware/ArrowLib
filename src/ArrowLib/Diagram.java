package ArrowLib;

import java.util.ArrayList;

import static ArrowLib.ArrowName.*;

public class Diagram
{
	Arrow arrow;

	public static void main( String args[] )
	{
		Diagram diagram = new Diagram();
	}

	public Diagram()
	{
		ArrowBuilder arrowBuilder = new ArrowBuilder();
		arrow = arrowBuilder.name( Contains ).inverseName( IsContainedBy ).
			domain( String.class ).codomain( Character.class ).end();

		ArrayList<Character> chars = new ArrayList<>();
		chars.add( 's' );
		chars.add( 'i' );
		chars.add( 'r' );

		MethodSequence<Arrow> sequence = new MethodSequence();
		sequence.subscribe( new FirstRule() );
		Arrow arrowProxy = sequence.createPublisher( arrow, Arrow.class );

		arrowProxy.connect( "sir", chars );

		//sequence.subscribe( new ObjectsArrowsRule() );
		//MethodSequence arrowsSequence = sequence.createPublisher()
	}

}
