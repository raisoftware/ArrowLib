package Arrows;

import java.util.Iterator;

public interface Set2<K, V>
{
	void add( V target );

	void remove( V target );

	boolean contains( V target );

	int size();

	Iterator<V> iterator();
}
