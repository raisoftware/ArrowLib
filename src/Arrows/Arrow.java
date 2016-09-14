package Arrows;

import Shared.Collection0.OrderedSet0;


public interface Arrow<K, V> extends ArrowView<K, V>
{
	OrderedSet0<Editor> subscribers();

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
