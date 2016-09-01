package Arrows;


public interface Property<T>
{
	T get();
	void set( T newValue ); // similar to set.clear(); set.add( newValue );
}
