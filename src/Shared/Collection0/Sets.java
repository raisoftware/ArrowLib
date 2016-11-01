package Shared.Collection0;

import com.google.common.collect.Iterables;
import java.util.HashSet;
import java.util.Set;

import static Shared.Collection0.Sets.*;


public class Sets
{
	private Sets()
	{

	}

	public static Set0 filter( Set0 set, Class... classes )
	{
		Set0 filteredSet = create( classes );

		if( classes.length == 0 )
		{
			filteredSet.addAll( set );
			return filteredSet;
		}

		for( Object obj : set )
		{
			for( Class cls : classes )
			{
				if( cls.isInstance( obj ) )
				{
					filteredSet.add( obj );
					break;
				}
			}
		}
		return filteredSet;
	}

	public static Set0 create( Class... domains )
	{
		return new BasicSet0( new HashSet(), domains );
	}

	public static Set0 create( Set backingSet, Class... domains )
	{
		return new BasicSet0( backingSet, domains );
	}

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
		Set0 toRemove = create( Object.class );
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

	public static boolean isNullOrEmpty( Set0 set )
	{
		return set == null || set.size() == 0;
	}

	public static Class[] domain( Set0... sets )
	{
		Set domains = new HashSet<>();
		for( Set0 set : sets )
		{
			for( Object o : set.domains() )
			{
				domains.add( (Class) o );
			}
		}


		if( domains.isEmpty() )
		{
			domains.add( Object.class );
		}

		Class[] domainsArray = Iterables.toArray( domains, Class.class );

		return domainsArray;
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

}
