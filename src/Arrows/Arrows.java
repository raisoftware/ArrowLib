package Arrows;

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
		Object2Arrow, Arrow2Object
	}

	Arrow<Enum, Arrow> name2arrow;
	ArrowConfig defaultArrowConfig;
	MethodSequence methodSequence;

	public Arrows( MethodSequence<Arrow> methodSequence )
	{
		this.methodSequence = methodSequence;
		ArrowBuilder arrowBuilder = create( StandardArrowName.Name2Arrow, StandardArrowName.Arrow2Name );

		ArrowConfig arrowConfig = arrowBuilder.allowMultipleTargets( false ).domain( Enum.class ).codomain( Arrow.class ).arrowConfig();
		name2arrow = new ManyToManyArrow( arrowConfig );

		defaultArrowConfig = create( StandardArrowName.DefaultName, StandardArrowName.InverseDefaultName ).arrowConfig();
	}

	public final ArrowBuilder create( Enum arrowName, Enum inverseArrowName )
	{
		return new ArrowBuilder( this, arrowName, inverseArrowName );
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

	public MethodSequence<Arrow> methodSequence()
	{
		return methodSequence;
	}

}
