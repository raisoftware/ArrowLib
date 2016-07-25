package Arrows.Impl.Rule;

import Arrows.*;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Arrows.Arrows.StandardArrowName.*;

public class Arrow2ObjectRule implements ArrowListener
{
	EditableArrow listenedArrow = null;

	EditableArrow<EditableArrow, Object> inboundArrow2object;
	EditableArrow<EditableArrow, Object> outboundArrow2object;

	public Arrow2ObjectRule( Arrows arrows )
	{

		try
		{
			this.inboundArrow2object = (EditableArrow) arrows.arrow( InboundArrow2Object );
			this.outboundArrow2object = (EditableArrow) arrows.arrow( OutboundArrow2Object );
		}
		catch( Exception ex )
		{
			Logger.getLogger( Arrow2ObjectRule.class.getName() ).log( Level.SEVERE, null, ex );
		}
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
	public void connect( Collection sources, Object target )
	{
		if( target == null || sources == null || sources.isEmpty() )
			return;

		outboundArrow2object.connect( listenedArrow, sources );
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
		if( target == null || source == null )
			return;

		if( listenedArrow.inverse().targets( target ).isEmpty() )
			inboundArrow2object.remove( listenedArrow, target );

		if( listenedArrow.targets( source ).isEmpty() )
			outboundArrow2object.remove( listenedArrow, source );
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
	public EditableArrow inverse()
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
	public void setTargetObject( EditableArrow target )
	{
		this.listenedArrow = target;
	}
}
