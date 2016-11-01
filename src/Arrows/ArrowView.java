package Arrows;

import Shared.Collection0.Set0;
import Shared.Collection0.Sets;
import java.util.Map;

public interface ArrowView<K, V>
{
	int ID_NOT_SET = -1;

	default Class codomain()
	{
		return Object.class;
	}

	default Class domain()
	{
		return Object.class;
	}

	Set0<K> sources();
	default K source( V target )
	{
		return inverse().target( target );
	}
	default Set0<K> sources( V target )
	{
		return inverse().targets( target );
	}
	default Set0<K> sources( Iterable<? extends V> targets )
	{
		return inverse().targets( targets );
	}



	Set0<V> targets();
	V target( K source );
	Set0<V> targets( K source );
	default Set0<V> targets( Iterable<? extends K> sources )
	{
		Set0<V> targets = Sets.create( codomain() );
		for( K source : sources )
		{
			targets.addAll( targets( source ) );
		}
		return targets;
	}



	Set0<Map.Entry<K, V>> relations();

	ArrowView<V, K> inverse();

	int id();
	void id( int id );
}
