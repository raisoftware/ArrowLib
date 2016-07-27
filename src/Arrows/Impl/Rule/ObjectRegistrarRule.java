package Arrows.Impl.Rule;

import Arrows.*;
import Arrows.Arrow.Editor;

public class ObjectRegistrarRule implements Editor
{

	Objects objects;

	public ObjectRegistrarRule( Objects objects )
	{
		this.objects = objects;
	}

	@Override
	public void connect( Object source, Iterable targets )
	{
		if( source == null || targets == null || !targets.iterator().hasNext() )
			return;

		if( !objects.contains( source ) )
		{
			objects.add( source );
		}
		for( Object target : targets )
		{
			if( !objects.contains( target ) )
			{
				objects.add( target );
			}
		}
	}

	@Override
	public void connect( Iterable sources, Object target )
	{
		if( target == null || sources == null || !sources.iterator().hasNext() )
			return;
		if( !objects.contains( target ) )
		{
			objects.add( target );
		}
		for( Object source : sources )
		{
			if( !objects.contains( source ) )
			{
				objects.add( source );
			}
		}
	}

	@Override
	public void connect( Object source, Object target )
	{
		if( source == null || target == null )
			return;

		if( !objects.contains( source ) )
		{
			objects.add( source );
		}

		if( !objects.contains( target ) )
		{
			objects.add( target );
		}
	}


	@Override
	public void remove( Object source, Object target )
	{
		//TOFIX implement
	}
}
