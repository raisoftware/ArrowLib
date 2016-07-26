package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ArrowUtils;
import java.util.*;
import java.util.function.Function;


public class ComputedArrowImpl<K, V> implements ComputedArrow<K, V>
{
	Arrow<K, V> precomputedArrow;
	Function<K, Set<V>> function;
	ArrowConfig arrowConfig;

	ArrowView<V, K> inverseArrow = new InverseComputedArrow();

	public ComputedArrowImpl( Function<K, Set<V>> function )
	{

		EditableArrowConfig editableArrowConfig = new ArrowBuilderImpl();
		precomputedArrow = new GenericArrow();
		this.arrowConfig = editableArrowConfig.readOnly( true );


		this.function = function;
	}

	@Override
	public void addSource( K source )
	{
		precomputedArrow.editor().connect( source, function.apply( source ) );
	}

	@Override
	public void addSources( Collection<? extends K> sources )
	{
		for( K source : sources )
		{
			precomputedArrow.editor().connect( source, function.apply( source ) );
		}
	}

	@Override
	public void remove( K source )
	{
		precomputedArrow.editor().remove( source, null );
	}

	@Override
	public Set<K> sources()
	{
		return precomputedArrow.sources();
	}

	@Override
	public Set<V> targets()
	{
		return precomputedArrow.targets();
	}

	@Override
	public ArrowConfig config()
	{
		return arrowConfig;
	}

	@Override
	public V target( K source ) throws Exception
	{
		return (V) ArrowUtils.target( this, source );
	}

	@Override
	public Set<V> targets( K source )
	{
		return precomputedArrow.targets( source );
	}

	@Override
	public Set<Map.Entry<K, V>> relations()
	{
		return precomputedArrow.relations();
	}

	@Override
	public ArrowView<V, K> inverse()
	{
		return inverseArrow;
	}

	private final class InverseComputedArrow implements ArrowView<V, K>
	{

		@Override
		public ArrowConfig config()
		{
			return arrowConfig;
		}

		@Override
		public Set<V> sources()
		{
			return precomputedArrow.targets();
		}

		@Override
		public Set<K> targets()
		{
			return precomputedArrow.sources();
		}

		@Override
		public K target( V source ) throws Exception
		{
			return (K) ArrowUtils.target( this, source );
		}

		@Override
		public Set<K> targets( V source )
		{
			return precomputedArrow.inverse().targets( source );
		}

		@Override
		public Set<Map.Entry<V, K>> relations()
		{
			return precomputedArrow.inverse().relations();
		}

		@Override
		public ArrowView<K, V> inverse()
		{
			return ComputedArrowImpl.this;
		}
	}
}
