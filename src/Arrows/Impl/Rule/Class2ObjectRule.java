package Arrows.Impl.Rule;

import Arrows.*;

import static Arrows.Arrows.Names.*;

public class Class2ObjectRule implements Arrow.Editor
{
	private Arrow<Class, Object> class2Object = null;
	private Arrow<Arrow, Object> inboundArrow2object;
	private Arrow<Arrow, Object> outboundArrow2object;

	public Class2ObjectRule( Arrows arrows )
	{
		try
		{
			this.class2Object = (Arrow) arrows.arrow( Class_Object );
			this.inboundArrow2object = (Arrow) arrows.arrow( InboundArrow_Object );
			this.outboundArrow2object = (Arrow) arrows.arrow( OutboundArrow_Object );
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}

	Arrow<Class, Object> arrow()
	{
		return class2Object;
	}

	@Override
	public void connect( Object source, Iterable targets )
	{
		if( source == null || targets == null || !targets.iterator().hasNext() )
			return;

		class2Object.editor().connect( source.getClass(), source );

		for( Object target : targets )
		{
			class2Object.editor().connect( target.getClass(), target );
		}
	}

	@Override
	public void connect( Iterable sources, Object target )
	{
		if( target == null || sources == null || !sources.iterator().hasNext() )
			return;

		class2Object.editor().connect( target.getClass(), target );

		for( Object source : sources )
		{
			class2Object.editor().connect( source.getClass(), sources );
		}
	}

	@Override
	public void connect( Object source, Object target )
	{
		if( source == null || target == null )
			return;
		class2Object.editor().connect( source.getClass(), source );
		class2Object.editor().connect( target.getClass(), target );
	}

	@Override
	public void remove( Object source, Object target )
	{
		//TOFIX implement this
	}
}
