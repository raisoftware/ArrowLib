package Arrows.Impl;

import Arrows.*;
import Shared.MethodBus.Sequence.MethodSequence;
import java.util.Iterator;

public class ArrowsImpl implements Arrows
{
	EditableArrow<Enum, Arrow> name2arrow;
	final private MethodSequence methodSequence;

	public ArrowsImpl( MethodSequence<EditableArrow, ArrowListener> methodSequence )
	{
		this.methodSequence = methodSequence;
		EditableArrowConfig editableArrowConfig = create( StandardArrowName.Name2Arrow, StandardArrowName.Arrow2Name ).listenable( false );

		ArrowConfig arrowConfig = editableArrowConfig.allowsMultipleTargets( false ).domain( Enum.class ).codomain( Arrow.class ).arrowConfig();
		name2arrow = new BasicArrow( arrowConfig );
		add( StandardArrowName.Name2Arrow, StandardArrowName.Arrow2Name, name2arrow );
	}

	@Override
	public final ArrowBuilder create( Enum arrowName, Enum inverseArrowName )
	{
		return new ArrowBuilderImpl( this, arrowName, inverseArrowName );
	}

	@Override
	public final void add( Enum arrowName, Enum arrowInverseName, Arrow arrow )
	{
		name2arrow.connect( arrowName, arrow );
		name2arrow.connect( arrowInverseName, arrow.inverse() );
	}

	@Override
	public Arrow arrow( Enum arrowName ) throws Exception
	{
		return name2arrow.target( arrowName );
	}

	@Override
	public EditableArrow editableArrow( Enum arrowName ) throws Exception
	{
		Arrow arrow = name2arrow.target( arrowName );
		if( arrow.config().readOnly() )
		{
			throw new Exception( "Arrow is read-only." );
		}
		return (EditableArrow) arrow;
	}

	@Override
	public MethodSequence<EditableArrow, ArrowListener> rules()
	{
		return methodSequence;
	}

	@Override
	public void add( Object target )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void remove( Object target )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean contains( Object target )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int size()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Iterator iterator()
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

}
