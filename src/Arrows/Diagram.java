package Arrows;

import Arrows.Impl.*;
import Shared.Collection0.Set0;
import java.util.function.BiPredicate;

public interface Diagram
{
	static Diagram create()
	{
		return new DiagramImpl();
	}

	Arrows arrows();

	Objects objects();

	<K, V> Set0<V> set0( Arrow<K, V> arrow, K source );

	Reference reference( Object nick, Arrow domain );

	GenericArrowBuilder createGeneric();

	ComputedArrow.Builder createComputed();

	ArrowView filter( ArrowView arrow, BiPredicate filter );

	ArrowView union( ArrowView... arrows );

	ArrowView intersect( ArrowView... arrows );

	ArrowView join( ArrowView... arrows );

	ArrowView identity( Set0 set );
}
