package Arrows;

public class Reference
{
	Object nick;
	ArrowView arrow;

	public Reference( Object nick, ArrowView domain )
	{
		this.nick = nick;
		this.arrow = arrow;
	}

	public Object value() throws Exception
	{
		return arrow.target( nick );
	}
}
