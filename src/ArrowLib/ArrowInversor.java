package ArrowLib;

import java.util.Collection;
import java.util.Set;

public class ArrowInversor implements Arrow
{

	Arrow arrow;

	public ArrowInversor( Arrow arrow )
	{
		this.arrow = arrow;
	}

	@Override
	public ArrowConfig config()
	{
		return arrow.config();//TOFIX
		// trebuie sa fac aceeasi schema si cu configul
	}

	@Override
	public void connect( Object source, Collection targets )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void connect( Object source, Object target )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set relations()
	{
		return arrow.inverseRelations();
		//trebuie intors
	}

	@Override
	public void remove( Object source, Object target )
	{
		throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Set2 sources()
	{
		return arrow.targets();
	}

	@Override
	public Set2 targets()
	{
		return arrow.sources();
	}

	@Override
	public Set2 targets( Object source )
	{
		return null;//trebuie intors si scos in interfata arrow
	}

	@Override
	public Arrow inverse()
	{
		return arrow;
	}

	@Override
	public Set inverseRelations()
	{
		return arrow.relations();
	}

}
