package Arrows.Impl.Rule;

import Arrows.*;

import static Arrows.Arrows.StandardArrowName.*;

import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arrow2ObjectHook implements ArrowListener
{

	Objects objects;
	Arrow listenedArrow = null;

	Arrow<Arrow, Object> inboundArrow2object;
	Arrow<Arrow, Object> outboundArrow2object;

	public Arrow2ObjectHook( Arrows arrows, Objects objects )
	{

		try
		{
			this.inboundArrow2object = arrows.arrow( InboundArrow2Object );
			this.outboundArrow2object = arrows.arrow( OutboundArrow2Object );
		}
		catch( Exception ex )
		{
			Logger.getLogger(Arrow2ObjectHook.class.getName() ).log( Level.SEVERE, null, ex );
		}
		this.objects = objects;//TOFIX imi trebuie??
	}

	@Override
	public ArrowConfig config()
	{
		return null;
	}

	@Override
	public void connect( Object source, Collection targets )
	{
		if( source == null || targets == null || targets.isEmpty() )
			return;

		outboundArrow2object.connect( listenedArrow, source );
		inboundArrow2object.connect( listenedArrow, targets );
	}

	@Override
	public void connect( Object source, Object target )
	{
		if( source == null || target == null )
			return;

		outboundArrow2object.connect( listenedArrow, source );
		inboundArrow2object.connect( listenedArrow, target );
	}

	@Override
	public Set relations()
	{
		return null;
	}

	@Override
	public void remove( Object source, Object target )
	{
	}

	@Override
	public Set sources()
	{
		return null;
	}

	@Override
	public Set targets()
	{
		return null;
	}

	@Override
	public Arrow inverse()
	{
		return null;
	}

	@Override
	public Set targets( Object source )
	{
		return null;
	}

	@Override
	public Object target( Object source ) throws Exception
	{
		return null;
	}

	@Override
	public void setTargetObject( Arrow target )
	{
		this.listenedArrow = target;
	}
}
