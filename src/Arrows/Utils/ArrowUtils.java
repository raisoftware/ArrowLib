package Arrows.Utils;

import Arrows.*;
import Arrows.Impl.JoinArrow;
import Shared.BasicSet0;
import Shared.Set0;
import java.io.*;
import java.util.*;

import static Arrows.Arrows.Names.*;


public class ArrowUtils
{
	public static Set0 generateRelations( ArrowView arrow )
	{
		Set0<Map.Entry> relations = new BasicSet0( new HashSet<>() );
		for( Object source : arrow.sources() )
		{
			for( Object target : arrow.targets( source ) )
			{
				Map.Entry<Object, Object> entry = new AbstractMap.SimpleEntry<>( source, target );
				relations.add( entry );
			}
		}
		return relations;
	}

	public static Object target( ArrowView arrow, Object source ) throws Exception
	{
		Set0 targets = arrow.targets( source );
		if( targets.size() != 1 )
			throw ExceptionUtils.targetsNumberException( targets.size() );
		return targets.iterator().next();
	}

	public static void checkForMultipleSourcesTargets( ArrowView arrow, boolean allowsMultipleTargets, boolean allowsMultipleSources, Object source, Iterable<? extends Object> targets )
	{
		if( !allowsMultipleTargets && arrow.sources().contains( source ) )
		{
			throw ExceptionUtils.multipleSourcesTargetsException( source, targets );
		}
		if( !allowsMultipleSources )
		{
			for( Object target : targets )
			{
				if( arrow.targets().contains( target ) )
				{
					throw ExceptionUtils.multipleSourcesTargetsException( source, targets );
				}
			}
		}
	}

	public static String shortToString( ArrowDiagram diagram, ArrowView arrow )
	{
		String idName = "";
		try
		{
			ArrowView<ArrowView, Integer> arrow2id = diagram.arrows().arrow( Arrow_Id );
			ArrowView arrow2name = diagram.arrows().arrow( Arrow_Name );

			Integer id = arrow2id.target( arrow );
			String formattedId = String.format( "%03d", id );

			Object name;
			try
			{
				name = arrow2name.target( arrow );
			}
			catch( Exception ex )
			{
				name = "Unnamed";
			}
			idName += "(#" + formattedId + "-" + name + ")";
		}
		catch( Exception ex )
		{
			idName = "Unknown id/name";
		}

		return idName;
	}

	public static String toString( ArrowDiagram diagram, ArrowView arrow, String arrowType )
	{
		return shortToString( diagram, arrow ) + " = " + arrowType + "<" + arrow.domain() + "," + arrow.codomain() + ">";// + "  Relations:" + arrow.relations();
	}

	public static void generateGraph( ArrowDiagram diagram, String path )
	{
		File dir = new File( path );

		dir.mkdirs();

		for( File file : dir.listFiles() )
		{
			file.delete();
		}

		Arrows arrows = diagram.arrows();
		try
		{
			Arrow<Integer, ArrowView> id_arrow = (Arrow) arrows.arrow( Id_Arrow );


			for( ArrowView arrow : id_arrow.targets() )
			{
				if( arrow instanceof JoinArrow )
				{
					generateGraphForJoinArrow( diagram, arrow, path );
				}

				try( Writer writer = new BufferedWriter( new OutputStreamWriter(
					new FileOutputStream( path + shortToString( diagram, arrow ) + ".dot" ), "utf-8" ) ) )
				{
					writer.write( "digraph Arrow{\n" );
					for( Object source : arrow.sources() )
					{
						for( Object target : arrow.targets( source ) )
						{
							writer.write( "\"" + source + "\"->\"" + target + "\"\n" );
						}
					}
					writer.write( "}" );
				}
			}
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}

	private static void generateGraphForJoinArrow( ArrowDiagram diagram, ArrowView arrow, String path )
	{
		//Incomplete
		final String colors[] = new String[]
		{
			"blue", "yellow", "green", "red", "orange", "lightgrey", "purple"
		};

		try( Writer writer = new BufferedWriter( new OutputStreamWriter(
			new FileOutputStream( path + shortToString( diagram, arrow ) + "-JoinView.dot" ), "utf-8" ) ) )
		{
			writer.write( "digraph JoinArrow{\n" );
			List<ArrowView> subarrows = ( (JoinArrow) arrow ).arrows();
			int i = 0;
			for( ArrowView subarrow : subarrows )
			{
				writer.write( "subgraph cluster_" + i + " {\n" );
				writer.write( "label = \"" + subarrow + "\";\n" );
				writer.write( "color=" + colors[i % colors.length] + ";\n" );
				for( Object source : subarrow.sources() )
				{
					for( Object target : subarrow.targets( source ) )
					{
						writer.write( "\"" + source + "\"->\"" + target + "\";\n" );
					}
				}

				writer.write( "}\n\n" );
				++i;

			}
			writer.write( "}" );

		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
}
