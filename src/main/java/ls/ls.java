package ls;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ls {
	
	boolean help;
	boolean all;
	boolean size;
	boolean reverse;
	boolean comma;
	boolean modified;
	
	public static void main(String[] args) {

		ls myLs = new ls();
		myLs.run(args);

	}

	private void run(String[] args) {
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
				}
			
			String current = System.getProperty("user.dir");
			File dirFile = new File(current);
			File []fileList = dirFile.listFiles();
			
			if(all) {
				for(File tempFile: fileList) {
					String tempFileName = tempFile.getName();
					System.out.println(tempFileName);
				}
				return;
			}
			
			if(size) {
				HashMap<Long, String> map = new HashMap<Long, String>();
				for(File tempFile: fileList) {
					String tempFileName = tempFile.getName();
					if( tempFileName.charAt(0)!='.' ) {
						map.put(tempFile.length(), tempFileName);
					}
				}
				TreeMap<Long,String> tm = new TreeMap<Long,String>(map);
				Iterator<Long> iteratorKey = tm.keySet().iterator();
				while(iteratorKey.hasNext()) {
					Long key = iteratorKey.next();
					System.out.println(key + " " + tm.get(key));
				}			
				return;
			}
			
			if(reverse) {
				ArrayList<String> list = new ArrayList<String>();
				for(File tempFile: fileList) {
					String tempFileName = tempFile.getName();
					if( tempFileName.charAt(0)!='.' )
					list.add(tempFileName );
				}
				
				for(int i = list.size()-1; i >= 0 ; i--) {
					System.out.println(list.get(i));
				}
				return;
			}
			
			if(comma) {
				ArrayList<String> list = new ArrayList<String>();
				for(File tempFile: fileList) {
					String tempFileName = tempFile.getName();
					if( tempFileName.charAt(0)!='.' ) {
						list.add(tempFileName);
						list.add(", " );
					}
				}
				for(int i=0; i < list.size()-2; i++) {
					System.out.print(list.get(i));
				}
				return;
			}
			
			if(modified) {
				HashMap<Long, String> map = new HashMap<Long, String>();
				for(File tempFile: fileList) {
					String tempFileName = tempFile.getName();
					if( tempFileName.charAt(0)!='.' ) {
						map.put(tempFile.lastModified(), tempFileName );
					}
				}
				TreeMap<Long,String> tm = new TreeMap<Long,String>(map);
				Iterator<Long> iteratorKey = tm.descendingKeySet().iterator();
				while(iteratorKey.hasNext()) {
					Long key = iteratorKey.next();
					System.out.println(tm.get(key));
				}		
				
				
				return;
			}
			
			for(File tempFile: fileList) {
				String tempFileName = tempFile.getName();
				if( tempFileName.charAt(0)!='.' )
				System.out.println(tempFileName);
			}
			
			
			}
		}

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);


			help = cmd.hasOption("h");
			all = cmd.hasOption("a");
			size = cmd.hasOption("s");
			reverse = cmd.hasOption("r");
			comma = cmd.hasOption("m");
			modified = cmd.hasOption("t");
			
		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	// Definition Stage
	private Options createOptions() {
		Options options = new Options();
		
		options.addOption(Option.builder("a").longOpt("all")
				.desc("Display all files")
				.build()
				);
		
		options.addOption(Option.builder("s").longOpt("size")
				.desc("Display files sorted by size ")
				.build()
				);
		
		options.addOption(Option.builder("r").longOpt("reverse")
				.desc("Display files in reverse order")
				.build()
				);
		
		options.addOption(Option.builder("m").longOpt("comma")
				.desc("Display files with comma")
				.build()
				);
		
		options.addOption(Option.builder("t").longOpt("modified")
				.desc("Display files based on last modified time ")
				.build()
				);
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
		        .desc("Help")
		        .build());

		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "Ls program";
		String footer ="";
		formatter.printHelp("Ls", header, options, footer, true);
	}

}
