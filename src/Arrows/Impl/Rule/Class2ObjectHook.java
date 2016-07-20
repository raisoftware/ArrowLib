package Arrows.Impl.Rule;

import Arrows.*;

import static Arrows.Arrows.StandardArrowName.*;

import java.util.*;

public class Class2ObjectHook implements ArrowListener
{
	private Arrow<Class, Object> class2Object = null;

	public Class2ObjectHook( Arrows arrows )
	{
		try
		{
			this.class2Object = arrows.arrow( Class2Object );
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}

	Arrow<Class, Object> arrow()
	{
		return class2Object;
	}

	@Override
	public ArrowConfig config()
	{
		return null;
	}

	@Override
	public void connect( Object source, Collection targets )
	{
		if( source == null || targets == null || targets.isEmpty() )
			return;

		class2Object.connect( source.getClass(), targets );

		for( Object target : targets )
		{
			class2Object.connect( target.getClass(), target );
		}
	}

	@Override
	public void connect( Object source, Object target )
	{
		if( source == null || target == null )
			return;
		class2Object.connect( source.getClass(), source );
		class2Object.connect( target.getClass(), target );
	}

	@Override
	public Set relations()
	{
		return null;
	}

	@Override
	public void remove( Object source, Object target )
	{
	}

	@Override
	public Set sources()
	{
		return null;
	}

	@Override
	public Set targets()
	{
		return null;
	}

	@Override
	public Arrow inverse()
	{
		return null;
	}

	@Override
	public Set targets( Object source )
	{
		return null;
	}

	@Override
	public Object target( Object source ) throws Exception
	{
		return null;
	}

	@Override
	public void setTargetObject( Arrow target )
	{
	}

}
