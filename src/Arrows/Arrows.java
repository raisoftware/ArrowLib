package Arrows;

import Arrows.ArrowEditor;
import Shared.Collection0.Set0;

public interface Arrows extends Set0<ArrowView>
{

	public static enum Names
	{
		Name_Arrow, Arrow_Name,
		Id_Arrow, Arrow_Id,
		Class_Object, Object_Class,
		Name_Object, Object_Name,
		Id_Object, Object_Id,
		Object_Object, Object_Object_Inverse,
		Object_Config, Config_Object,
		InboundArrow_Object, Object_InboundArrow,
		OutboundArrow_Object, Object_OutboundArrow,
		Owner_Property, Property_Owner
	}

	void name( ArrowView arrow, Object arrowName, Object arrowInverseName );

	ArrowView arrowView( Object arrowName ) throws Exception;
	Arrow arrow( Object arrowName ) throws Exception;

	Set0<ArrowEditor> customRules();
}
