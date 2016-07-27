package Shared;


public class Set0Utils
{
	public static <T> void addAll( Set0<T> set, Iterable<T> iterable )
	{
		for( T value : iterable )
		{
			set.add( value );
		}
	}

	public static <T> boolean containsAll( Set0<T> set1, Set0<T> set2 )
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
		for( T value : set1 )
		{
			if( !set2.contains( value ) )
			{
				set1.remove( value );
			}
		}
	}

	public static boolean isEmpty( Set0 set )
	{
		return set.size() == 0;
	}

}
