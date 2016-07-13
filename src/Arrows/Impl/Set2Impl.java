package Arrows.Impl;

import Arrows.Arrow;
import Arrows.Set2;

public class Set2Impl<K, V> implements Set2<K, V>
{
	K source;
	Arrow<K, V> arrow;

	public Set2Impl( K source, Arrow<K, V> arrow )
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
		return 0;
		//arrow.targets(source).size();
	}

	//iterator() : Iterator
	@Override
	public boolean contains( V target )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}
}
