package weka;

import java.io.File;
import java.io.FileReader;

import weka.classifiers.meta.pbil.Configuration;
import weka.classifiers.meta.pbil.Solve;
import weka.core.Instances;

public class SpecificTest extends Solve{

	//Fatal Error for Solve Encode: [false, false, false, true, false, true, false, true, true, false, true, false, false, true, true, true, false, false, false, false, true, true, true, false, false, true, true, false, false, false, true, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, true, false, false, false, true, false, false, false, true, true, false, false, true, false, false, true, true, false, false, false, false, false, false, true, true, true, true, true, false, false, true, true, false, true, true, true, false, true, true, false, false, false, true, true, true, true, true, true, true, false, false, false, true, false, false, true, true, false, false, true, true, false, true, true, false, false, true, false, true, false, false, true, false, false, true, false, true, true, false, true, false, true, true, false, false, false, true, false, false, false, true, true, false, true, true, false, true, true, true, false, false, false, false, true, true, false, true, false, false, true, false, false, false, true, false, true, true, false, true, true, false, false, false, true, false, true, false, true, false, true, false, true, false, false, true, true, true, false, true, false, true, false, true, true, true, false, true, true, true, false, false, false, true, false, true, true, false, true, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, true, false, true, true, true, true, true, true, false, false, false, false, true, true, true, true, true, false, false, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, false, false, true, false, true, true, false, false, true, true, false, true, true, true, true, false, true, true, true, false, true, true, false, false, false, true, false, true, false, true, true, false, true, false, false, true, false, true, false, false, false, true, true, true, true, false, false, false, true, true, false, false, false, false, false, false, true, true, false, false, false, true, true, false, true, false, false, false, true, true, true]
	//Before [false, false, false, true, false, true, false, true, true, false, true, false, false, true, true, true, false, false, false, false, true, true, true, false, false, true, true, false, false, false, true, false, true, false, false, true, false, false, false, false, true, false, false, false, true, false, false, true, false, false, false, true, false, true, true, false, true, false, false, true, false, false, false, true, true, false, false, false, true, false, false, false, false, false, false, false, true, true, true, false, false, false, true, false, false, false, true, false, false, false, true, true, false, false, true, false, false, true, true, false, false, false, false, false, false, true, true, true, true, true, false, false, true, true, false, true, true, true, false, true, true, false, false, false, true, true, true, true, true, true, true, false, false, false, true, false, false, true, true, false, false, true, true, false, true, true, false, false, true, false, true, false, false, true, false, false, true, false, true, true, false, true, false, true, true, false, false, false, true, false, false, false, true, true, false, true, true, false, true, true, true, false, false, false, false, true, true, false, true, false, false, true, false, false, false, true, false, true, true, false, true, true, false, false, false, true, false, true, false, true, false, true, false, true, false, false, true, true, true, false, true, false, true, false, true, true, true, false, true, true, true, false, false, false, true, false, true, true, false, true, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, true, false, true, true, true, true, true, true, false, false, false, false, true, true, true, true, true, false, false, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false, false, true, false, false, true, false, true, true, false, false, true, true, false, true, true, true, true, false, true, true, true, false, true, true, false, false, false, true, false, true, false, true, true, false, true, false, false, true, false, true, false, false, false, true, true, true, true, false, false, false, true, true, false, false, false, false, false, false, true, true, false, false, false, true, true, false, true, false, false, false, true, true, true]

	private static final long serialVersionUID = 1L;

	protected SpecificTest(Configuration configuration) throws Exception {
		super(configuration);
		
		//4 29 4 29
		
		//setParans("weka.classifiers.trees.J48 -O -U -B -A -M 2 -C 0.28");
		//System.out.println(">>>" + isSatisfiable()); // TODO

		
		//boolean[] error = new boolean[] {false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, false, true, true, true, true, false, false, true, true, false, false, false, false, false, true, true, false, false, false, true, false, false, false, true, false, true, false, false, false, false, false, false, true, true, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, false, true, false, true, false, true, false, false, true, false, true, false, false, false, false, true, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, true, false, false, true, true, true, true, false, false, true, false, false, false, false, false, true, false, false, true, false, false, true, false, false, false, false, true, false, false, false, false, true, false, true, true, false, true, false, true, false, true, false, false, true, true, true, false, true, true, false, false, true, false, true, false, false, true, false, true, false, true, false, false, true, false, false, false, false, false, false, false, false, true, false, true, false, false, true, false, false, true, false, false, true, true, true, true, true, false, false, false, true, false, false, false, true, false, true, true, true, false, true, true, false, false, true, false, true, false, false, true, true, false, true, true, true, true, true, false, true, true, true, false, true, false, true, true, true, false, false, true, false, true, true, false, false, false, false, false, false, true, false, false, true, false, false, false, true, false, true, false, true, false, false, false, false, false, true, false, false, false, true, true, true, true, false, false, false, false, true, true, true, false, false, false, true, true, false, true, true, false, true, true, false, false, true, true, false, false, false, false, true, true, false, true, false, true, true, true, true, true, false, false, true, false, false, false, true, true, true, true, true, true, false, false, true, true, false, false, true, true, true, true, true, true, false, true, true, false, false, false, true, true, false, false, false, false, false, false, true, true, false, true, false, false, false, true, true, false, true, true, false, true, true, false};
		//System.arraycopy(error, 0, encode, 0, error.length);
		
		//System.out.println("INPUT " + isValid()); // TODO
		
		//String modelName = getModelName();
		//Model model = configuration.ensembles.containsKey(modelName) ? configuration.ensembles.get(modelName) : configuration.classifiers.get(modelName);
		//repair(new Random(2));
		
		//System.out.println("PARTIAL " + isValid() ); // TODO
		//System.out.println(getParans()); // TODO
		//System.out.println("OUTPUT " + isValid() ); // TODO
		//System.out.println(); // TODO
		
		Instances instances = new Instances(new FileReader(new File("Iris.arff")));
		instances.setClassIndex(instances.numAttributes()-1);
		
		
		setParans("weka.classifiers.functions.SMO -C 0.53 -N 2 -M -K \"weka.classifiers.functions.supportVector.PolyKernel -E 4.11 -L\"");
		evaluate(instances, 10);
		
		System.out.println(this);
		System.out.println("END " + evaluated); // TODO
		//getClassifier();
		
		//System.out.println(isValid()); // TODO
		//System.out.println("Model Name " + getModelName()); // TODO
		//System.out.println("Model Valid " + isValid(4, model)); // TODO
		//repair(new Random(0));
		//System.out.println("-----------------------------------------"); // TODO
		//System.out.println("Model Name " + getModelName()); // TODO
		//System.out.println("Model Valid " + model + " " + isValid(4, model)); // TODO
		
		
		//System.out.println(isValid()); // TODO
		//System.out.println(getParans()); // TODO
		
		

		/*
		System.out.println("Model Name " + getModelName()); // TODO
		System.out.println("Base Classifiers " + Arrays.toString(getBaseClassifiersNames())); // TODO
		
		Model model = configuration.classifiers.get("weka.classifiers.trees.RandomTree");
		
		System.out.println(isValid(4, model)); // TODO
		
		System.out.println(getParans()); // TODO*/
	}

	public static void main(String[] args) throws Exception {
		//weka.classifiers.meta.AdaBoostM1 -P 62 -I 10 -S 1 -W weka.classifiers.rules.DecisionTable -- -E auc -I -S weka.attributeSelection.BestFirst -X 1Undefined AUC!!
		new SpecificTest(new Configuration(new File("pbil-parameters.txt")));
		/*
		Instances instances = new Instances(new FileReader(new File("Iris.arff")));
		instances.setClassIndex(instances.numAttributes()-1);
		
		Solve solve = new Solve(new Configuration(new File("pbil-parameters.txt")));
		solve.setParans("weka.classifiers.meta.Stacking -X 10 -S 1 -B \"weka.classifiers.bayes.BayesNet -D -Q weka.classifiers.bayes.net.search.local.TAN \" -B \"weka.classifiers.bayes.BayesNet -Q weka.classifiers.bayes.net.search.local.K2 \" -B \"weka.classifiers.bayes.BayesNet -Q weka.classifiers.bayes.net.search.local.LAGDHillClimber \" -B \"weka.classifiers.bayes.BayesNet -D -Q weka.classifiers.bayes.net.search.local.K2 \" -B \"weka.classifiers.bayes.BayesNet -D -Q weka.classifiers.bayes.net.search.local.TAN \" -B \"weka.classifiers.bayes.BayesNet -D -Q weka.classifiers.bayes.net.search.local.HillClimber \" -B \"weka.classifiers.bayes.BayesNet -Q weka.classifiers.bayes.net.search.local.K2 \" -B \"weka.classifiers.bayes.BayesNet -Q weka.classifiers.bayes.net.search.local.LAGDHillClimber \" -B \"weka.classifiers.rules.DecisionTable -E mae -S weka.attributeSelection.GreedyStepwise -X 1 \" -B \"weka.classifiers.trees.J48 -O -B -A -S -M 51 -C 0.33 \"");
		solve.evaluate(instances, instances);
		System.out.println("END"); // TODO*/
	}

}
