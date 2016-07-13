package Arrows.Impl;

import Arrows.*;
import java.util.*;

public class Class2ObjectRule implements ArrowListener
{
	final private Arrow<Class, Object> class2Objects;

	public Class2ObjectRule( Arrow<Class, Object> class2Objects )
	{
		this.class2Objects = class2Objects;
	}

	Arrow<Class, Object> arrow()
	{
		return class2Objects;
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

		class2Objects.connect( source.getClass(), targets );

		for( Object target : targets )
		{
			class2Objects.connect( target.getClass(), target );
		}
	}

	@Override
	public void connect( Object source, Object target )
	{
		if( source == null || target == null )
			return;
		class2Objects.connect( source.getClass(), source );
		class2Objects.connect( target.getClass(), target );
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

}
