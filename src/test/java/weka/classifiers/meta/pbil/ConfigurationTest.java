package weka.classifiers.meta.pbil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void testConfiguration() throws IOException, ParseException {
		Configuration configuration = new Configuration(new File("test/pbil-parameters.txt"));

		assertNotNull(configuration.ensembles);
		assertEquals(6, configuration.ensembles.size());
		assertNotNull(configuration.classifiers);
		assertEquals(9, configuration.classifiers.size());

		Model model;

		assertTrue(configuration.classifiers.containsKey("weka.classifiers.bayes.BayesNet"));
		assertFalse(configuration.ensembles.containsKey("weka.classifiers.bayes.BayesNet"));
		model = configuration.classifiers.get("weka.classifiers.bayes.BayesNet");
		assertFalse(model.isEnsemble);
		assertEquals(2, model.parameters.size());

		assertTrue(configuration.classifiers.containsKey("weka.classifiers.bayes.NaiveBayes"));
		assertFalse(configuration.ensembles.containsKey("weka.classifiers.bayes.NaiveBayes"));
		model = configuration.classifiers.get("weka.classifiers.bayes.NaiveBayes");
		assertFalse(model.isEnsemble);
		assertEquals(2, model.parameters.size());
		assertFalse(model.isValid("weka.classifiers.bayes.NaiveBayes -D -K"));

		assertTrue(configuration.classifiers.containsKey("weka.classifiers.functions.MultilayerPerceptron"));
		assertFalse(configuration.ensembles.containsKey("weka.classifiers.functions.MultilayerPerceptron"));
		model = configuration.classifiers.get("weka.classifiers.functions.MultilayerPerceptron");
		assertFalse(model.isEnsemble);
		assertEquals(8, model.parameters.size());

		assertTrue(configuration.classifiers.containsKey("weka.classifiers.functions.SMO"));
		assertFalse(configuration.ensembles.containsKey("weka.classifiers.functions.SMO"));
		model = configuration.classifiers.get("weka.classifiers.functions.SMO");
		assertFalse(model.isEnsemble);
		assertEquals(4, model.parameters.size());
		assertNotNull(model.parameters.get("-K").subParameters.get("weka.classifiers.functions.supportVector.Puk"));
		assertEquals(2, model.parameters.get("-K").subParameters.get("weka.classifiers.functions.supportVector.Puk").parameters.size());

		assertTrue(configuration.classifiers.containsKey("weka.classifiers.lazy.IBk"));
		assertFalse(configuration.ensembles.containsKey("weka.classifiers.lazy.IBk"));
		model = configuration.classifiers.get("weka.classifiers.lazy.IBk");
		assertFalse(model.isEnsemble);
		assertEquals(5, model.parameters.size());
		assertFalse(model.isValid("weka.classifiers.lazy.IBk -F -I"));

		assertTrue(configuration.classifiers.containsKey("weka.classifiers.lazy.KStar"));
		assertFalse(configuration.ensembles.containsKey("weka.classifiers.lazy.KStar"));
		model = configuration.classifiers.get("weka.classifiers.lazy.KStar");
		assertFalse(model.isEnsemble);
		assertEquals(3, model.parameters.size());

		assertTrue(configuration.classifiers.containsKey("weka.classifiers.trees.J48"));
		assertFalse(configuration.ensembles.containsKey("weka.classifiers.trees.J48"));
		model = configuration.classifiers.get("weka.classifiers.trees.J48");
		assertFalse(model.isEnsemble);
		assertEquals(8, model.parameters.size());
		assertFalse(model.isValid("weka.classifiers.trees.J48 -U -S"));
		assertTrue(model.isValid("weka.classifiers.trees.J48 -S"));
		assertTrue(model.isValid("weka.classifiers.trees.J48 -C 0"));
		assertFalse(model.isValid("weka.classifiers.trees.J48 -U -C 0.01"));

		assertTrue(configuration.classifiers.containsKey("weka.classifiers.rules.DecisionTable"));
		assertFalse(configuration.ensembles.containsKey("weka.classifiers.rules.DecisionTable"));
		model = configuration.classifiers.get("weka.classifiers.rules.DecisionTable");
		assertFalse(model.isEnsemble);
		assertEquals(4, model.parameters.size());

		assertTrue(configuration.classifiers.containsKey("weka.classifiers.trees.RandomTree"));
		assertFalse(configuration.ensembles.containsKey("weka.classifiers.trees.RandomTree"));
		model = configuration.classifiers.get("weka.classifiers.trees.RandomTree");
		assertFalse(model.isEnsemble);
		assertEquals(5, model.parameters.size());
		assertFalse(model.isValid("weka.classifiers.trees.RandomTree -K 1"));
		assertFalse(model.isValid("weka.classifiers.trees.RandomTree -depth 1"));
		assertFalse(model.isValid("weka.classifiers.trees.RandomTree -N 1"));
		assertFalse(model.isValid("weka.classifiers.trees.RandomTree -K 0 -N 1"));
		assertFalse(model.isValid("weka.classifiers.trees.RandomTree -depth 0 -K 0 -N 1"));
		assertTrue(model.isValid("weka.classifiers.trees.RandomTree -depth 0 -K 0 -N 0"));

		assertFalse(configuration.classifiers.containsKey("weka.classifiers.trees.RandomForest"));
		assertTrue(configuration.ensembles.containsKey("weka.classifiers.trees.RandomForest"));
		model = configuration.ensembles.get("weka.classifiers.trees.RandomForest");
		assertTrue(model.isEnsemble);
		assertEquals(3, model.parameters.size());
		assertNull(model.classifierParameter);

		assertFalse(configuration.classifiers.containsKey("weka.classifiers.meta.AdaBoostM1"));
		assertTrue(configuration.ensembles.containsKey("weka.classifiers.meta.AdaBoostM1"));
		model = configuration.ensembles.get("weka.classifiers.meta.AdaBoostM1");
		assertTrue(model.isEnsemble);
		assertEquals(4, model.parameters.size());
		assertFalse(model.isValid("weka.classifiers.meta.AdaBoostM1 -Q -P 99"));
		assertNotNull(model.numClassifiers);
		assertNotNull(model.classifierParameter);
		for (String base : configuration.classifiers.keySet())
			assertTrue(model.classifierParameter.interval.contains(base));

		assertFalse(configuration.classifiers.containsKey("weka.classifiers.meta.Bagging"));
		assertTrue(configuration.ensembles.containsKey("weka.classifiers.meta.Bagging"));
		model = configuration.ensembles.get("weka.classifiers.meta.Bagging");
		assertTrue(model.isEnsemble);
		assertEquals(4, model.parameters.size());
		assertFalse(model.isValid("weka.classifiers.meta.AdaBoostM1 -O -P 45"));
		assertNotNull(model.numClassifiers);
		assertNotNull(model.classifierParameter);
		for (String base : configuration.classifiers.keySet())
			assertTrue(model.classifierParameter.interval.contains(base));

		assertFalse(configuration.classifiers.containsKey("weka.classifiers.meta.RandomCommittee"));
		assertTrue(configuration.ensembles.containsKey("weka.classifiers.meta.RandomCommittee"));
		model = configuration.ensembles.get("weka.classifiers.meta.RandomCommittee");
		assertTrue(model.isEnsemble);
		assertEquals(2, model.parameters.size());
		assertNotNull(model.numClassifiers);
		assertNotNull(model.classifierParameter);
		for (String base : configuration.classifiers.keySet()) {
			if ("weka.classifiers.trees.RandomTree".equals(base) || "weka.classifiers.functions.MultilayerPerceptron".equals(base))
				assertTrue(model.classifierParameter.interval.contains(base));
			else
				assertFalse(model.classifierParameter.interval.contains(base));
		}

		assertFalse(configuration.classifiers.containsKey("weka.classifiers.meta.Stacking"));
		assertTrue(configuration.ensembles.containsKey("weka.classifiers.meta.Stacking"));
		model = configuration.ensembles.get("weka.classifiers.meta.Stacking");
		assertTrue(model.isEnsemble);
		assertEquals(2, model.parameters.size());
		assertNotNull(model.numClassifiers);
		assertNotNull(model.percentHomogeneous);
		assertNotNull(model.classifierParameter);
		for (String base : configuration.classifiers.keySet())
			assertTrue(model.classifierParameter.interval.contains(base));

		assertFalse(configuration.classifiers.containsKey("weka.classifiers.meta.Vote"));
		assertTrue(configuration.ensembles.containsKey("weka.classifiers.meta.Vote"));
		model = configuration.ensembles.get("weka.classifiers.meta.Vote");
		assertTrue(model.isEnsemble);
		assertEquals(2, model.parameters.size());
		assertNotNull(model.numClassifiers);
		assertNotNull(model.percentHomogeneous);
		assertNotNull(model.classifierParameter);
		for (String base : configuration.classifiers.keySet())
			assertTrue(model.classifierParameter.interval.contains(base));
	}

}
