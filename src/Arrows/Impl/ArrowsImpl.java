package Arrows.Impl;

import Arrows.*;
import Arrows.Objects;
import java.util.*;

public class ArrowsImpl implements Arrows
{
	Arrow<Enum, Arrow> name2arrow;

	Objects objects;

	void objects( Objects objects )
	{
		this.objects = objects;
	}

	public ArrowsImpl()
	{
		this.objects = null;
		EditableArrowConfig editableArrowConfig = create( StandardArrowName.Name2Arrow, StandardArrowName.Arrow2Name ).listenable( false );

		ArrowConfig arrowConfig = editableArrowConfig.allowsMultipleTargets( false ).domain( Enum.class ).codomain( Arrow.class ).arrowConfig();
		name2arrow = new GenericArrow( arrowConfig, false );
		add( StandardArrowName.Name2Arrow, StandardArrowName.Arrow2Name, name2arrow );
	}

	@Override
	public final ArrowBuilder create( Enum arrowName, Enum inverseArrowName )
	{
		return new ArrowBuilderImpl( this, objects, arrowName, inverseArrowName );
	}

	@Override
	public final void add( Enum arrowName, Enum arrowInverseName, Arrow arrow )
	{
		name2arrow.editor().connect( arrowName, arrow );
		name2arrow.editor().connect( arrowInverseName, arrow.inverse() );
	}

	@Override
	public Arrow arrow( Enum arrowName ) throws Exception
	{
		return name2arrow.target( arrowName );
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
