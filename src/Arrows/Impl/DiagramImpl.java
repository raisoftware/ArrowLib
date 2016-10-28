package Arrows.Impl;

import Arrows.*;
import Shared.Collection0.Set0;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;


public class DiagramImpl implements Diagram
{
	ArrowsImpl arrows;
	Objects objects;
	private final AtomicInteger sequence = new AtomicInteger();

	public DiagramImpl()
	{
		arrows = new ArrowsImpl( this );
		objects = new ObjectsImpl( this );
	}

	@Override
	public final Arrows arrows()
	{
		return arrows;
	}

	@Override
	public Objects objects()
	{
		return objects;
	}

	@Override
	public Reference reference( Object nick, Arrow arrow )
	{
		return new Reference( nick, arrow );
	}

	@Override
	public <K, V> Set0<V> set0( Arrow<K, V> arrow, K source )
	{
		return new ArrowSet0( arrow, source );
	}

	@Override
	public ArrowView filter( ArrowView arrow, BiPredicate filter )
	{
		return new FilterArrow( this, arrow, filter );
	}

	@Override
	public ArrowView join( ArrowView... arrows )
	{
		return new JoinArrow( this, arrows );
	}

	@Override
	public ArrowView union( ArrowView... arrows )
	{
		return new UnionArrow( this, arrows );
	}

	@Override
	public ArrowView intersect( ArrowView... arrows )
	{
		return new IntersectArrow( this, arrows );
	}

	@Override
	public final GenericArrowBuilder createGeneric()
	{
		return new GenericArrowBuilder( this );
	}

	@Override
	public final ComputedArrow.Builder createComputed()
	{
		return new ComputedArrowImpl.Builder( this );
	}

	@Override
	public ArrowView identity( Set0 set )
	{
		//TOFIX
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public <T> T identify( T object )
	{
		Set0<Object> foundObjects = objects.identity().targets( object );

		if( foundObjects.size() == 0 )
			return null;

		if( foundObjects.size() > 1 )
			throw new RuntimeException( "Found too many objects" );


		return (T) foundObjects.iterator().next();
	}

	@Override
	public <T> void remove( T object )
	{
		Object foundObject = objects.identity().target( object );
		objects.remove( foundObject );
	}

	@Override
	public void remove( ArrowView arrow )
	{
		arrows.remove( arrow );
	}

	@Override
	public int incrementAndGetSequence()
	{
		return sequence.incrementAndGet();
	}

	@Override
	public int getAndIncrementSequence()
	{
		return sequence.getAndIncrement();
	}

}
