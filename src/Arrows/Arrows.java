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

	void name( ArrowView arrow, String arrowName, String arrowInverseName );
	void name( ArrowView arrow, Enum arrowName, Enum arrowInverseName );

	ArrowView arrowView( String arrowName );
	Arrow arrow( String arrowName );

	ArrowView arrowView( Enum arrowName );
	Arrow arrow( Enum arrowName );

	Set0<ArrowEditor> customRules();

	Arrow<Arrow, Object> inboundArrow_object();
	Arrow<Arrow, Object> outboundArrow_object();
	Arrow<Class, Object> class_object();
}
