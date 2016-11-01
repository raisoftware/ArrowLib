package Arrows.Impl;

import Arrows.*;
import Shared.Collection0.Set0;
import Shared.Collection0.Sets;
import com.google.common.collect.ImmutableSet;
import java.util.Iterator;

import static Arrows.Arrows.Names.*;

public class ObjectsImpl implements Objects
{
	private Diagram diagram;
	private Arrow<Integer, Object> id2object = null;
	private Arrow<Object, Object> name2object = null;
	private Arrow<Object, ObjectConfig> object2config = null;
	private Arrow<Class, Object> class2object = null;
	private Arrow<Arrow, Object> inboundArrow2object = null;
	private Arrow<Arrow, Object> outboundArrow2object = null;
	private ArrowView<Object, Object> identity = null;
	private Set0<Class> domains;

	public ObjectsImpl( Diagram diagram )
	{
		this.diagram = diagram;
		Arrows arrows = diagram.arrows();
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

		domains = Sets.create( Class.class );
		domains.add( Object.class );
	}

	@Override
	public void add( Object object )
	{
		if( contains( object ) )
			throw new RuntimeException( "Object already registered. [object = " + object + "]" );

		ObjectConfig config = new ObjectConfigBuilderImpl().end();
		id2object.aim( diagram.getAndIncrementSequence(), object );
		object2config.aim( object, config );
	}

	@Override
	public void name( Object object, Object name )
	{
		if( !contains( object ) )
			throw new RuntimeException( "Object not registered.  [object = " + object + "  name = " + name + "]" );

		if( name2object.targets().contains( object ) )
			throw new RuntimeException( "Object already has a name  [object = " + object + "  new-name = " + name + "]" );

		name2object.inverse().aim( object, name );
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
		if( Sets.isEmpty( object2config.targets( obj ) ) )
			return;

		ObjectConfig objConfig = object2config.target( obj );

		if( objConfig.tracksInboundArrows() )
		{
			Set0<Arrow> arrows = inboundArrow2object.sources( obj );
			ImmutableSet<Arrow> arrowsCopy = ImmutableSet.copyOf( arrows );
			for( Arrow arrow : arrowsCopy )
			{
				arrow.removeSources( obj );
			}
		}


		if( objConfig.tracksOutboundArrows() )
		{
			Set0<Arrow> arrows = outboundArrow2object.sources( obj );
			for( Arrow arrow : arrows )
			{
				Set0 targets = null;
				if( cascade )
				{
					targets = arrow.targets( obj );
				}
				arrow.removeTargets( obj );
				if( cascade )
				{
					ImmutableSet targetsCopy = ImmutableSet.copyOf( targets );
					for( Object target : targetsCopy )
						remove( target, cascade );
				}
			}
		}

		id2object.removeSources( obj );
		name2object.removeSources( obj );
		object2config.removeTargets( obj );
		class2object.removeSources( obj );
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

		object2config.removeTargets( object );
		object2config.aim( object, config );
	}

	@Override
	public ObjectConfig config( Object object )
	{
		if( !contains( object ) )
			throw new RuntimeException( "Object is not registered." );

		return object2config.target( object );

	}

	@Override
	public ArrowView identity()
	{
		return identity;
	}

	@Override
	public Set0<Class> domains()
	{
		return domains;
	}
}
