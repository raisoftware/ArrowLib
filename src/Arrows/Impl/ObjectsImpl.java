package Arrows.Impl;

import Arrows.*;
import Shared.Set0;
import Shared.Set0Utils;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import static Arrows.Arrows.Names.*;

public class ObjectsImpl implements Objects
{
	private AtomicInteger sequence = new AtomicInteger();
	private Arrow<Object, Object> id2object = null;
	private Arrow<Object, Object> name2object = null;
	private Arrow<Object, ObjectConfig> object2config = null;
	private Arrow<Class, Object> class2object = null;
	private Arrow<Arrow, Object> inboundArrow2object = null;
	private Arrow<Arrow, Object> outboundArrow2object = null;
	private ArrowView<Object, Object> identity = null;

	public ObjectsImpl( Arrows arrows )
	{
		try
		{
			this.id2object = arrows.arrow( Id_Object );
			this.name2object = arrows.arrow( Name_Object );
			this.object2config = arrows.arrow( Object_Config );
			this.class2object = arrows.arrow( Class_Object );
			this.inboundArrow2object = arrows.arrow( InboundArrow_Object );
			this.outboundArrow2object = arrows.arrow( OutboundArrow_Object );
			this.identity = arrows.arrowView( Object_Object );
		}
		catch( Exception ex )
		{
			throw new RuntimeException( "Default arrow not found.", ex );
		}
	}

	@Override
	public void add( Object object )
	{
		if( contains( object ) )
			throw new RuntimeException( "Object already registered. [object = " + object + "]" );

		ObjectConfig config = new ObjectConfigBuilderImpl().end();
		id2object.editor().connect( sequence.getAndIncrement(), object );
		object2config.editor().connect( object, config );
	}

	@Override
	public void name( Object object, Object name )
	{
		if( !contains( object ) )
			throw new RuntimeException( "Object not registered.  [object = " + object + "  name = " + name + "]" );

		if( name2object.targets().contains( object ) )
			throw new RuntimeException( "Object already has a name  [object = " + object + "  new-name = " + name + "]" );

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

	@Override
	public ArrowView identity()
	{
		return identity;
	}

	@Override
	public Class domain()
	{
		return Object.class;
	}
}
