package Arrows.Utils;

import Arrows.ArrowView;
import Arrows.Diagram;
import Shared.BasicSet0;
import Shared.Set0;
import java.util.*;

import static Arrows.Arrows.StandardArrowName.*;


public class ArrowUtils
{
	public static Set0 generateRelations( ArrowView arrow )
	{
		Set0<Map.Entry> relations = new BasicSet0( new HashSet<>() );
		for( Object source : arrow.sources() )
		{
			for( Object target : arrow.targets( source ) )
			{
				Map.Entry<Object, Object> entry = new AbstractMap.SimpleEntry<>( source, target );
				relations.add( entry );
			}
		}
		return relations;
	}

	public static Object target( ArrowView arrow, Object source ) throws Exception
	{
		Set0 targets = arrow.targets( source );
		if( targets.size() != 1 )
			throw ExceptionUtils.targetsNumberException( targets.size() );
		return targets.iterator().next();
	}

	public static void checkForMultipleSourcesTargets( ArrowView arrow, boolean allowsMultipleTargets, boolean allowsMultipleSources, Object source, Iterable<? extends Object> targets )
	{
		if( !allowsMultipleTargets && arrow.sources().contains( source ) )
		{
			throw ExceptionUtils.multipleSourcesTargetsException( source, targets );
		}
		if( !allowsMultipleSources )
		{
			for( Object target : targets )
			{
				if( arrow.targets().contains( target ) )
				{
					throw ExceptionUtils.multipleSourcesTargetsException( source, targets );
				}
			}
		}
	}

	public static String shortToString( Diagram diagram, ArrowView arrow, String arrowType )
	{
		String idName = "";
		try
		{
			ArrowView arrow2id = diagram.arrows().arrow( Arrow2Id );
			ArrowView arrow2name = diagram.arrows().arrow( Arrow2Name );

			Object id = arrow2id.target( arrow );

			Object name;
			try
			{
				name = arrow2name.target( arrow );
			}
			catch( Exception ex )
			{
				name = "Unnamed";
			}
			idName += "(#" + id + "-" + name + ")";
		}
		catch( Exception ex )
		{
			idName = "Unknown id/name";
		}

		return idName + " = " + arrowType + "<" + arrow.domain() + "," + arrow.codomain() + ">";
	}

	public static String toString( Diagram diagram, ArrowView arrow, String arrowType )
	{
		return shortToString( diagram, arrow, arrowType ) + "  Relations:" + arrow.relations();
	}


}
