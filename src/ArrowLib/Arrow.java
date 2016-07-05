package ArrowLib;

import java.util.*;

public interface Arrow<K, V>
{

	ArrowConfig config();

	//	names() : Set<Enum> // evaluates the Arrows.sources()
	//    config() : ArrowConfig
	//    inverse() : Arrow; // returns the reciprocical arrow; zero cost operation. If inverseDisabled throws exception (maybe we should inverse only on demand)
	//    connect( Object source, Collection targets ); // map.put()
	//    connect( Collection sources, Object target );
	//    remove( domainObject, codomainObject ) // if domain or codomain object is null remove the entire relation
	//    targets( Object source ) : Set2< Object > // similar to function eval() ; arrow( source )
	//    // source( Object target ) // not necessary because we can write arrow.inverse().source( target )
	//    sources() : Set2 // coimage
	//    targets() : Set2 // image
	//    relations() : Set< Pair<> >
	void connect( K source, Collection<? extends V> targets );

	void connect( K source, V target );

	Set<Map.Entry<K, V>> relations();

	Set<Map.Entry<V, K>> inverseRelations();

	void remove( K source, V target );

	Set2 sources();

	Set2 targets();

	Set2 targets( K source );

	Arrow inverse();

}
