package Arrows.Impl.Rule;

import Arrows.*;

import java.util.Collection;
import java.util.Set;

public class ObjectRegistrarRule implements ArrowListener
{

	Objects objects;
	//Arrow listenedArrow = null;

	public ObjectRegistrarRule( Arrows arrows, Objects objects )
	{
		this.objects = objects;
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

		objects.add( source );

		for( Object target : targets )
		{
			objects.add( target );
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
		//this.listenedArrow = target;
	}
}
