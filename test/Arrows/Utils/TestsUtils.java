package Arrows.Utils;

import Arrows.ArrowEditor;


public class TestsUtils
{
	public static void connectWordToLetters( ArrowEditor arrow, String word )
	{
		for( int i = 0; i < word.length(); ++i )
		{
			arrow.connect( word, word.charAt( i ) );
		}
	}

	public static void connectLettersToUpperCaseLetters( ArrowEditor arrow, String word )
	{
		for( int i = 0; i < word.length(); ++i )
		{
			char c = word.charAt( i );
			arrow.connect( c, Character.toUpperCase( c ) );
		}
	}

}
