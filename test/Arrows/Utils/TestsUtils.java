package Arrows.Utils;

import Arrows.Arrow.Editor;


public class TestsUtils
{
	public static void connectWordToLetters( Editor arrow, String word )
	{
		for( int i = 0; i < word.length(); ++i )
		{
			arrow.connect( word, word.charAt( i ) );
		}
	}

	public static void connectLettersToUpperCaseLetters( Editor arrow, String word )
	{
		for( int i = 0; i < word.length(); ++i )
		{
			char c = word.charAt( i );
			arrow.connect( c, Character.toUpperCase( c ) );
		}
	}

}
