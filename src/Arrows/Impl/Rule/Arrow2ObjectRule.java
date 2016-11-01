package Arrows.Impl.Rule;

import Arrows.*;
import Shared.Collection0.Sets;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Arrows.Arrows.Names.*;

public class Arrow2ObjectRule implements ArrowEditor
{
	private final Arrow listenedArrow;

	private Arrow<Arrow, Object> inboundArrow2object;
	private Arrow<Arrow, Object> outboundArrow2object;
	private ArrowView<Object, ObjectConfig> object2config;


	public Arrow2ObjectRule( Arrow listenedArrow, Arrows arrows )
	{
		this.listenedArrow = listenedArrow;
		try
		{
			this.inboundArrow2object = arrows.arrow( InboundArrow_Object );
			this.outboundArrow2object = arrows.arrow( OutboundArrow_Object );

			//This Rule should be executed after the ObjectRegistrarRule
			this.object2config = arrows.arrowView( Object_Config );

		}
		catch( Exception ex )
		{
			Logger.getLogger( Arrow2ObjectRule.class.getName() ).log( Level.SEVERE, null, ex );
		}
	}


	@Override
	public void aim( Object source, Iterable targets )
	{
		if( source == null || targets == null || !targets.iterator().hasNext() )
			return;

		trackSource( source );

		for( Object target : targets )
		{
			trackTarget( target );
		}
	}

	@Override
	public void aim( Object source, Object target )
	{
		if( source == null || target == null )
			return;

		trackSource( source );
		trackTarget( target );
	}

	@Override
	public void aim( Iterable sources, Object target )
	{
		if( target == null || sources == null || !sources.iterator().hasNext() )
			return;

		for( Object source : sources )
		{
			trackSource( source );
		}

		trackTarget( target );
	}

	private void removeTarget( Object target )
	{
		if( Sets.isEmpty( listenedArrow.sources( target ) ) )
		{
			inboundArrow2object.remove( listenedArrow, target );
			outboundArrow2object.remove( listenedArrow.inverse(), target );
		}

	}

	private void removeSource( Object source )
	{
		if( Sets.isEmpty( listenedArrow.targets( source ) ) )
		{
			outboundArrow2object.remove( listenedArrow, source );
			inboundArrow2object.remove( listenedArrow.inverse(), source );
		}
	}

	@Override
	public void remove( Object source, Object target )
	{
		if( target == null || source == null )
			return;

		// at this point its config is gone so we have to remove it regardless of the tracksInboundArrows/tracksOutboundArrows properties

		removeTarget( target );
		removeSource( source );

	}

	private void trackSource( Object source )
	{
		track( source, true );
	}

	private void trackTarget( Object target )
	{
		track( target, false );
	}

	private void track( Object object, boolean source )
	{
		ObjectConfig config = config( object );
		Arrow arrow;

		if( source )
		{
			arrow = listenedArrow;
		}
		else
		{
			arrow = listenedArrow.inverse();
		}

		if( config.tracksOutboundArrows() )
		{
			outboundArrow2object.aim( arrow, object );
		}

		if( config.tracksInboundArrows() )
		{
			inboundArrow2object.aim( arrow.inverse(), object );
		}
	}

	private ObjectConfig config( Object object )
	{
		return object2config.target( object );

	}

	@Override
	public void removeAll( Object source, Iterable targets )
	{
		for( Object target : targets )
		{
			remove( source, target );
		}
	}

	@Override
	public void removeAll( Iterable sources, Object target )
	{
		for( Object source : sources )
		{
			remove( source, target );
		}
	}

}
