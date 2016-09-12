package Arrows;

import Shared.Collection0.Set0;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ArrowProperty<K, V> implements Property<V>
{
	Arrow<K, V> arrow;
	K source;

	public ArrowProperty( Arrow<K, V> arrow, K source, V initialValue )
	{
		this.arrow = arrow;
		this.source = source;
		this.arrow.editor().connect( source, initialValue );
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
		return arrow.inverse().targets( get() );
	}

	@Override
	public V get()
	{
		try
		{
			return arrow.target( source );
		}
		catch( Exception ex )
		{
			//TOFIX
			Logger.getLogger( ArrowProperty.class.getName() ).log( Level.SEVERE, null, ex );
		}
		return null;
	}

	@Override
	public void set( V newValue )
	{
		arrow.editor().remove( source, null );
		arrow.editor().connect( source, newValue );
	}

}
