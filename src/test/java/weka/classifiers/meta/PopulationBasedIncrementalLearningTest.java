package weka.classifiers.meta;

import java.io.FileReader;
import java.util.Date;
import java.util.Random;

import org.junit.Test;

import weka.core.Instances;

public class PopulationBasedIncrementalLearningTest {

	@Test
	public void testBuildClassifier() throws Exception {
		Instances instances = new Instances(new FileReader("Iris.arff"));
		instances.setClassIndex(instances.numAttributes() - 1);

		PopulationBasedIncrementalLearning pbil = new PopulationBasedIncrementalLearning();
		pbil.setConfiguration("test/pbil-parameters.txt");
		pbil.setTimeLimit(1);
		//pbil.setGenerations(25);

		int numFolds = 10;
		int repetitions = 2;

		double[] error = new double[repetitions];
		String[] models = new String[repetitions];
		for (int i = 0; i < repetitions; i++) {

			Instances data = new Instances(instances);
			data.randomize(new Random(i));
			data.stratify(numFolds);

			double minError = 1.0;
			for (int fold = 0; fold < numFolds; fold++) {
				System.out.println("Rep " + i + " Fold " + fold); // TODO
				Instances train = data.trainCV(numFolds, fold, new Random(i));
				Instances test = data.testCV(numFolds, fold);
				System.out.println("START TRAINING " + new Date()); // TODO
				pbil.buildClassifier(train);

				int miss = 0;
				for (int j = 0; j < test.numInstances(); j++) {
					if (pbil.classifyInstance(test.get(j)) != test.get(j).classValue()) {
						miss++;
					}
				}

				double p = miss / (double) test.numInstances();
				error[i] += p;
				if (p < minError) {
					p = error[i];
					models[i] = pbil.toString();
					models[i] += "\n\nError:          " + p;
					models[i] += "\nPerformed Time(m):" + pbil.getPerformedTime();
					models[i] += "\nPerformed Steps:  " + pbil.getPerformedGenerations();
				}
				System.out.println("\tError:            " + p); // TODO
				System.out.println("\tPerformed Time(m):" + pbil.getPerformedTime()); // TODO
				System.out.println("\tPerformed Steps  :" + pbil.getPerformedGenerations()); // TODO
				System.out.println(); // TODO
			}

			error[i] = error[i] / (double) numFolds;

			System.out.println("\tValidation Error[" + i + "]: " + error[i]); // TODO
			//System.out.println("Model " + i + ":"); // TODO
			//System.out.println(models[i]); // TODO
			//System.out.println(); // TODO
		}

		System.out.println("\n\n-----------------------"); // TODO
		for (int i = 0; i < repetitions; i++) {
			System.out.println("Model " + i + ":"); // TODO
			System.out.println(models[i]); // TODO
			System.out.println(); // TODO
		}
		System.out.println("Results:"); // TODO
		for (int i = 0; i < repetitions; i++) {
			System.out.println("\tValidation Error[" + i + "]: " + error[i]); // TODO
		}
	}
}
