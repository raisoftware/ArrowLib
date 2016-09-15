package Shared.Collection0;

import java.util.HashSet;


public class Sets
{
//	public static Create()
//	{
//		return new BasicSet0()
//	}
	public static <T> boolean containsAll( Set0<T> set1, Iterable<T> set2 )
	{
		for( T value : set2 )
		{
			if( !set1.contains( value ) )
			{
				return false;
			}
		}
		return true;
	}

	public static <T> void retainAll( Set0<T> set1, Set0<T> set2 )
	{
		Set0 toRemove = new BasicSet0( new HashSet(), set1.domain() );
		for( T value : set1 )
		{
			if( !set2.contains( value ) )
			{
				toRemove.add( value );
			}
		}
		set1.removeAll( toRemove );
	}

	public static boolean isEmpty( Set0 set )
	{
		return set.size() == 0;
	}

	public static Class domain( Set0... sets )
	{
		Class domain = sets[0].domain();

		for( Set0 set : sets )
		{
			if( set.domain() != domain )
			{
				return Object.class;
			}
		}

		return domain;
	}

	public static Set0 intersect( Set0... sets )
	{
		BasicSet0 intersect = new BasicSet0( new HashSet(), domain( sets ) );
		intersect.addAll( sets[0] );

		boolean first = true;
		for( Set0 set : sets )
		{
			if( first )
			{
				first = false;
				continue;
			}

			retainAll( intersect, set );
		}
		return intersect;
	}

	public static Set0 union( Set0... sets )
	{
		BasicSet0 union = new BasicSet0( new HashSet(), domain( sets ) );

		for( Set0 set : sets )
		{
			union.addAll( set );
		}

		return union;

	}

	public static Set0 difference( Set0 first, Set0 second )
	{
		BasicSet0 difference = new BasicSet0( new HashSet(), domain( first, second ) );
		difference.addAll( first );
		difference.removeAll( second );
		return difference;
	}

	public static <T> Set0<T> create( Class domain )
	{
		return new BasicSet0( new HashSet(), domain );
	}
}
