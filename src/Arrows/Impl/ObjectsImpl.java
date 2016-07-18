package Arrows.Impl;

import Arrows.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Arrows.Arrows.StandardArrowName.*;

public class ObjectsImpl implements Objects
{
	final ObjectConfig defaultObjectConfig = new ObjectConfigBuilderImpl().end();
	Arrow<Enum, Object> name2Object = null;
	Arrow<Object, ObjectConfig> object2Config = null;
	Arrow<Class, Object> class2Object = null;

	public ObjectsImpl( Arrows arrows )
	{
		try
		{
			this.name2Object = arrows.arrow( Name2Object );
			this.object2Config = arrows.arrow( Object2Config );
			this.class2Object = arrows.arrow( Class2Object );
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
	public void add( Object object, ObjectConfig config ) //    objectsArrow.connect( name, value )
	{
		addInternal( object, config );
	}

	@Override
	public void remove( Object obj ) // remove object and all the standard relations involving it. if arrows are tracked, also delete the relations it is involved
	{

	}

	@Override
	public Iterator objects()
	{
		return name2Object.targets().iterator();
	}

	@Override
	public Iterator objects( Class clazz )
	{
		return class2Object.targets( clazz ).iterator();
	}

	@Override
	public boolean contains( Object object )
	{
		return name2Object.targets().contains( object );
	}

	//@Override
	public int size()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void addNewObject( Object object, ObjectConfig objectConfig ) throws Exception
	{
		if( contains( object ) )
			throw new Exception( "Object already registered." );
		name2Object.connect( objectConfig.name(), object );
		object2Config.connect( object, objectConfig );
	}

}
