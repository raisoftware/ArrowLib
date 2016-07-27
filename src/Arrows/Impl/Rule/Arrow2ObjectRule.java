package Arrows.Impl.Rule;

import Arrows.*;
import Shared.Set0Utils;
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
			this.inboundArrow2object = (Arrow) arrows.arrow( InboundArrow2Object );
			this.outboundArrow2object = (Arrow) arrows.arrow( OutboundArrow2Object );
		}
		catch( Exception ex )
		{
			Logger.getLogger( Arrow2ObjectRule.class.getName() ).log( Level.SEVERE, null, ex );
		}
	}


	@Override
	public void connect( Object source, Iterable targets )
	{
		if( source == null || targets == null || !targets.iterator().hasNext() )
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
	public void connect( Iterable sources, Object target )
	{
		if( target == null || sources == null || !sources.iterator().hasNext() )
			return;

		outboundArrow2object.editor().connect( listenedArrow, sources );
		inboundArrow2object.editor().connect( listenedArrow, target );
	}

	@Override
	public void remove( Object source, Object target )
	{
		if( target == null || source == null )
			return;

		if( Set0Utils.isEmpty( listenedArrow.inverse().targets( target ) ) )
			inboundArrow2object.editor().remove( listenedArrow, target );

		if( Set0Utils.isEmpty( listenedArrow.targets( source ) ) )
			outboundArrow2object.editor().remove( listenedArrow, source );
	}
}
