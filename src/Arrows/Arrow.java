package Arrows;

import Shared.Set0;


public interface Arrow<K, V> extends ArrowView<K, V>
{
	Set0<Editor> listeners();

	Editor<K, V> editor();

	@Override
	Arrow<V, K> inverse();

	public interface Editor<K, V>
	{
		void connect( K source, V target );

		void connect( K source, Iterable<? extends V> targets );

		void connect( Iterable<? extends K> sources, V target );

		void remove( K source, V target );
	}
}
