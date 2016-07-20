package Arrows.Impl;

import Arrows.EditableArrow;
import Arrows.Set2;
import java.util.Iterator;

public class Set2Impl<K, V> implements Set2<K, V>
{
	K source;
	EditableArrow<K, V> arrow;

	public Set2Impl( K source, EditableArrow<K, V> arrow )
	{
		this.source = source;
		this.arrow = arrow;
	}

	@Override
	public void add( V target )
	{
		arrow.connect( source, target );
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
}
