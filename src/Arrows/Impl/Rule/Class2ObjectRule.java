package Arrows.Impl.Rule;

import Arrows.*;

import static Arrows.Arrows.Names.*;

public class Class2ObjectRule implements Arrow.Editor
{
	private Arrow<Class, Object> class2Object = null;
	private ArrowView<Arrow, Object> inboundArrow2object;
	private ArrowView<Arrow, Object> outboundArrow2object;
	private ArrowView<Object, ObjectConfig> object2config;

	public Class2ObjectRule( Arrows arrows )
	{
		try
		{
			this.class2Object = (Arrow) arrows.arrow( Class_Object );

			//This Rule should be executed after the ObjectRegistrarRule and after the Arrow2ObjectRule

			this.inboundArrow2object = arrows.arrow( InboundArrow_Object );
			this.outboundArrow2object = arrows.arrow( OutboundArrow_Object );
			this.object2config = arrows.arrow( Object_Config );
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

		trackClass( source );

		for( Object target : targets )
		{
			trackClass( target );
		}
	}

	@Override
	public void connect( Iterable sources, Object target )
	{
		if( target == null || sources == null || !sources.iterator().hasNext() )
			return;

		trackClass( target );

		for( Object source : sources )
		{
			trackClass( source );
		}
	}

	@Override
	public void connect( Object source, Object target )
	{
		if( source == null || target == null )
			return;
		trackClass( source );
		trackClass( target );
	}

	@Override
	public void remove( Object source, Object target )
	{
		if( !inboundArrow2object.targets().contains( source ) && !outboundArrow2object.targets().contains( source ) )
		{
			class2Object.inverse().editor().remove( source, null );
		}

		if( !inboundArrow2object.targets().contains( target ) && !outboundArrow2object.targets().contains( target ) )
		{
			class2Object.inverse().editor().remove( target, null );
		}
	}


	private void trackClass( Object object )
	{

		if( config( object ).tracksClass() )
		{
			class2Object.editor().connect( object.getClass(), object );
		}
	}

	private ObjectConfig config( Object object )
	{
		try
		{
			return object2config.target( object );
		}
		catch( Exception ex )
		{
			throw new RuntimeException( ex.getMessage() );
		}
	}
}