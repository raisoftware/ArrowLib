package Arrows.Impl;

import Arrows.Arrow;
import Shared.Collection0.Set0;
import java.util.Iterator;

public class ArrowSet0<K, V> implements Set0<V>
{
	private final Arrow<K, V> arrow;
	private final K source;

	public ArrowSet0( Arrow<K, V> arrow, K source )
	{
		this.arrow = arrow;
		this.source = source;
	}

	@Override
	public void add( V target )
	{
		arrow.aim( source, target );
	}

	@Override
	public void remove( V target )
	{
		arrow.remove( source, target );
	}

	@Override
	public int size()
	{
		return arrow.targets( source ).size();
	}

	@Override
	public Iterator<V> iterator()
	{
		return arrow.targets( source ).iterator();
	}


	@Override
	public boolean contains( V target )
	{
		return arrow.targets( source ).contains( target );
	}

	@Override
	public Class domain()
	{
		return arrow.codomain();
	}
}
