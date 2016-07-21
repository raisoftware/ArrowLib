package Arrows;

import java.util.Collection;

public interface EditableArrow<K, V> extends Arrow<K, V>
{
	void connect( K source, V target );

	void connect( K source, Collection<? extends V> targets );

	void connect( Collection<? extends K> sources, V target );

	void remove( K source, V target );

	@Override
	EditableArrow<V, K> inverse();
}
