package Shared;

import java.util.*;


public class BasicSet0<T> implements Set0<T>
{
	Set<T> set;

	public BasicSet0( Set<T> set )
	{
		this.set = set;
	}

	@Override
	public void add( T target )
	{
		set.add( target );
	}

	@Override
	public void remove( T target )
	{
		set.remove( target );
	}

	@Override
	public boolean contains( T target )
	{
		return set.contains( target );
	}

	@Override
	public int size()
	{
		return set.size();
	}

	@Override
	public Iterator<T> iterator()
	{
		return set.iterator();
	}

	@Override
	public int hashCode()
	{
		return set.hashCode();
	}

	@Override
	public boolean equals( Object obj )
	{
		if( !( obj instanceof BasicSet0 ) )
			return false;
		if( obj == this )
			return true;

		return set.equals( ( (BasicSet0) obj ).set );
	}

	@Override
	public String toString()
	{
		return set.toString();
	}

}
