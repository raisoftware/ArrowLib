package Arrows;

import Arrows.Impl.*;
import Shared.MethodBus.Sequence.MethodSequence;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;

public interface Arrows extends Set2
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

	EditableArrow editableArrow( Enum arrowName ) throws Exception;


	ArrowBuilder create( Enum arrowName, Enum inverseArrowName );

	MethodSequence<EditableArrow, ArrowListener> rules();

	static Arrow filter( Arrow arrow, BiPredicate filter )
	{
		return new FilterArrow( arrow, filter );
	}

	static Arrow join( Arrow... arrows )
	{
		return new JoinArrow( arrows );
	}

	static Arrow union( Arrow... arrows )
	{
		return new UnionArrow( arrows );
	}

	static Arrow intersect( Arrow... arrows )
	{
		return new IntersectArrow( arrows );
	}

	static <K, V> ComputedArrow<K, V> computedArrow( Function<K, Set<V>> function )
	{
		return new ComputedArrowImpl<>( function );
	}
}
