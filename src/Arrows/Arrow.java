package Arrows;

import java.util.*;

public interface Arrow<K, V>
{

	ArrowConfig config();

	void connect( K source, Collection<? extends V> targets );

	void connect( K source, V target );

	Set<Map.Entry<K, V>> relations();

	void remove( K source, V target );

	Set<K> sources();

	Set<V> targets();

	Set<V> targets( K source );

	V target( K source ) throws Exception;

	Arrow inverse();

}
