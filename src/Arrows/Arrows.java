package Arrows;

import Arrows.Arrow.Editor;
import Arrows.Impl.*;
import Shared.Set0;
import java.util.function.BiPredicate;

public interface Arrows extends Set0<ArrowView>
{

	public static enum Names
	{
		Name_Arrow, Arrow_Name,
		Id_Arrow, Arrow_Id,
		Class_Object, Object_Class,
		Name_Object, Object_Name,
		Id_Object, Object_Id,
		Object_Config, Config_Object,
		InboundArrow_Object, Object_InboundArrow,
		OutboundArrow_Object, Object_OutboundArrow
	}

	void name( ArrowView arrow, Object arrowName, Object arrowInverseName );

	ArrowView arrow( Object arrowName ) throws Exception;

	GenericArrowBuilder createGeneric();

	ComputedArrowBuilder createComputed();

	Set0<Editor> customRules();

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
}
