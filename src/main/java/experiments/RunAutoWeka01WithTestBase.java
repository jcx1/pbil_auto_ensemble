package experiments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Date;
import java.util.Random;

import weka.classifiers.meta.AutoWEKAClassifier;
import weka.core.Instances;
import weka.core.Utils;

public class RunAutoWeka01WithTestBase {

	private static int timeLimit = 1; // in minutes
	private static int numRepetitions = 1; // default = 10
	private static String output_name = "Auto-Weka Exp1.test";
	private static int seed = 123;
	private static int memLimit = 4096;
	private static int nBestConfigs = 1;
	private static int parallelRuns = 1;

	// Bases para treinamento e teste
	private static String[] base_train = { "resources/Iris.arff" };
	private static String[] base_test = { "resources/Iris.arff" };

	public static void main(String[] args) throws FileNotFoundException {

		final File output = new File(output_name + " " + new Date().toString().replaceAll(":", "_") + ".csv");
		final PrintStream out = new PrintStream(new FileOutputStream(output, true));
		out.println("Error,Steps,Time,Base,Parameters,\"Best Solve\"");
		out.close();

		for (int base = 0; base < base_train.length; base++) {
			for (int i = 0; i < numRepetitions; i++) {
				run(output, base_train[base], base_test[base], seed, timeLimit, memLimit, nBestConfigs, parallelRuns);
			}
		}
	}

	private static void run(File output, String base_train, String base_test, int seed, int mTime, int mem, int nBest, int pRuns) throws FileNotFoundException {
		final AutoWEKAClassifier classifier = new AutoWEKAClassifier();
		classifier.setSeed(seed);
		classifier.setTimeLimit(mTime);
		classifier.setMemLimit(mem);
		classifier.setnBestConfigs(nBest);
		classifier.setParallelRuns(pRuns);

		double error = 0;
		double time = 0;
		String bestConfiguration = null;

		try {
			final Random rand = new Random(seed);
			final Instances train = new Instances(new FileReader(base_train));
			train.setClassIndex(train.numAttributes() - 1);
			train.randomize(rand);
			final Instances test = new Instances(new FileReader(base_test));
			test.setClassIndex(test.numAttributes() - 1);

			time = System.currentTimeMillis();

			classifier.buildClassifier(train);

			int miss = 0;
			for (int i = 0; i < test.numInstances(); i++) {
				if (Double.compare(test.get(i).classValue(), classifier.classifyInstance(test.get(i))) != 0) {
					miss++;
				}
			}

			error = miss / (double) test.numInstances();
			bestConfiguration = classifier.getBestConfiguration();

			time = (System.currentTimeMillis() - time) / 1000.0; // to seconds

		} catch (Exception e) {
			bestConfiguration = null;
		}

		final String configuration = Utils.arrayToString(classifier.getOptions()).replaceAll(",", " ");
		PrintStream out = new PrintStream(new FileOutputStream(output, true));
		if (bestConfiguration != null) {
			out.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"\n", error, Double.NaN, time, base_train, configuration, bestConfiguration.replaceAll("\"", "\"\""));
		} else {
			out.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"\n", Double.NaN, Double.NaN, Double.NaN, base_train, bestConfiguration, "Error");
		}
		out.close();
	}
}
