package Arrows;

import java.util.Map;
import java.util.Set;

public interface ArrowView<K, V>
{
	default Class codomain()
	{
		return Object.class;
	}

	default Class domain()
	{
		return Object.class;
	}

	Set<K> sources();

	Set<V> targets();

	V target( K source ) throws Exception;

	Set<V> targets( K source );

	Set<Map.Entry<K, V>> relations();

	ArrowView<V, K> inverse();
}
