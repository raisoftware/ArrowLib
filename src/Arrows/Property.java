package Arrows;


public interface Property<T>
{
	T get();
	void set( T newValue ); // similar to set.clear(); set.add( newValue );

	static <K, V> Property<V> property( Arrow<K, V> arrow, K source, V initialValue )
	{
		return new ArrowProperty<>( arrow, source, initialValue );
	}
}
