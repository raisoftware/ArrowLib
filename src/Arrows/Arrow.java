package Arrows;

import java.util.*;

public interface Arrow<K, V>
{

	ArrowConfig config();

	Set<K> sources();

	Set<V> targets();

	V target( K source ) throws Exception;

	Set<V> targets( K source );

	Set<Map.Entry<K, V>> relations();

	Arrow<V, K> inverse();

}
