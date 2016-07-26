package Arrows;

import Arrows.Impl.*;
import Shared.Set0;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;

public interface Arrows extends Set0<ArrowView>
{

	public static enum StandardArrowName
	{
		DefaultName, InverseDefaultName,
		Name2Arrow, Arrow2Name,
		Id2Arrow, Arrow2Id,
		Class2Object, Object2Class,
		Name2Object, Object2Name,
		Object2Config, Config2Object,
		InboundArrow2Object, Object2InboundArrow,
		OutboundArrow2Object, Object2OutboundArrow
	}

	void name( ArrowView arrow, Object arrowName, Object arrowInverseName );

	ArrowView arrow( Object arrowName ) throws Exception;

	//ArrowBuilder create( Enum arrowName, Enum inverseArrowName );

	GenericArrowBuilder createGeneric();
	//ComputedArrowBuilder createComputed( );


	static ArrowView filter( Arrow arrow, BiPredicate filter )
	{
		return new FilterArrow( arrow, filter );
	}

	static ArrowView join( Arrow... arrows )
	{
		return new JoinArrow( arrows );
	}

	static ArrowView union( Arrow... arrows )
	{
		return new UnionArrow( arrows );
	}

	static ArrowView intersect( Arrow... arrows )
	{
		return new IntersectArrow( arrows );
	}

	static <K, V> ComputedArrow<K, V> computedArrow( Function<K, Set<V>> function, Class domain, Class codomain )
	{
		return new ComputedArrowImpl<>( function, domain, codomain );
	}
}
