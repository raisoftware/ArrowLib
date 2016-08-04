package Arrows;

import Arrows.Impl.*;
import Shared.Set0;
import java.util.function.BiPredicate;

public interface Diagram
{
	static Diagram create()
	{
		return new DiagramImpl();
	}

	Arrows arrows();

	Objects objects();

	<K, V> Set0<V> set0( K source, Arrow<K, V> arrow );

	Reference reference( Object nick, Arrow domain );

	GenericArrowBuilder createGeneric();

	ComputedArrow.Builder createComputed();

	ArrowView filter( ArrowView arrow, BiPredicate filter );

	ArrowView union( ArrowView... arrows );

	ArrowView intersect( ArrowView... arrows );

	ArrowView join( ArrowView... arrows );

}
