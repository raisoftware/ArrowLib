package Arrows;

import java.util.HashMap;
import java.util.Map;

public class Arrows
{
	Map<Enum, Arrow> arrows = new HashMap();

	public Arrows()
	{

	}

	public void add( Arrow arrow )
	{
		arrows.put( arrow.config().getName(), arrow );
		arrows.put( arrow.config().getInverseName(), arrow.inverse() );
	}

}
