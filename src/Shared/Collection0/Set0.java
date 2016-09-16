package Shared.Collection0;

public interface Set0<V> extends Iterable<V>
{
	Set0<Class> domains();

	void add( V target );

	void remove( V target );

	boolean contains( V target );

	int size();

	default void addAll( Iterable<V> iterable )
	{
		for( V value : iterable )
		{
			add( value );
		}
	}

	default void removeAll( Iterable<V> iterable )
	{
		for( V value : iterable )
		{
			remove( value );
		}
	}

}
