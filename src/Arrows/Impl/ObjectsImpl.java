package Arrows.Impl;

import Arrows.*;
import Shared.Set0;
import Shared.Set0Utils;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Arrows.Arrows.StandardArrowName.*;

public class ObjectsImpl implements Objects
{
	AtomicInteger sequence = new AtomicInteger();
	Arrow<Object, Object> name2Object = null;
	Arrow<Object, ObjectConfig> object2Config = null;
	Arrow<Class, Object> class2Object = null;
	Arrow<Arrow, Object> inboundArrow2object = null;
	Arrow<Arrow, Object> outboundArrow2object = null;

	public ObjectsImpl( Arrows arrows )
	{
		try
		{
			this.name2Object = (Arrow) arrows.arrow( Name2Object );
			this.object2Config = (Arrow) arrows.arrow( Object2Config );
			this.class2Object = (Arrow) arrows.arrow( Class2Object );
			this.inboundArrow2object = (Arrow) arrows.arrow( InboundArrow2Object );
			this.outboundArrow2object = (Arrow) arrows.arrow( OutboundArrow2Object );
		}
		catch( Exception ex )
		{
			Logger.getLogger( ObjectsImpl.class.getName() ).log( Level.SEVERE, null, ex );
		}
	}

	@Override
	public void add( Object object )
	{
		if( contains( object ) )
			throw new RuntimeException( "Object already registered." );

		ObjectConfig config = new ObjectConfigBuilderImpl( sequence.getAndIncrement() ).end();
		name2Object.editor().connect( config.name(), object );
		object2Config.editor().connect( object, config );
	}

	@Override
	public boolean contains( Object object )
	{
		return name2Object.targets().contains( object );
	}

	@Override
	public int size()
	{
		return name2Object.targets().size();
	}


	@Override
	public void remove( Object obj, boolean cascade )
	{
		if( Set0Utils.isEmpty( object2Config.targets( obj ) ) )
			return;

		ObjectConfig objConfig;

		try
		{
			objConfig = object2Config.target( obj );
		}
		catch( Exception ex )
		{
			throw new RuntimeException( ex.getMessage() );
		}

		if( objConfig.tracksInboundArrows() )
		{
			Set0<Arrow> arrows = inboundArrow2object.inverse().targets( obj );
			for( Arrow arrow : arrows )
			{
				arrow.inverse().editor().remove( obj, null );
			}
		}


		if( objConfig.tracksOutboundArrows() )
		{
			Set0<Arrow> arrows = outboundArrow2object.inverse().targets( obj );
			for( Arrow arrow : arrows )
			{
				Set0 targets = null;
				if( cascade )
				{
					targets = arrow.targets( obj );
				}
				arrow.editor().remove( obj, null );
				if( cascade )
				{
					for( Object target : targets )
						remove( target, cascade );
				}
			}
		}
	}

	@Override
	public void remove( Object target )
	{
		remove( target, false );
	}

	@Override
	public Iterator iterator( Class clazz )
	{
		return class2Object.targets( clazz ).iterator();
	}

	@Override
	public Iterator iterator()
	{
		return name2Object.targets().iterator();
	}

	@Override
	public void config( Object object, ObjectConfig config )
	{
		if( !contains( object ) )
			throw new RuntimeException( "Object is not registered." );
		name2Object.inverse().editor().remove( object, null );
		object2Config.editor().remove( object, null );
		name2Object.editor().connect( config.name(), object );
		object2Config.editor().connect( object, config );
	}

	@Override
	public ObjectConfig config( Object object )
	{
		if( !contains( object ) )
			throw new RuntimeException( "Object is not registered." );

		try
		{
			return object2Config.target( object );
		}
		catch( Exception ex )
		{
			throw new RuntimeException( ex.getMessage() );
		}
	}
}
