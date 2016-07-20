package Arrows;

import java.util.Collection;

public interface ComputedArrow<K, V> extends Arrow<K, V>
{
	public void addSource( K source );

	public void addSources( Collection<? extends K> source );

	public void remove( K source );
}
