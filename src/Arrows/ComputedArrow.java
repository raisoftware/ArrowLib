package Arrows;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public interface ComputedArrow<K, V> extends ArrowView<K, V>
{
	public void addSource( K source );

	public void addSources( Collection<? extends K> sources );

	public void remove( K source );

	public interface Builder<K, V>
	{
		Builder domain( Class allowedClasses );

		Builder codomain( Class allowedClasses );

		Builder function( Function<K, Set<V>> function );

		ComputedArrow end();
	}
}
