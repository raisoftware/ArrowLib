package Arrows;

import Shared.Collection0.OrderedSet0;


public interface Arrow<K, V> extends ArrowView<K, V>, ArrowEditor<K, V>
{
	OrderedSet0<ArrowEditor> subscribers();

	@Override
	Arrow<V, K> inverse();

	default void removeTargets( K source )
	{
		removeAll( source, targets( source ) );
	}

	default void removeSources( V target )
	{
		removeAll( sources( target ), target );
	}
}
