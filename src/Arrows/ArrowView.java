package Arrows;

import Shared.Collection0.Set0;
import java.util.Map;

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

	Set0<K> sources();

	Set0<V> targets();

	V target( K source ) throws Exception;

	Set0<V> targets( K source );

	Set0<Map.Entry<K, V>> relations();

	ArrowView<V, K> inverse();
}
