package Arrows.Impl;

import Arrows.*;
import Arrows.Utils.ExceptionUtils;
import java.util.*;
import java.util.function.Function;


public class ComputedArrowImpl<K, V> implements ComputedArrow<K, V>
{
	EditableArrow<K, V> precomputedArrow;
	Function<K, Set<V>> function;
	ArrowConfig arrowConfig;

	Arrow<V, K> inverseArrow = new InverseComputedArrow();

	public ComputedArrowImpl( Function<K, Set<V>> function )
	{

		EditableArrowConfig editableArrowConfig = new ArrowBuilderImpl();
		precomputedArrow = new BasicArrow( editableArrowConfig );
		this.arrowConfig = editableArrowConfig.readOnly( true );


		this.function = function;
	}

	@Override
	public void addSource( K source )
	{
		precomputedArrow.connect( source, function.apply( source ) );
	}

	@Override
	public void addSources( Collection<? extends K> sources )
	{
		for( K source : sources )
		{
			precomputedArrow.connect( source, function.apply( source ) );
		}
	}

	@Override
	public void remove( K source )
	{
		precomputedArrow.remove( source, null );
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
		Set<V> targets = targets( source );
		if( targets.size() != 1 )
			throw ExceptionUtils.targetsNumberException( targets.size() );
		return targets.iterator().next();
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
	public Arrow<V, K> inverse()
	{
		return inverseArrow;
	}

	private final class InverseComputedArrow implements Arrow<V, K>
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
			Set<K> targets = targets( source );
			if( targets.size() != 1 )
				throw ExceptionUtils.targetsNumberException( targets.size() );
			return targets.iterator().next();
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
		public Arrow<K, V> inverse()
		{
			return ComputedArrowImpl.this;
		}

	}
}
