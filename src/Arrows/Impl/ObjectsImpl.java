package Arrows.Impl;

import Arrows.*;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Arrows.Arrows.StandardArrowName.*;

public class ObjectsImpl implements Objects
{
	final ObjectConfig defaultObjectConfig = new ObjectConfigBuilderImpl().end();
	EditableArrow<Enum, Object> name2Object = null;
	EditableArrow<Object, ObjectConfig> object2Config = null;
	EditableArrow<Class, Object> class2Object = null;
	EditableArrow<EditableArrow, Object> inboundArrow2object = null;
	EditableArrow<EditableArrow, Object> outboundArrow2object = null;

	public ObjectsImpl( Arrows arrows )
	{
		try
		{
			this.name2Object = (EditableArrow) arrows.arrow( Name2Object );
			this.object2Config = (EditableArrow) arrows.arrow( Object2Config );
			this.class2Object = (EditableArrow) arrows.arrow( Class2Object );
			this.inboundArrow2object = (EditableArrow) arrows.arrow( InboundArrow2Object );
			this.outboundArrow2object = (EditableArrow) arrows.arrow( OutboundArrow2Object );
		}
		catch( Exception ex )
		{
			Logger.getLogger( ObjectsImpl.class.getName() ).log( Level.SEVERE, null, ex );
		}
	}

	private void addInternal( Object object, ObjectConfig objectConfig )
	{
		if( !contains( object ) )
		{
			try
			{
				addNewObject( object, objectConfig );
			}
			catch( Exception ex )
			{
				assert ( false );
			}
		}
		else
		{
			try
			{
				if( name2Object.inverse().target( object ) == StandardObjectName.Unnamed )
				{
					//replace
					name2Object.inverse().remove( object, null );
					object2Config.remove( object, null );
					name2Object.connect( objectConfig.name(), object );
					object2Config.connect( object, objectConfig );
				}
				else if( objectConfig.name() != StandardObjectName.Unnamed )
					throw new Exception( "Object registered twice, should remove first." );
			}
			catch( Exception ex )
			{
				Logger.getLogger( ObjectsImpl.class.getName() ).log( Level.SEVERE, null, ex );
			}
		}
	}

	@Override
	public ObjectConfigBuilder create()
	{
		return new ObjectConfigBuilderImpl();
	}

	@Override
	public void add( Object object )
	{
		addInternal( object, defaultObjectConfig );
	}

	@Override
	public void add( Object object, ObjectConfig config )
	{
		addInternal( object, config );
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
	public void addNewObject( Object object, ObjectConfig objectConfig ) throws Exception
	{
		if( contains( object ) )
			throw new Exception( "Object already registered." );
		name2Object.connect( objectConfig.name(), object );
		object2Config.connect( object, objectConfig );
	}

	@Override
	public void remove( Object obj, boolean cascade )// remove object and all the standard relations involving it. if arrows are tracked, also delete the relations it is involved
	{
		if( object2Config.targets( obj ).isEmpty() )
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
			Set<EditableArrow> arrows = inboundArrow2object.inverse().targets( obj );
			for( EditableArrow arrow : arrows )
			{
				arrow.inverse().remove( obj, null );
			}
		}


		if( objConfig.tracksOutboundArrows() )
		{
			Set<EditableArrow> arrows = outboundArrow2object.inverse().targets( obj );
			for( EditableArrow arrow : arrows )
			{
				Set targets = null;
				if( cascade )
				{
					targets = arrow.targets( obj );
				}
				arrow.remove( obj, null );
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

}
