package Arrows.Impl.Rule;

import Arrows.*;
import Arrows.ArrowEditor;

public class ObjectRegistrarRule implements ArrowEditor
{

	private Objects objects;

	public ObjectRegistrarRule( Objects objects )
	{
		this.objects = objects;
	}

	private void addObject( Object object )
	{
		if( !objects.contains( object ) )
		{
			objects.add( object );
		}
	}

	@Override
	public void aim( Object source, Iterable targets )
	{
		if( source == null || targets == null || !targets.iterator().hasNext() )
			return;

		addObject( source );
		for( Object target : targets )
		{
			addObject( target );
		}
	}

	@Override
	public void aim( Iterable sources, Object target )
	{
		if( target == null || sources == null || !sources.iterator().hasNext() )
			return;

		addObject( target );
		for( Object source : sources )
		{
			addObject( source );
		}
	}

	@Override
	public void aim( Object source, Object target )
	{
		if( source == null || target == null )
			return;

		addObject( source );
		addObject( target );
	}


	@Override
	public void remove( Object source, Object target )
	{
		//TOFIX implement
	}

	@Override
	public void removeAll( Object source, Iterable targets )
	{
		//TOFIX implement
	}

	@Override
	public void removeAll( Iterable sources, Object target )
	{
		//TOFIX implement
	}
}
