package Arrows;

import java.util.Map;
import java.util.Set;

public interface ArrowView<K, V>
{
	ArrowConfig config();

	Set<K> sources();

	Set<V> targets();

	V target( K source ) throws Exception;

	Set<V> targets( K source );

	Set<Map.Entry<K, V>> relations();

	ArrowView<V, K> inverse();
}
