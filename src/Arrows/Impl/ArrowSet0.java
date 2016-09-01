package Arrows.Impl;

import Arrows.Arrow;
import Shared.Set0;
import java.util.Iterator;

public class ArrowSet0<K, V> implements Set0<V>
{
	private K source;
	private Arrow<K, V> arrow;

	public ArrowSet0( K source, Arrow<K, V> arrow )
	{
		this.source = source;
		this.arrow = arrow;
	}

	@Override
	public void add( V target )
	{
		arrow.editor().connect( source, target );
	}

	@Override
	public void remove( V target )
	{
		arrow.editor().remove( source, target );
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
}
