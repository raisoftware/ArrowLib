package Arrows;

public interface ArrowEditor<K, V>
{
	void connect( K source, V target );
	void connect( K source, Iterable<? extends V> targets );
	void connect( Iterable<? extends K> sources, V target );
	void remove( K source, V target );

}
