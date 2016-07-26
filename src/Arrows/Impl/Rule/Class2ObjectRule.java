package Arrows.Impl.Rule;

import Arrows.*;
import java.util.*;

import static Arrows.Arrows.StandardArrowName.*;

public class Class2ObjectRule implements Arrow.Editor
{
	private Arrow<Class, Object> class2Object = null;

	public Class2ObjectRule( Arrows arrows )
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
	public void connect( Object source, Collection targets )
	{
		if( source == null || targets == null || targets.isEmpty() )
			return;

		class2Object.editor().connect( source.getClass(), source );

		for( Object target : targets )
		{
			class2Object.editor().connect( target.getClass(), target );
		}
	}

	@Override
	public void connect( Collection sources, Object target )
	{
		if( target == null || sources == null || sources.isEmpty() )
			return;

		class2Object.editor().connect( target.getClass(), target );

		for( Object source : sources )
		{
			class2Object.editor().connect( source.getClass(), sources );
		}
	}

	@Override
	public void connect( Object source, Object target )
	{
		if( source == null || target == null )
			return;
		class2Object.editor().connect( source.getClass(), source );
		class2Object.editor().connect( target.getClass(), target );
	}

	@Override
	public void remove( Object source, Object target )
	{
		//TOFIX implement this
	}
}
