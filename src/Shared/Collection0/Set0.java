package Shared.Collection0;

public interface Set0<V> extends Iterable<V>
{
	Class domain();

	void add( V target );

	void remove( V target );

	boolean contains( V target );

	int size();
}
