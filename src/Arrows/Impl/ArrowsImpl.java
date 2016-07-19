package Arrows.Impl;

import Arrows.*;
import Shared.MethodBus.Sequence.MethodSequence;

public class ArrowsImpl implements Arrows
{
	Arrow<Enum, Arrow> name2arrow;
	final private ArrowConfig defaultArrowConfig;
	final private MethodSequence methodSequence;

	public ArrowsImpl( MethodSequence<Arrow, ArrowListener> methodSequence )
	{
		this.methodSequence = methodSequence;
		ArrowBuilder arrowBuilder = create( StandardArrowName.Name2Arrow, StandardArrowName.Arrow2Name ).listenable( false );

		ArrowConfig arrowConfig = arrowBuilder.allowMultipleTargets( false ).domain( Enum.class ).codomain( Arrow.class ).arrowConfig();
		name2arrow = new ManyToManyArrow( arrowConfig );
		add( StandardArrowName.Name2Arrow, StandardArrowName.Arrow2Name, name2arrow );

		defaultArrowConfig = create( StandardArrowName.DefaultName, StandardArrowName.InverseDefaultName ).arrowConfig();
	}

	@Override
	public final ArrowBuilder create( Enum arrowName, Enum inverseArrowName )
	{
		return new ArrowBuilderImpl( this, arrowName, inverseArrowName );
	}

	@Override
	public void add( Enum arrowName, Enum arrowInverseName, Arrow arrow )
	{
		name2arrow.connect( arrowName, arrow );
		name2arrow.connect( arrowInverseName, arrow.inverse() );
	}

	@Override
	public Arrow arrow( Enum arrowName ) throws Exception
	{
		return name2arrow.target( arrowName );
	}

	@Override
	public ArrowConfig defaultArrowConfig()
	{
		return defaultArrowConfig;
	}

	@Override
	public MethodSequence<Arrow, ArrowListener> methodSequence()
	{
		return methodSequence;
	}

}
