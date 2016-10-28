package Arrows.Impl;

import Arrows.*;
import Arrows.ArrowEditor;
import Arrows.Impl.Rule.*;
import Shared.Collection0.*;
import java.util.*;

import static Arrows.Arrows.Names.*;

public class ArrowsImpl implements Arrows
{
	private final Arrow<Object, ArrowView> name2arrow;
	private final Arrow<Integer, ArrowView> id2arrow;

	private final Arrow<Arrow, Object> inboundArrow_object;
	private final Arrow<Arrow, Object> outboundArrow_object;
	private final Arrow<Class, Object> class_object;

	private final Diagram diagram;

	private final Class2ObjectRule class2ObjectRule;
	private final Set0<ArrowEditor> customRules;
	private final Set0<Class> domains;

	public ArrowsImpl( Diagram diagram )
	{
		this.diagram = diagram;

		name2arrow = new GenericArrow( diagram, Names.Name_Arrow, Names.Arrow_Name, Object.class, ArrowView.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ false, /*listenable=*/ false );
		id2arrow = new GenericArrow( diagram, Names.Id_Arrow, Names.Arrow_Id, Integer.class, ArrowView.class, /*allowsMultipleSources=*/ false, /*allowsMultipleTargets=*/ false, /*listenable=*/ false );

		addInternal( name2arrow );
		addInternal( id2arrow );
		name( name2arrow, Names.Name_Arrow, Names.Arrow_Name );
		name( id2arrow, Names.Id_Arrow, Names.Arrow_Id );


		class_object = new GenericArrow( diagram, Class_Object, Object_Class, Class.class, Object.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ true, /*listenable=*/ false );
		addInternal( class_object );
		name( class_object, Class_Object, Object_Class );


		Arrow<Object, Object> name2object = new GenericArrow( diagram, Name_Object, Object_Name, Object.class, Object.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ true, /*listenable=*/ false );
		addInternal( name2object );
		name( name2object, Name_Object, Object_Name );

		Arrow<Integer, Object> id2object = new GenericArrow( diagram, Id_Object, Object_Id, Integer.class, Object.class, /*allowsMultipleSources=*/ false, /*allowsMultipleTargets=*/ false, /*listenable=*/ false );
		addInternal( id2object );
		name( id2object, Id_Object, Object_Id );

		ArrowView object2object = diagram.join( id2object.inverse(), id2object );
		addInternal( object2object );
		name( object2object, Object_Object, Object_Object_Inverse );

		Arrow object2config = new GenericArrow( diagram, Object_Config, Config_Object, Object.class, ObjectConfig.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ true, /*listenable=*/ false );
		addInternal( object2config );
		name( object2config, Object_Config, Config_Object );


		inboundArrow_object = new GenericArrow( diagram, InboundArrow_Object, Object_InboundArrow, Arrow.class, Object.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ true, /*listenable=*/ false );
		addInternal( inboundArrow_object );
		name( inboundArrow_object, InboundArrow_Object, Object_InboundArrow );


		outboundArrow_object = new GenericArrow( diagram, OutboundArrow_Object, Object_OutboundArrow, Arrow.class, Object.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ true, /*listenable=*/ false );
		addInternal( outboundArrow_object );
		name( outboundArrow_object, OutboundArrow_Object, Object_OutboundArrow );

		Arrow<Object, Object> owner2property = new GenericArrow( diagram, Owner_Property, Property_Owner, Object.class, Object.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ false, /*listenable=*/ false );
		addInternal( owner2property );
		name( owner2property, Owner_Property, Property_Owner );

		class2ObjectRule = new Class2ObjectRule( this );
		customRules = Sets.create( ArrowEditor.class );

		domains = Sets.create( Class.class );
		domains.add( ArrowView.class );
	}

	@Override
	public Set0<ArrowEditor> customRules()
	{
		return customRules;
	}

	private final void addInternal( ArrowView arrow )
	{
		int arrowId = diagram.incrementAndGetSequence();
		int arrowInverseId = diagram.incrementAndGetSequence();
		arrow.id( arrowId );
		id2arrow.aim( arrowId, arrow );
		id2arrow.aim( arrowInverseId, arrow.inverse() );
	}

	@Override
	public final void name( ArrowView arrow, Enum arrowName, Enum arrowInverseName )
	{
		name( arrow, arrowName.toString(), arrowInverseName.toString() );
	}

	@Override
	public final void name( ArrowView arrow, String arrowName, String arrowInverseName )
	{
		if( !contains( arrow ) )
			throw new RuntimeException( "Arrow not registered." );

		if( name2arrow.targets().contains( arrow ) )
			throw new RuntimeException( "Arrow already has a name" );

		name2arrow.inverse().aim( arrow, arrowName );
		name2arrow.inverse().aim( arrow.inverse(), arrowInverseName );
	}

	@Override
	public final void add( ArrowView arrow )
	{

		if( arrow instanceof Arrow )
		{
			Arrow listenedArrow = (Arrow) arrow;

			ObjectRegistrarRule objectRegistrarRule = new ObjectRegistrarRule( diagram.objects() );
			Arrow2ObjectRule arrow2ObjectRule = new Arrow2ObjectRule( listenedArrow, this );

			listenedArrow.subscribers().add( objectRegistrarRule );
			listenedArrow.subscribers().add( arrow2ObjectRule );
			listenedArrow.subscribers().add( class2ObjectRule );


			for( ArrowEditor rule : customRules )
			{
				listenedArrow.subscribers().add( rule );
			}
		}

		addInternal( arrow );
	}

	@Override
	public ArrowView arrowView( String arrowName )
	{
		return name2arrow.target( arrowName );
	}

	@Override
	public Arrow arrow( String arrowName )
	{
		return (Arrow) name2arrow.target( arrowName );
	}

	@Override
	public ArrowView arrowView( Enum arrowName )
	{
		return arrowView( arrowName.toString() );
	}

	@Override
	public Arrow arrow( Enum arrowName )
	{
		return arrow( arrowName.toString() );
	}

	@Override
	public void remove( ArrowView target )
	{
		id2arrow.removeSources( target );
		name2arrow.removeSources( target );
	}

	@Override
	public boolean contains( ArrowView target )
	{
		return id2arrow.targets().contains( target );
	}

	@Override
	public int size()
	{
		return id2arrow.targets().size();
	}

	@Override
	public Iterator<ArrowView> iterator()
	{
		return id2arrow.targets().iterator();
	}

	@Override
	public Set0<Class> domains()
	{
		return domains;
	}

	@Override
	public Arrow<Arrow, Object> inboundArrow_object()
	{
		return inboundArrow_object;
	}

	@Override
	public Arrow<Arrow, Object> outboundArrow_object()
	{
		return outboundArrow_object;
	}

	@Override
	public Arrow<Class, Object> class_object()
	{
		return class_object;
	}
}
