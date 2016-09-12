package Shared.Collection0;

public interface OrderedSet0<T> extends Set0<T>
{
	public enum Position
	{
		Beginning,
		End,
		Before,
		After
	}

	int indexOf( T item );

	void insert( T item, Position position, T insertPoint );
}
