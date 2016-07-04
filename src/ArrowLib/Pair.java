package ArrowLib;

public class Pair<A, B>
{
	private final A source;
	private final B target;

	public Pair( A source, B target )
	{
		super();
		this.source = source;
		this.target = target;
	}

	public int hashCode()
	{
		int hashFirst = source != null ? source.hashCode() : 0;
		int hashSecond = target != null ? target.hashCode() : 0;

		return ( hashFirst + hashSecond ) * hashSecond + hashFirst;
	}

	public boolean equals( Object other )
	{
		if( other instanceof Pair )
		{
			Pair otherPair = (Pair) other;
			return ( ( this.source == otherPair.source
				|| ( this.source != null && otherPair.source != null
				&& this.source.equals( otherPair.source ) ) )
				&& ( this.target == otherPair.target
				|| ( this.target != null && otherPair.target != null
				&& this.target.equals( otherPair.target ) ) ) );
		}

		return false;
	}

	public String toString()
	{
		return "(" + source + ", " + target + ")";
	}

	public A getSource()
	{
		return source;
	}

	public B getTarget()
	{
		return target;
	}

}
