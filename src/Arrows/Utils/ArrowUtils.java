package Arrows.Utils;

import Arrows.ArrowView;
import java.util.*;


public class ArrowUtils
{
	public static Set generateRelations( ArrowView arrow )
	{
		Set<Map.Entry> relations = new HashSet<>();
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
		Set targets = arrow.targets( source );
		if( targets.size() != 1 )
			throw ExceptionUtils.targetsNumberException( targets.size() );
		return targets.iterator().next();
	}

	public static void checkForMultipleSourcesTargets( ArrowView arrow, boolean allowsMultipleTargets, boolean allowsMultipleSources, Object source, Collection<? extends Object> targets )
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

}
