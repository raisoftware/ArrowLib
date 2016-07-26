package Arrows;

import java.util.Collection;

public interface ComputedArrow<K, V> extends ArrowView<K, V>
{
	public void addSource( K source );

	public void addSources( Collection<? extends K> sources );

	public void remove( K source );
}
