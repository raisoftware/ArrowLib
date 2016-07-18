package Arrows;

import Arrows.Impl.ArrowBuilderImpl;
import Arrows.Impl.ManyToManyArrow;
import Shared.MethodBus.Sequence.MethodSequence;

public class Arrows
{
	public static enum StandardArrowName
	{
		DefaultName, InverseDefaultName,
		Name2Arrow, Arrow2Name,
		Class2Object, Object2Class,
		Name2Object, Object2Name,
		Object2Config, Config2Object,
		InboundArrow2Object, Object2InboundArrow,
		OutboundArrow2Object, Object2OutboundArrow
	}

	Arrow<Enum, Arrow> name2arrow;
	final private ArrowConfig defaultArrowConfig;
	final private MethodSequence methodSequence;

	public Arrows( MethodSequence<Arrow, ArrowListener> methodSequence )
	{
		this.methodSequence = methodSequence;
		ArrowBuilder arrowBuilder = create( StandardArrowName.Name2Arrow, StandardArrowName.Arrow2Name ).listenable( false );

		ArrowConfig arrowConfig = arrowBuilder.allowMultipleTargets( false ).domain( Enum.class ).codomain( Arrow.class ).arrowConfig();
		name2arrow = new ManyToManyArrow( arrowConfig );
		add( StandardArrowName.Name2Arrow, StandardArrowName.Arrow2Name, name2arrow );

		defaultArrowConfig = create( StandardArrowName.DefaultName, StandardArrowName.InverseDefaultName ).arrowConfig();
	}

	public final ArrowBuilder create( Enum arrowName, Enum inverseArrowName )
	{
		return new ArrowBuilderImpl( this, arrowName, inverseArrowName );
	}

	public void add( Enum arrowName, Enum arrowInverseName, Arrow arrow )
	{
		name2arrow.connect( arrowName, arrow );
		name2arrow.connect( arrowInverseName, arrow.inverse() );
	}

	public Arrow arrow( Enum arrowName ) throws Exception
	{
		return name2arrow.target( arrowName );
	}

	public ArrowConfig defaultArrowConfig()
	{
		return defaultArrowConfig;
	}

	public MethodSequence<Arrow, ArrowListener> methodSequence()
	{
		return methodSequence;
	}

}
