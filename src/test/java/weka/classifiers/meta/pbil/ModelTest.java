package weka.classifiers.meta.pbil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.HashMap;

import org.junit.Test;

import weka.classifiers.meta.pbil.Interval.Type;

public class ModelTest {

	@Test
	public void test001Model() throws ParseException {
		Model model;

		model = new Model("Model1");
		assertFalse(model.isEnsemble);
		assertEquals("Model1", model.name);
		assertNull(model.classifierParameter);
		assertNull(model.percentHomogeneous);
		assertNull(model.numClassifiers);
		assertNotNull(model.parameters);
		assertTrue(model.parameters.isEmpty());
		assertNotNull(model.conditionals);
		assertTrue(model.conditionals.isEmpty());

		Parameter numClassifiers = Parameter.parse("@parameter numClassifiers -D 3 integer {3}");
		Parameter percentHomogeneous = Parameter.parse("@parameter percentHomogeneous -D 0.3 double {0.3}");
		Parameter classifier = Parameter.parse("@parameter classifier -W weka.classifiers.bayes.NaiveBayes string {weka.classifiers.bayes.BayesNet, weka.classifiers.bayes.NaiveBayes, weka.classifiers.functions.MultilayerPerceptron, weka.classifiers.functions.SMO, weka.classifiers.lazy.IBk, weka.classifiers.lazy.KStar, weka.classifiers.trees.J48, weka.classifiers.rules.DecisionTable, weka.classifiers.trees.RandomTree}");
		model = new Model("Model1", numClassifiers, percentHomogeneous, classifier);
		assertTrue(model.isEnsemble);
		assertEquals("Model1", model.name);
		assertNotNull(model.classifierParameter);
		assertTrue(model.classifierParameter.interval.type == Type.String);
		assertNotNull(model.percentHomogeneous);
		assertTrue(model.percentHomogeneous.interval.type == Type.Double);
		assertNotNull(model.numClassifiers);
		assertTrue(model.numClassifiers.interval.type == Type.Integer);
		assertNotNull(model.parameters);
		assertTrue(model.parameters.isEmpty());
		assertNotNull(model.conditionals);
		assertTrue(model.conditionals.isEmpty());
	}

	@Test
	public void test002Parse() throws ParseException, IOException {
		String text;
		Model model;
		BufferedReader reader;
		HashMap<String, Model> classifiers = new HashMap<>();

		text = "@classifier weka.classifiers.bayes.BayesNet" + "\n@parameter useADTree -D false boolean" + "\n@parameter searchAlgorithm -Q weka.classifiers.bayes.net.search.local.K2 string {weka.classifiers.bayes.net.search.local.K2, weka.classifiers.bayes.net.search.local.HillClimber, weka.classifiers.bayes.net.search.local.LAGDHillClimber, weka.classifiers.bayes.net.search.local.SimulatedAnnealing, weka.classifiers.bayes.net.search.local.TabuSearch, weka.classifiers.bayes.net.search.local.TAN}" + "\n@end";
		reader = new BufferedReader(new StringReader(text));
		model = Model.parse(reader, classifiers);
		assertFalse(model.isEnsemble);
		assertEquals("weka.classifiers.bayes.BayesNet", model.name);
		assertNull(model.classifierParameter);
		assertNull(model.percentHomogeneous);
		assertNull(model.numClassifiers);
		assertNotNull(model.parameters);
		assertEquals(2, model.parameters.size());
		assertNotNull(model.conditionals);
		assertTrue(model.conditionals.isEmpty());

		text = "\t@classifier weka.classifiers.bayes.BayesNet" + "\n\t@parameter useADTree -D false boolean" + "\n@parameter searchAlgorithm -Q weka.classifiers.bayes.net.search.local.K2 string {weka.classifiers.bayes.net.search.local.K2, weka.classifiers.bayes.net.search.local.HillClimber, weka.classifiers.bayes.net.search.local.LAGDHillClimber, weka.classifiers.bayes.net.search.local.SimulatedAnnealing, weka.classifiers.bayes.net.search.local.TabuSearch, weka.classifiers.bayes.net.search.local.TAN}" + "\n@end";
		reader = new BufferedReader(new StringReader(text));
		model = Model.parse(reader, classifiers);
		assertFalse(model.isEnsemble);
		assertEquals("weka.classifiers.bayes.BayesNet", model.name);
		assertNull(model.classifierParameter);
		assertNull(model.percentHomogeneous);
		assertNull(model.numClassifiers);
		assertNotNull(model.parameters);
		assertEquals(2, model.parameters.size());
		assertNotNull(model.conditionals);
		assertTrue(model.conditionals.isEmpty());
		classifiers.put("weka.classifiers.bayes.BayesNet", model);

		text = "@classifier weka.classifiers.trees.J48\n" + "	@parameter unpruned -U false boolean\n" + "	@parameter subtreeRaising -S false boolean\n" + "	@parameter confidenceFactor -C 0.25 double [0,1]\n" + "	@conditional -U == false and -S == true\n" + "	@conditional -U == false and -C == 0\n" + "@end";
		reader = new BufferedReader(new StringReader(text));
		model = Model.parse(reader, classifiers);
		assertFalse(model.isEnsemble);
		assertEquals("weka.classifiers.trees.J48", model.name);
		assertNull(model.classifierParameter);
		assertNull(model.percentHomogeneous);
		assertNull(model.numClassifiers);
		assertNotNull(model.parameters);
		assertEquals(3, model.parameters.size());
		assertNotNull(model.conditionals);
		assertEquals(2, model.conditionals.size());
		classifiers.put("weka.classifiers.trees.J48", model);

		text = "@classifier weka.classifiers.functions.SMO\n" + "	@parameter c -C 1.0 double [0.5,1.5]\n" + "	@parameter filterType -N 0 integer {0,1,2}\n" + "		@parameter buildCalibrationModels -M false boolean\n" + "@parameter kernel -K weka.classifiers.functions.supportVector.NormalizedPolyKernel string {weka.classifiers.functions.supportVector.NormalizedPolyKernel, weka.classifiers.functions.supportVector.PolyKernel, weka.classifiers.functions.supportVector.Puk, weka.classifiers.functions.supportVector.RBFKernel}\n" + "	@sub-parameter -K weka.classifiers.functions.supportVector.NormalizedPolyKernel\n" + "		@parameter exponent -E 1 double [0.2,5]\n" + "		@parameter useLowerOrder -L false boolean\n" + "	@end\n" + "@end";
		reader = new BufferedReader(new StringReader(text));
		model = Model.parse(reader, classifiers);
		assertFalse(model.isEnsemble);
		assertEquals("weka.classifiers.functions.SMO", model.name);
		assertNull(model.classifierParameter);
		assertNull(model.percentHomogeneous);
		assertNull(model.numClassifiers);
		assertNotNull(model.parameters);
		assertEquals(4, model.parameters.size());
		assertNotNull(model.conditionals);
		assertTrue(model.conditionals.isEmpty());
		assertNotNull(model.parameters.get("-K"));
		assertEquals(1, model.parameters.get("-K").subParameters.size());
		assertNotNull(model.parameters.get("-K").subParameters.get("weka.classifiers.functions.supportVector.NormalizedPolyKernel"));
		assertEquals(2, model.parameters.get("-K").subParameters.get("weka.classifiers.functions.supportVector.NormalizedPolyKernel").parameters.size());
		classifiers.put("weka.classifiers.functions.SMO", model);

		text = "@ensemble weka.classifiers.trees.RandomForest\n" + "@parameter numIterations -I 10 integer [2,128]\n" + "@parameter numFeatures -K 2 integer [0,32]\n" + "@parameter maxDepth -depth 0 integer [0,20]\n" + "@end";
		reader = new BufferedReader(new StringReader(text));
		model = Model.parse(reader, classifiers);
		assertTrue(model.isEnsemble);
		assertEquals("weka.classifiers.trees.RandomForest", model.name);
		assertNull(model.classifierParameter);
		assertNull(model.percentHomogeneous);
		assertNull(model.numClassifiers);
		assertNotNull(model.parameters);
		assertEquals(3, model.parameters.size());
		assertTrue(model.parameters.containsKey("-I"));
		assertTrue(model.parameters.containsKey("-K"));
		assertTrue(model.parameters.containsKey("-depth"));
		assertNotNull(model.conditionals);
		assertTrue(model.conditionals.isEmpty());

		text = "	@ensemble weka.classifiers.meta.Vote\n" + "	@parameter-num-classifiers 2 [2,10]\n" + "	@parameter-percent-homogeneous 0 {0.6}\n" + "@parameter classifier -B weka.classifiers.bayes.BayesNet classifier {weka.classifiers.bayes.BayesNet, weka.classifiers.functions.SMO, weka.classifiers.trees.J48}\n" + "@end";
		reader = new BufferedReader(new StringReader(text));
		model = Model.parse(reader, classifiers);
		assertTrue(model.isEnsemble);
		assertEquals("weka.classifiers.meta.Vote", model.name);
		assertNotNull(model.classifierParameter);
		assertEquals(3, model.classifierParameter.interval.size());
		assertTrue(model.classifierParameter.interval.contains("weka.classifiers.bayes.BayesNet"));
		assertTrue(model.classifierParameter.subParameters.containsKey("weka.classifiers.bayes.BayesNet"));
		assertEquals(2, model.classifierParameter.subParameters.get("weka.classifiers.bayes.BayesNet").parameters.size());
		assertTrue(model.classifierParameter.interval.contains("weka.classifiers.trees.J48"));
		assertTrue(model.classifierParameter.subParameters.containsKey("weka.classifiers.trees.J48"));
		assertEquals(3, model.classifierParameter.subParameters.get("weka.classifiers.trees.J48").parameters.size());
		assertEquals(2, model.classifierParameter.subParameters.get("weka.classifiers.trees.J48").conditionals.size());
		assertTrue(model.classifierParameter.interval.contains("weka.classifiers.functions.SMO"));

		try {
			text = "classifier weka.classifiers.bayes.BayesNet" + "\n@parameter useADTree -D false boolean" + "\n@parameter searchAlgorithm -Q weka.classifiers.bayes.net.search.local.K2 string {weka.classifiers.bayes.net.search.local.K2, weka.classifiers.bayes.net.search.local.HillClimber, weka.classifiers.bayes.net.search.local.LAGDHillClimber, weka.classifiers.bayes.net.search.local.SimulatedAnnealing, weka.classifiers.bayes.net.search.local.TabuSearch, weka.classifiers.bayes.net.search.local.TAN}" + "\n@end";
			reader = new BufferedReader(new StringReader(text));
			model = Model.parse(reader, classifiers);
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			text = "@classifier weka.classifiers.bayes.BayesNet" + "\n@parameter useADTree -D false boolean" + "\n@parameter searchAlgorithm -Q weka.classifiers.bayes.net.search.local.K2 string {weka.classifiers.bayes.net.search.local.K2, weka.classifiers.bayes.net.search.local.HillClimber, weka.classifiers.bayes.net.search.local.LAGDHillClimber, weka.classifiers.bayes.net.search.local.SimulatedAnnealing, weka.classifiers.bayes.net.search.local.TabuSearch, weka.classifiers.bayes.net.search.local.TAN}";
			reader = new BufferedReader(new StringReader(text));
			model = Model.parse(reader, classifiers);
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			text = "@classifier weka.classifiers.bayes.BayesNet bayes" + "\n@parameter useADTree -D false boolean" + "\n@parameter searchAlgorithm -Q weka.classifiers.bayes.net.search.local.K2 string {weka.classifiers.bayes.net.search.local.K2, weka.classifiers.bayes.net.search.local.HillClimber, weka.classifiers.bayes.net.search.local.LAGDHillClimber, weka.classifiers.bayes.net.search.local.SimulatedAnnealing, weka.classifiers.bayes.net.search.local.TabuSearch, weka.classifiers.bayes.net.search.local.TAN}" + "\n@end";
			reader = new BufferedReader(new StringReader(text));
			model = Model.parse(reader, classifiers);
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			text = "@classifier -K weka.classifiers.bayes.BayesNet" + "\n@parameter useADTree -D false boolean" + "\n@parameter searchAlgorithm -Q weka.classifiers.bayes.net.search.local.K2 string {weka.classifiers.bayes.net.search.local.K2, weka.classifiers.bayes.net.search.local.HillClimber, weka.classifiers.bayes.net.search.local.LAGDHillClimber, weka.classifiers.bayes.net.search.local.SimulatedAnnealing, weka.classifiers.bayes.net.search.local.TabuSearch, weka.classifiers.bayes.net.search.local.TAN}" + "\n@end";
			reader = new BufferedReader(new StringReader(text));
			model = Model.parse(reader, classifiers);
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			text = "	ensemble weka.classifiers.meta.Vote\n" + "	@parameter-num-classifiers 2 [2,10]\n" + "	@parameter-percent-homogeneous 0 {0.6}\n" + "@parameter classifier -B weka.classifiers.bayes.BayesNet classifier {weka.classifiers.bayes.BayesNet, weka.classifiers.functions.SMO, weka.classifiers.trees.J48}\n" + "@end";
			reader = new BufferedReader(new StringReader(text));
			model = Model.parse(reader, classifiers);
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			text = "	@ensemble weka.classifiers.meta.Vote\n" + "	@parameter-num-classifiers 2 [2,10]\n" + "	@parameter-percent-homogeneous 0 {0.6}\n" + "@parameter classifier -B weka.classifiers.bayes.BayesNet classifier {weka.classifiers.bayes.BayesNet, weka.classifiers.functions.SMO, weka.classifiers.trees.J48}\n";
			reader = new BufferedReader(new StringReader(text));
			model = Model.parse(reader, classifiers);
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			text = "	@ensemble -K weka.classifiers.meta.Vote\n" + "	@parameter-num-classifiers 2 [2,10]\n" + "	@parameter-percent-homogeneous 0 {0.6}\n" + "@parameter classifier -B weka.classifiers.bayes.BayesNet classifier {weka.classifiers.bayes.BayesNet, weka.classifiers.functions.SMO, weka.classifiers.trees.J48}\n" + "@end";
			reader = new BufferedReader(new StringReader(text));
			model = Model.parse(reader, classifiers);
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			text = "	@ensemble weka.classifiers.meta.Vote vote\n" + "	@parameter-num-classifiers 2 [2,10]\n" + "	@parameter-percent-homogeneous 0 {0.6}\n" + "@parameter classifier -B weka.classifiers.bayes.BayesNet classifier {weka.classifiers.bayes.BayesNet, weka.classifiers.functions.SMO, weka.classifiers.trees.J48}\n" + "@end";
			reader = new BufferedReader(new StringReader(text));
			model = Model.parse(reader, classifiers);
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			text = "	@ensemble weka.classifiers.meta.Vote\n" + "	@parameter-num-classifiers 2 [2,10]\n" + "	@parameter-percent-homogeneous 0 {0.6}\n" + "@parameter classifier -X weka.classifiers.bayes.BayesNet classifier {weka.classifiers.bayes.BayesNet, weka.classifiers.functions.SMO, weka.classifiers.trees.J48}\n" + "@end";
			reader = new BufferedReader(new StringReader(text));
			model = Model.parse(reader, classifiers);
			fail("Expected a exception.");
		} catch (ParseException e) {}
	}

	@Test
	public void test003IsValid() throws ParseException, IOException {

		String text;
		Model model;
		BufferedReader reader;
		HashMap<String, Model> classifiers = new HashMap<>();

		text = "@classifier weka.classifiers.lazy.IBk\n" + "@parameter k -K 1 integer [1,64]\n" + "@parameter meanSquared -E false boolean\n" + "@parameter crossValidate -X false boolean\n" + "@parameter distanceWeightingOneMinus -F false boolean\n" + "@parameter distanceWeightingOneOver -I false boolean\n" + "@conditional -F == true and -I == true\n" + "@end";

		reader = new BufferedReader(new StringReader(text));
		model = Model.parse(reader, classifiers);

		assertTrue(model.isValid("weka.classifiers.lazy.IBk -K 1 -W 0 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -R first-last\\\"\""));
		assertFalse(model.isValid("weka.classifiers.lazy.IBk -K 1 -F -I -W 0 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -R first-last\\\"\""));

		text = "	@classifier weka.classifiers.trees.J48\n" + "	@parameter unpruned -U false boolean\n" + "	@parameter subtreeRaising -S false boolean\n" + "	@parameter confidenceFactor -C 0.25 double [0,1]\n" + "	@conditional -U == false and -S == true\n" + "	@conditional -U == false and -C == 0\n" + "@end\n";

		reader = new BufferedReader(new StringReader(text));
		model = Model.parse(reader, classifiers);
		assertTrue(model.isValid("weka.classifiers.trees.J48"));
		assertTrue(model.isValid("weka.classifiers.trees.J48 -U -S"));
		assertTrue(model.isValid("weka.classifiers.trees.J48 -U -C 0"));
		assertTrue(model.isValid("weka.classifiers.trees.J48 -U -C 0 -S"));
		assertFalse(model.isValid("weka.classifiers.trees.J48 -S"));
		assertFalse(model.isValid("weka.classifiers.trees.J48 -C 0"));
	}
}
