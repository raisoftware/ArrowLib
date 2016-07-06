package Arrows;

public class Set2<K, V>
{
	K source;
	Arrow<K, V> arrow;

	public Set2( K source, Arrow<K, V> arrow )
	{
		this.source = source;
		this.arrow = arrow;
	}

	void add( V target )
	{
		arrow.connect( source, target );
	}

	void remove( V target )
	{
		arrow.remove( source, target );
	}

	void contains( V target )
	{
		//arrow.targets( source );
	}

	int size()
	{
		return 0;
		//arrow.targets(source).size();
	}

	//iterator() : Iterator
}
