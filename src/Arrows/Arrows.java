package Arrows;

import Arrows.Impl.FilterArrow;
import Arrows.Impl.JoinArrow;
import Shared.MethodBus.Sequence.MethodSequence;
import java.util.function.BiPredicate;

public interface Arrows
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

	void add( Enum arrowName, Enum arrowInverseName, Arrow arrow );

	Arrow arrow( Enum arrowName ) throws Exception;

	ArrowBuilder create( Enum arrowName, Enum inverseArrowName );

	ArrowConfig defaultArrowConfig();

	MethodSequence<Arrow, ArrowListener> methodSequence();
	
	static Arrow filter(Arrow arrow, BiPredicate bipredicate)
	{
		return new FilterArrow(arrow, bipredicate);
	}
	static Arrow join(Arrow... arrows)
	{
		return new JoinArrow(arrows);
	}
	static Arrow union(Arrow... arrows)
	{
		return null;
	}
	static Arrow intersect(Arrow... arrows)
	{
		return null;
	}
}
