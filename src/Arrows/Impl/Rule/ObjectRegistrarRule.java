package Arrows.Impl.Rule;

import Arrows.*;
import Arrows.Arrow.Editor;
import java.util.Collection;

public class ObjectRegistrarRule implements Editor
{

	Objects objects;

	public ObjectRegistrarRule( Arrows arrows, Objects objects )
	{
		this.objects = objects;
	}

	@Override
	public void connect( Object source, Collection targets )
	{
		if( source == null || targets == null || targets.isEmpty() )
			return;

		objects.add( source );

		for( Object target : targets )
		{
			objects.add( target );
		}
	}

	@Override
	public void connect( Collection sources, Object target )
	{
		if( target == null || sources == null || sources.isEmpty() )
			return;

		objects.add( target );

		for( Object source : sources )
		{
			objects.add( source );
		}
	}

	@Override
	public void connect( Object source, Object target )
	{
		if( source == null || target == null )
			return;

		objects.add( source );
		objects.add( target );
	}


	@Override
	public void remove( Object source, Object target )
	{
		//TOFIX implement
	}
}
