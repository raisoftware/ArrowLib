package Arrows.Utils;

import Arrows.EditableArrow;


public class TestsUtils
{
	public static void connectWordToLetters( EditableArrow arrow, String word )
	{
		for( int i = 0; i < word.length(); ++i )
		{
			arrow.connect( word, word.charAt( i ) );
		}
	}

	public static void connectLettersToUpperCaseLetters( EditableArrow arrow, String word )
	{
		for( int i = 0; i < word.length(); ++i )
		{
			char c = word.charAt( i );
			arrow.connect( c, Character.toUpperCase( c ) );
		}
	}

}
