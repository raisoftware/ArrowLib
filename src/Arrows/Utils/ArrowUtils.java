package Arrows.Utils;

import Arrows.Arrow;
import java.util.*;


public class ArrowUtils
{
	public static Set generateRelations( Arrow arrow )
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
}
