package Arrows.Impl;

import Arrows.*;
import Arrows.Impl.Rule.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static Arrows.Arrows.StandardArrowName.*;

public class ArrowsImpl implements Arrows
{
	Arrow<Object, ArrowView> name2arrow;
	Arrow<Integer, ArrowView> id2arrow;

	Diagram diagram;
	AtomicInteger sequence = new AtomicInteger();

	Class2ObjectRule class2ObjectRule;


	public ArrowsImpl( Diagram diagram )
	{
		this.diagram = diagram;

		name2arrow = new GenericArrow( Object.class, ArrowView.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ false, /*listenable=*/ false );
		id2arrow = new GenericArrow( Integer.class, ArrowView.class, /*allowsMultipleSources=*/ false, /*allowsMultipleTargets=*/ false, /*listenable=*/ false );

		addInternal( name2arrow );
		addInternal( id2arrow );
		name( name2arrow, StandardArrowName.Name2Arrow, StandardArrowName.Arrow2Name );
		name( id2arrow, StandardArrowName.Id2Arrow, StandardArrowName.Arrow2Id );


		Arrow class2object = new GenericArrow( Class.class, Object.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ true, /*listenable=*/ false );
		addInternal( class2object );
		name( class2object, Class2Object, Object2Class );


		Arrow name2object = new GenericArrow( Object.class, Object.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ true, /*listenable=*/ false );
		addInternal( name2object );
		name( name2object, Name2Object, Object2Name );


		Arrow object2config = new GenericArrow( Object.class, ObjectConfig.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ true, /*listenable=*/ false );
		addInternal( object2config );
		name( object2config, Object2Config, Config2Object );


		Arrow inboundArrow2object = new GenericArrow( Arrow.class, Object.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ true, /*listenable=*/ false );
		addInternal( inboundArrow2object );
		name( inboundArrow2object, InboundArrow2Object, Object2InboundArrow );


		Arrow outboundArrow2object = new GenericArrow( Arrow.class, Object.class, /*allowsMultipleSources=*/ true, /*allowsMultipleTargets=*/ true, /*listenable=*/ false );
		addInternal( outboundArrow2object );
		name( outboundArrow2object, OutboundArrow2Object, Object2OutboundArrow );

		class2ObjectRule = new Class2ObjectRule( this );
	}

	@Override
	public final GenericArrowBuilder createGeneric()
	{
		return new GenericArrowBuilder( this );
	}

	public final void addInternal( ArrowView arrow )
	{
		id2arrow.editor().connect( sequence.incrementAndGet(), arrow );
		id2arrow.editor().connect( sequence.incrementAndGet(), arrow.inverse() );
	}

	@Override
	public void name( ArrowView arrow, Object arrowName, Object arrowInverseName )
	{
		if( !contains( arrow ) )
			throw new RuntimeException( "Arrow not registered." );

		if( name2arrow.targets().contains( arrow ) )
			throw new RuntimeException( "Arrow already has a name" );

		name2arrow.inverse().editor().connect( arrow, arrowName );
		name2arrow.inverse().editor().connect( arrow.inverse(), arrowInverseName );
	}

	@Override
	public final void add( ArrowView arrow )
	{
		//TOFIX if( listenable )
		{
			Arrow listenedArrow = (Arrow) arrow;

			ObjectRegistrarRule objectRegistrarRule = new ObjectRegistrarRule( diagram.objects() );
			Arrow2ObjectRule arrow2ObjectRule = new Arrow2ObjectRule( listenedArrow, this );

			listenedArrow.listeners().add( class2ObjectRule );
			listenedArrow.listeners().add( objectRegistrarRule );
			listenedArrow.listeners().add( arrow2ObjectRule );
		}
		addInternal( arrow );
	}

	@Override
	public ArrowView arrow( Object arrowName ) throws Exception
	{
		return name2arrow.target( arrowName );
	}

	@Override
	public void remove( ArrowView target )
	{
		id2arrow.inverse().editor().remove( target, null );
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



}
