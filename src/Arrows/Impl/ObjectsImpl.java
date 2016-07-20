package Arrows.Impl;

import Arrows.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Arrows.Arrows.StandardArrowName.*;

public class ObjectsImpl implements Objects
{
	final ObjectConfig defaultObjectConfig = new ObjectConfigBuilderImpl().end();
	EditableArrow<Enum, Object> name2Object = null;
	EditableArrow<Object, ObjectConfig> object2Config = null;
	EditableArrow<Class, Object> class2Object = null;

	public ObjectsImpl( Arrows arrows )
	{
		try
		{
			this.name2Object = arrows.editableArrow( Name2Object );
			this.object2Config = arrows.editableArrow( Object2Config );
			this.class2Object = arrows.editableArrow( Class2Object );
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
	public void remove( Object obj, boolean cascade ) // remove object and all the standard relations involving it. if arrows are tracked, also delete the relations it is involved
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void remove( Object target )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
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
