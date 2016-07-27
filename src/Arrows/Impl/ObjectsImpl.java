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
	Arrow<Object, Object> id2object = null;
	Arrow<Object, Object> name2object = null;
	Arrow<Object, ObjectConfig> object2config = null;
	Arrow<Class, Object> class2object = null;
	Arrow<Arrow, Object> inboundArrow2object = null;
	Arrow<Arrow, Object> outboundArrow2object = null;

	public ObjectsImpl( Arrows arrows )
	{
		try
		{
			this.id2object = (Arrow) arrows.arrow( Id2Object );
			this.name2object = (Arrow) arrows.arrow( Name2Object );
			this.object2config = (Arrow) arrows.arrow( Object2Config );
			this.class2object = (Arrow) arrows.arrow( Class2Object );
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

		ObjectConfig config = new ObjectConfigBuilderImpl().end();
		id2object.editor().connect( sequence.getAndIncrement(), object );
		object2config.editor().connect( object, config );
	}

	public void name( Object object, Object name )
	{
		if( !contains( object ) )
			throw new RuntimeException( "Object not registered." );

		if( name2object.targets().contains( object ) )
			throw new RuntimeException( "Object already has a name" );

		name2object.inverse().editor().connect( object, name );
	}

	@Override
	public boolean contains( Object object )
	{
		return id2object.targets().contains( object );
	}

	@Override
	public int size()
	{
		return id2object.targets().size();
	}


	@Override
	public void remove( Object obj, boolean cascade )
	{
		if( Set0Utils.isEmpty( object2config.targets( obj ) ) )
			return;

		ObjectConfig objConfig;

		try
		{
			objConfig = object2config.target( obj );
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
		return class2object.targets( clazz ).iterator();
	}

	@Override
	public Iterator iterator()
	{
		return id2object.targets().iterator();
	}

	@Override
	public void config( Object object, ObjectConfig config )
	{
		if( !contains( object ) )
			throw new RuntimeException( "Object is not registered." );

		object2config.editor().remove( object, null );
		object2config.editor().connect( object, config );
	}

	@Override
	public ObjectConfig config( Object object )
	{
		if( !contains( object ) )
			throw new RuntimeException( "Object is not registered." );

		try
		{
			return object2config.target( object );
		}
		catch( Exception ex )
		{
			throw new RuntimeException( ex.getMessage() );
		}
	}
}
