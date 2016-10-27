package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ArrowUtils;
import Shared.Collection0.Set0;
import java.util.*;
import java.util.function.Function;


public class ComputedArrowImpl<K, V> implements ComputedArrow<K, V>
{
	private Arrow<K, V> precomputedArrow;
	private Function<K, Set<V>> function;
	private Diagram diagram;
	private Class domain;
	private Class codomain;

	private ArrowView<V, K> inverseArrow = new InverseComputedArrow();

	private int id = ID_NOT_SET;

	public ComputedArrowImpl( Diagram diagram, Function<K, Set<V>> function, Class domain, Class codomain )
	{
		this.domain = domain;
		this.codomain = codomain;
		precomputedArrow = new GenericArrow( diagram, "ComputedArrow", "InverseComputedArrow", domain, codomain, true, true, false );

		this.diagram = diagram;
		this.function = function;
	}

	@Override
	public void addSource( K source )
	{
		precomputedArrow.aim( source, function.apply( source ) );
	}

	@Override
	public void addSources( Collection<? extends K> sources )
	{
		for( K source : sources )
		{
			precomputedArrow.aim( source, function.apply( source ) );
		}
	}

	@Override
	public void remove( K source )
	{
		precomputedArrow.removeTargets( source );
	}

	@Override
	public Set0<K> sources()
	{
		return precomputedArrow.sources();
	}

	@Override
	public Set0<V> targets()
	{
		return precomputedArrow.targets();
	}

	@Override
	public V target( K source )
	{
		return (V) ArrowUtils.target( this, source );
	}

	@Override
	public Set0<V> targets( K source )
	{
		return precomputedArrow.targets( source );
	}

	@Override
	public Set0<Map.Entry<K, V>> relations()
	{
		return precomputedArrow.relations();
	}

	@Override
	public ArrowView<V, K> inverse()
	{
		return inverseArrow;
	}

	@Override
	public Class codomain()
	{
		return codomain;
	}

	@Override
	public Class domain()
	{
		return domain;
	}

	@Override
	public String toString()
	{
		return ArrowUtils.toString( diagram, this, "ComputedArrow" );
	}

	@Override
	public int id()
	{
		return id;
	}

	@Override
	public void id( int id )
	{
		this.id = id;
	}

	private final class InverseComputedArrow implements ArrowView<V, K>
	{
		private int id = ID_NOT_SET;

		@Override
		public Set0<V> sources()
		{
			return precomputedArrow.targets();
		}

		@Override
		public Set0<K> targets()
		{
			return precomputedArrow.sources();
		}

		@Override
		public K target( V source )
		{
			return (K) ArrowUtils.target( this, source );
		}

		@Override
		public Set0<K> targets( V source )
		{
			return precomputedArrow.inverse().targets( source );
		}

		@Override
		public Set0<Map.Entry<V, K>> relations()
		{
			return precomputedArrow.inverse().relations();
		}

		@Override
		public ArrowView<K, V> inverse()
		{
			return ComputedArrowImpl.this;
		}

		@Override
		public Class codomain()
		{
			return domain;
		}

		@Override
		public Class domain()
		{
			return codomain;
		}

		@Override
		public String toString()
		{
			return ArrowUtils.toString( diagram, this, "InverseComputedArrow" );
		}

		@Override
		public int id()
		{
			return id;
		}

		@Override
		public void id( int id )
		{
			this.id = id;
		}

	}


	public static class Builder<K, V> implements ComputedArrow.Builder<K, V>
	{
		private Class domain = Object.class;
		private Class codomain = Object.class;
		private Function<K, Set<V>> function;
		private Diagram diagram = null;

		public Builder( Diagram diagram )
		{
			super();
			this.diagram = diagram;
		}

		@Override
		public Builder domain( Class allowedClasses )
		{
			this.domain = allowedClasses;
			return this;
		}

		@Override
		public Builder codomain( Class allowedClasses )
		{
			this.codomain = allowedClasses;
			return this;
		}

		@Override
		public Builder function( Function<K, Set<V>> function )
		{
			this.function = function;
			return this;
		}

		@Override
		public ComputedArrow end()
		{
			assert ( diagram != null );
			ComputedArrow arrow = new ComputedArrowImpl( diagram, function, domain, codomain );
			diagram.arrows().add( arrow );
			return arrow;
		}
	}
}
