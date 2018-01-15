package mgr;

import java.io.IOException;

import java.util.Arrays;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * Canaliza las llamadas a través de la consola. Gestiona los parámetros de entra y redirige a Paser
 * El único parámetro obligatorio es -o [Nombre del fichero de salida]
 */
public class App {
	private static Logger logger;

	public static void main(String[] arguments) throws IOException {

		String pathIn = null;
		String pathOut = null;

		final OptionParser optionParser = new OptionParser();
		OptionSet options = null;

		final String[] fileInputOptions = { "i", "in" };
		optionParser.acceptsAll(Arrays.asList(fileInputOptions), "Path and name of file containig annotations.").withRequiredArg().ofType(String.class);

		final String[] fileOutputtOptions = { "o", "out" };
		optionParser.acceptsAll(Arrays.asList(fileOutputtOptions),
								"Path and name of file in which to output the annotations.").withRequiredArg().required().ofType(String.class);

		final String[] removeOptions = { "r", "remove" };
		optionParser.acceptsAll(Arrays.asList(removeOptions), "Removes duplicate annotations");

		final String[] overlappingOptions = { "l", "overlapping" };
		optionParser.acceptsAll(Arrays.asList(overlappingOptions), "Removes overlapping annotations");

		final String[] verboseOptions = { "v", "verbose" };
		optionParser.acceptsAll(Arrays.asList(verboseOptions), "Verbose logging.");

		final String[] helpOptions = { "h", "help" };
		optionParser.acceptsAll(Arrays.asList(helpOptions), "Display help/usage information").forHelp();


		try {
			options = optionParser.parse(arguments);

			if (options.has("help")) {
				optionParser.printHelpOn(System.out);

			} else {
				if (!options.has("in")) {
					System.out.println("Input file not found. Using My Clippings.txt");
					pathIn = "My Clippings.txt";
				} else {
					pathIn = (String)options.valueOf("in");
				}

				if (options.has("verbose")) {
					logger = LogManager.getLogger(App.class);
				}
				pathOut = (String)options.valueOf("out");
				Parser.execute(pathIn, pathOut, options.has("remove"), options.has("overlapping"), options.has("verbose"));
			}

		} catch (OptionException e) {
			System.out.println(e.getMessage());
			optionParser.printHelpOn(System.out);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
