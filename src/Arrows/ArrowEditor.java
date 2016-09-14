package Arrows;

public interface ArrowEditor<K, V>
{
	void aim( K source, V target );
	void aim( K source, Iterable<? extends V> targets );
	void aim( Iterable<? extends K> sources, V target );
	void remove( K source, V target );

}
