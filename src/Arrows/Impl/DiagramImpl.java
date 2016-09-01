package Arrows.Impl;

import Arrows.*;
import Shared.Set0;
import java.util.function.BiPredicate;

public class DiagramImpl implements ArrowDiagram
{
	ArrowsImpl arrows;
	Objects objects;

	public DiagramImpl()
	{
		arrows = new ArrowsImpl( this );
		objects = new ObjectsImpl( arrows );
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
	public <K, V> Set0<V> set0( K source, Arrow<K, V> arrow )
	{
		return new ArrowSet0( source, arrow );
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

}
