package Shared;

import java.util.Iterator;

public interface Set0<V>
{
	void add( V target );

	void remove( V target );

	boolean contains( V target );

	int size();

	Iterator<V> iterator();
}
