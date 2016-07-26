package Arrows.Impl.Rule;

import Arrows.*;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Arrows.Arrows.StandardArrowName.*;

public class Arrow2ObjectRule implements Arrow.Editor
{
	Arrow listenedArrow = null;

	Arrow<Arrow, Object> inboundArrow2object;
	Arrow<Arrow, Object> outboundArrow2object;

	public Arrow2ObjectRule( Arrow listenedArrow, Arrows arrows )
	{
		this.listenedArrow = listenedArrow;
		try
		{
			this.inboundArrow2object = arrows.arrow( InboundArrow2Object );
			this.outboundArrow2object = arrows.arrow( OutboundArrow2Object );
		}
		catch( Exception ex )
		{
			Logger.getLogger( Arrow2ObjectRule.class.getName() ).log( Level.SEVERE, null, ex );
		}
	}


	@Override
	public void connect( Object source, Collection targets )
	{
		if( source == null || targets == null || targets.isEmpty() )
			return;

		outboundArrow2object.editor().connect( listenedArrow, source );
		inboundArrow2object.editor().connect( listenedArrow, targets );
	}

	@Override
	public void connect( Object source, Object target )
	{
		if( source == null || target == null )
			return;

		outboundArrow2object.editor().connect( listenedArrow, source );
		inboundArrow2object.editor().connect( listenedArrow, target );
	}

	@Override
	public void connect( Collection sources, Object target )
	{
		if( target == null || sources == null || sources.isEmpty() )
			return;

		outboundArrow2object.editor().connect( listenedArrow, sources );
		inboundArrow2object.editor().connect( listenedArrow, target );
	}

	@Override
	public void remove( Object source, Object target )
	{
		if( target == null || source == null )
			return;

		if( listenedArrow.inverse().targets( target ).isEmpty() )
			inboundArrow2object.editor().remove( listenedArrow, target );

		if( listenedArrow.targets( source ).isEmpty() )
			outboundArrow2object.editor().remove( listenedArrow, source );
	}
}
