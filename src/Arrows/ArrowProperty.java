package Arrows;

import Shared.Collection0.Set0;


public class ArrowProperty<K, V> implements Property<V>
{
	Arrow<K, V> arrow;
	K source;

	public ArrowProperty( Arrow<K, V> arrow, K source, V initialValue )
	{
		this.arrow = arrow;
		this.source = source;
		this.arrow.aim( source, initialValue );
	}

	Arrow<K, V> arrow()
	{
		return arrow;
	}

	K owner()
	{
		return source;
	}

	Set0<K> similar() // returns sources of the arrow with the same value as property
	{
		return arrow.sources( get() );
	}

	@Override
	public V get()
	{
		return arrow.target( source );
	}

	@Override
	public void set( V newValue )
	{
		arrow.removeTargets( source );
		arrow.aim( source, newValue );
	}

}
