package Arrows.Utils;

import Arrows.*;
import Arrows.Impl.JoinArrow;
import Shared.Collection0.*;
import java.io.*;
import java.util.*;
import java.util.function.BiPredicate;

import static Arrows.Arrows.Names.*;


public class ArrowUtils
{
	private ArrowUtils()
	{
	}

	public static ArrowView filterByClassArrow( Diagram diagram, ArrowView arrow, Class clazz )
	{
		BiPredicate<Object, Object> filter = (Object source, Object target) -> clazz.isInstance( target );
		return diagram.filter( arrow, filter );
	}

	public static Set0 generateRelations( ArrowView arrow )
	{
		Set0<Map.Entry> relations = new BasicSet0( new HashSet<>(), Map.Entry.class );
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

	public static Object target( ArrowView arrow, Object source )
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

	public static String shortToString( Diagram diagram, ArrowView arrow )
	{
		ArrowView<ArrowView, Integer> arrow2id = diagram.arrows().arrowView( Arrow_Id );
		ArrowView arrow2name = diagram.arrows().arrowView( Arrow_Name );


		Set0<Integer> ids = arrow2id.targets( arrow );

		if( Sets.isEmpty( ids ) )
			return "Unknown id/name";

		Integer id = ids.iterator().next();

		Set0<Object> names = arrow2name.targets( arrow );
		if( names.size() > 1 )
			throw new RuntimeException( "More arrows with the same name" );

		Object name;
		if( Sets.isEmpty( names ) )
		{
			name = "Unnamed";
		}
		else
		{
			name = names.iterator().next();
		}
		String idName = String.format( "(#%03d_%s)", id, name );

		return idName;
	}

	public static String toString( Diagram diagram, ArrowView arrow, String arrowType )
	{
		return String.format( "%s = %s<%s,%s>", shortToString( diagram, arrow ), arrowType, arrow.domain().getSimpleName(), arrow.codomain().getSimpleName() );// + "  Relations:" + arrow.relations();
	}

	public static void generateGraph( Diagram diagram, String path )
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
			Arrow<Integer, ArrowView> id_arrow = arrows.arrow( Id_Arrow );


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

	private static void generateGraphForJoinArrow( Diagram diagram, ArrowView arrow, String path )
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
