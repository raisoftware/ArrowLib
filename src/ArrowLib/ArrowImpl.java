package ArrowLib;

import ArrowLib.Utils.ManyToManyMap;
import java.util.*;

public class ArrowImpl<K, V> implements Arrow<K, V>
{
	ArrowConfig config;

	ManyToManyMap<K, V> map = new ManyToManyMap();
//	Set<K> sources = new HashSet<>();
//	Set<V> targets = new HashSet<>();
//	Set<Pair<K, V>> relations = new HashSet<>();

	public ArrowImpl( ArrowConfig arrowConfig )
	{
		this.config = arrowConfig;
	}

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
	@Override
	public void connect( K source, Collection<? extends V> targets )
	{
		map.putAll( source, targets );

//		this.sources.add( source );
//		this.targets.addAll( targets );
//
//		for( V target : targets )
//		{
//			this.relations.add( new Pair( source, target ) );
//		}
	}

	@Override
	public void connect( K source, V target )
	{
		map.put( source, target );
//		this.sources.add( source );
//		this.targets.add( target );
//		this.relations.add( new Pair( source, target ) );
	}

	@Override
	public Set2 sources()
	{
		return null;
	}

	@Override
	public Set2 targets()
	{
		return null;
	}

	@Override
	public Set<Map.Entry<K, V>> relations()
	{
		return null;
	}

	@Override
	public Set2 targets( K source )
	{
		return null;
	}

	Arrow inverse()
	{
		return null;
	}

	@Override
	public void remove( K source, V target )
	{

	}

	@Override
	public ArrowConfig config()
	{
		return config;
	}
}
