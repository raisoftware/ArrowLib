package Shared;

public interface Set0<V> extends Iterable<V>
{
	void add( V target );

	void remove( V target );

	boolean contains( V target );

	int size();
}
