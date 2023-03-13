package weka.classifiers.meta.pbil;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.KStar;
import weka.classifiers.meta.Vote;
import weka.core.Instances;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SolveTest {

	@Test
	public void test000GetNumBitsInt() throws ParseException {
		assertEquals(0, Solve.getNumBits(0)); // 0
		assertEquals(1, Solve.getNumBits(1)); // 1
		assertEquals(2, Solve.getNumBits(2)); // 10
		assertEquals(2, Solve.getNumBits(3)); // 11
		assertEquals(3, Solve.getNumBits(4)); // 100
		assertEquals(3, Solve.getNumBits(5)); // 101
		assertEquals(3, Solve.getNumBits(6)); // 110
		assertEquals(3, Solve.getNumBits(7)); // 111
		assertEquals(4, Solve.getNumBits(8)); // 1000
		assertEquals(4, Solve.getNumBits(9));
		assertEquals(5, Solve.getNumBits(16)); // 10000
		assertEquals(5, Solve.getNumBits(17));
		assertEquals(6, Solve.getNumBits(32)); // 100000
		assertEquals(6, Solve.getNumBits(33));
	}

	@Test
	public void test001GetNumBitsParameterInt() throws ParseException {
		Parameter parameter;

		parameter = Parameter.parse("@parameter useADTree -D false boolean {false}");
		assertEquals(0, Solve.getNumBits(parameter));

		parameter = Parameter.parse("@parameter useADTree -D false boolean");
		assertEquals(1, Solve.getNumBits(parameter));

		parameter = Parameter.parse("@parameter useADTree -D 1 integer {1, 2, 3}");
		assertEquals(2, Solve.getNumBits(parameter));

		parameter = Parameter.parse("@parameter useADTree -D 1 integer {1, 2, 3, 4}");
		assertEquals(2, Solve.getNumBits(parameter));

		parameter = Parameter.parse("@parameter useADTree -D 1 integer {1, 2, 3, 4, 5}");
		assertEquals(3, Solve.getNumBits(parameter));

		parameter = Parameter.parse("@parameter useADTree -D 1 integer [1, 5]");
		assertEquals(3, Solve.getNumBits(parameter));

		parameter = Parameter.parse("@parameter useADTree -D 1 double [1.0, 5.0, 1]");
		assertEquals(6, Solve.getNumBits(parameter));
	}

	@Test
	public void test002GetNumBitsModel() throws ParseException {
		Model model;
		Parameter parameter;

		parameter = Parameter.parse("@parameter pString -S v1 string {v1, v2, v3, v4}");
		model = new Model("model1");
		model.parameters.put(parameter.key, parameter);
		assertEquals(2, Solve.getNumBits(parameter));
		assertEquals(2, Solve.getNumBits(model));

		parameter = Parameter.parse("@parameter pString -X 0 integer [0,10]");
		model.parameters.put(parameter.key, parameter);
		assertEquals(4, Solve.getNumBits(parameter));
		assertEquals(6, Solve.getNumBits(model));

		Model sub = new Model("v1");
		sub.parameters.put("-S", Parameter.parse("@parameter pString -S v1 string {v1, v2, v3, v4}"));
		sub.parameters.put("-X", Parameter.parse("@parameter pString -X 0 integer [0,10]"));

		parameter = model.parameters.get("-S");
		parameter.subParameters.put("v1", sub);
		assertEquals(8, Solve.getNumBits(parameter));
		assertEquals(12, Solve.getNumBits(model));
	}

	private File getFile1() throws IOException, ParseException {
		File tempFile = File.createTempFile("tmp", ".txt");
		tempFile.deleteOnExit();
		PrintStream out = new PrintStream(tempFile);
		out.println("@classifier weka.classifiers.lazy.KStar");
		out.println("@parameter globalBlend -B 20 integer [1,100]"); // 7 bits
		out.println("@parameter entropicAutoBlend -E false boolean"); // 1 bit
		out.println("@parameter missingMode -M a string {a,d,m,n}"); // 2 bits
		out.println("@end");
		out.close();
		// 0 bit models

		Configuration configuration = new Configuration(tempFile);
		assertEquals(7, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-B")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-E")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-M")));
		assertEquals(10, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar")));

		return tempFile;
	}

	private File getFile2() throws IOException, ParseException {
		File tempFile = File.createTempFile("tmp", ".txt");
		tempFile.deleteOnExit();
		PrintStream out = new PrintStream(tempFile);
		out.println("@classifier weka.classifiers.lazy.KStar");
		out.println("@parameter globalBlend -B 20 integer [1,100]"); // 7 bits
		out.println("@parameter entropicAutoBlend -E false boolean"); // 1 bit
		out.println("@parameter missingMode -M a string {a,d,m,n}"); // 2 bits
		out.println("@end");
		out.println("@classifier weka.classifiers.rules.DecisionTable");
		out.println("@parameter evaluationMeasure -E acc string {acc, rmse, mae, auc}"); // 2
		out.println("@parameter useIbk -I false boolean"); // 1
		out.println("@parameter search -S weka.attributeSelection.BestFirst string {weka.attributeSelection.BestFirst,weka.attributeSelection.GreedyStepwise}"); // 1
		out.println("@parameter crossVal -X 1 integer {1,2,3,4}"); // 2
		out.println("@end");
		out.close();
		// 1 bit models

		Configuration configuration = new Configuration(tempFile);
		assertEquals(7, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-B")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-E")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-M")));
		assertEquals(10, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar")));

		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-E")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-I")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-S")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-X")));
		assertEquals(6, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable")));
		return tempFile;
	}

	private File getFile1Base(int maxBaseClassifiers) throws IOException, ParseException {
		File tempFile = File.createTempFile("tmp", ".txt");
		tempFile.deleteOnExit();
		PrintStream out = new PrintStream(tempFile);
		out.println("@classifier weka.classifiers.lazy.KStar");
		out.println("@parameter globalBlend -B 20 integer [1,100]"); // 7 bits
		out.println("@parameter entropicAutoBlend -E false boolean"); // 1 bit
		out.println("@parameter missingMode -M a string {a,d,m,n}"); // 2 bits
		out.println("@end");
		out.println("@ensemble weka.classifiers.meta.Vote");
		out.println("@parameter combinationRule -R AVG string {AVG, PROD, MAJ, MIN, MAX, MED}"); // 3 bits
		out.println("@parameter-num-classifiers 2 [1," + maxBaseClassifiers + "]"); // ? bits
		out.println("@parameter classifier -B weka.classifiers.lazy.KStar classifier {weka.classifiers.lazy.KStar}"); // 1
																														// bit
		out.println("@end");
		out.close();
		// 1 bit models

		Configuration configuration = new Configuration(tempFile);
		assertEquals(7, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-B")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-E")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-M")));
		assertEquals(3, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R")));
		assertEquals(10, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.Vote").classifierParameter));
		assertEquals(10, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar")));
		assertEquals(3, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.Vote")));

		return tempFile;
	}

	private File getFile2Base(int maxBaseClassifiers) throws IOException, ParseException {
		File tempFile = File.createTempFile("tmp", ".txt");
		tempFile.deleteOnExit();
		PrintStream out = new PrintStream(tempFile);
		out.println("@classifier weka.classifiers.lazy.KStar");
		out.println("@parameter globalBlend -B 20 integer [1,100]"); // 7 bits
		out.println("@parameter entropicAutoBlend -E false boolean"); // 1 bit
		out.println("@parameter missingMode -M a string {a,d,m,n}"); // 2 bits
		out.println("@end");
		out.println("@classifier weka.classifiers.rules.DecisionTable");
		out.println("@parameter evaluationMeasure -E acc string {acc, rmse, mae, auc}"); // 2
		out.println("@parameter useIbk -I false boolean"); // 1
		out.println("@parameter search -S weka.attributeSelection.BestFirst string {weka.attributeSelection.BestFirst,weka.attributeSelection.GreedyStepwise}"); // 1
		out.println("@parameter crossVal -X 1 integer {1,2,3,4}"); // 2
		out.println("@end");
		out.println("@ensemble weka.classifiers.meta.Vote");
		out.println("@parameter combinationRule -R AVG string {AVG, PROD, MAJ, MIN, MAX, MED}"); // 3 bits
		out.println("@parameter-num-classifiers 2 [1," + maxBaseClassifiers + "]"); // ? bits
		out.println("@parameter classifier -B weka.classifiers.lazy.KStar classifier {weka.classifiers.lazy.KStar}"); // 1
																														// bit
		out.println("@end");
		out.println("@ensemble weka.classifiers.trees.RandomForest");
		out.println("@parameter numIterations -I 10 integer [2,128]"); // 7 bits
		out.println("@parameter numFeatures -K 2 integer [0,32]"); // 6 bits
		out.println("@parameter maxDepth -depth 0 integer [0,20]"); // 5 bit
		out.println("@end");
		out.close();
		// 2 bit models

		Configuration configuration = new Configuration(tempFile);
		assertEquals(7, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-B")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-E")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-M")));
		assertEquals(3, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R")));
		assertEquals(10, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.Vote").classifierParameter));
		assertEquals(10, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-E")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-I")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-S")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-X")));
		assertEquals(6, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable")));
		assertEquals(3, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.Vote")));
		assertEquals(18, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.trees.RandomForest")));

		return tempFile;
	}

	private File getFile3Base(int minBaseClassifiers, int maxBaseClassifiers) throws IOException, ParseException {
		File tempFile = File.createTempFile("tmp", ".txt");
		tempFile.deleteOnExit();
		PrintStream out = new PrintStream(tempFile);
		out.println("@classifier weka.classifiers.lazy.KStar");
		out.println("@parameter globalBlend -B 20 integer [1,100]"); // 7 bits
		out.println("@parameter entropicAutoBlend -E false boolean"); // 1 bit
		out.println("@parameter missingMode -M a string {a,d,m,n}"); // 2 bits
		out.println("@end");
		out.println("@classifier weka.classifiers.rules.DecisionTable");
		out.println("@parameter evaluationMeasure -E acc string {acc, rmse, mae, auc}"); // 2
		out.println("@parameter useIbk -I false boolean"); // 1
		out.println("@parameter search -S weka.attributeSelection.BestFirst string {weka.attributeSelection.BestFirst,weka.attributeSelection.GreedyStepwise}"); // 1
		out.println("@parameter crossVal -X 1 integer {1,2,3}"); // 2
		out.println("@end");
		out.println("@classifier weka.classifiers.functions.SMO");
		out.println("@parameter kernel -K weka.classifiers.functions.supportVector.NormalizedPolyKernel string {weka.classifiers.functions.supportVector.NormalizedPolyKernel, weka.classifiers.functions.supportVector.PolyKernel, weka.classifiers.functions.supportVector.Puk, weka.classifiers.functions.supportVector.RBFKernel}"); // 2
		out.println("@sub-parameter -K weka.classifiers.functions.supportVector.NormalizedPolyKernel");
		out.println("@parameter exponent -E 1 double [0.2,5,1]"); // 6 bits
		out.println("@parameter useLowerOrder -L false boolean"); // 1 bit
		out.println("@end");
		out.println("@sub-parameter -K weka.classifiers.functions.supportVector.PolyKernel");
		out.println("@parameter exponent -E 1 double [0.2,5,1]"); // 6 bits
		out.println("@parameter useLowerOrder -L false boolean"); // 1 bits
		out.println("@conditional -L == true and -E == 5.0");
		out.println("@end");
		out.println("@sub-parameter -K weka.classifiers.functions.supportVector.Puk");
		out.println("@parameter sigma -S 1 double [0.1,10,1]"); // 7 bits
		out.println("@parameter omega -O 1 double [0.1,1,1]"); // 4 bits
		out.println("@end");
		out.println("@sub-parameter -K weka.classifiers.functions.supportVector.RBFKernel");
		out.println("@parameter gamma -G 0.01 double [0.0001,1,1]"); // 4 bits
		out.println("@end");
		out.println("@conditional -K == weka.classifiers.functions.supportVector.Puk");
		out.println("@end");
		out.println("@ensemble weka.classifiers.meta.Vote");
		out.println("@parameter seed -S 1 integer {1}"); // 0 bits
		out.println("@parameter combinationRule -R AVG string {AVG, PROD, MAJ, MIN, MAX, MED}"); // 3 bits
		out.println("@parameter-num-classifiers 2 [" + minBaseClassifiers + "," + maxBaseClassifiers + "]"); // ? bits
		// 2 bits
		out.println("@parameter classifier -B weka.classifiers.lazy.KStar classifier {weka.classifiers.lazy.KStar, weka.classifiers.rules.DecisionTable, weka.classifiers.functions.SMO}");
		out.println("@parameter-percent-homogeneous 0 {0.5}");
		out.println("@end");
		out.println("@ensemble weka.classifiers.trees.RandomForest");
		out.println("@parameter numIterations -I 10 integer [2,128]"); // 7 bits
		out.println("@parameter numFeatures -K 2 integer [0,32]"); // 6 bits
		out.println("@parameter maxDepth -depth 0 integer [0,20]"); // 5 bit
		out.println("@end");
		out.close();
		// 3 bit models

		Configuration configuration = new Configuration(tempFile);
		assertEquals(7, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-B")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-E")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-M")));
		assertEquals(3, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R")));
		assertEquals(10, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-E")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-I")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-S")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-X")));
		assertEquals(6, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable")));
		assertEquals(13, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.functions.SMO")));
		assertEquals(3, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.Vote")));
		assertEquals(15, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.Vote").classifierParameter));
		assertEquals(18, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.trees.RandomForest")));

		return tempFile;
	}

	private File getFile4Base(int minBaseClassifiers, int maxBaseClassifiers) throws IOException, ParseException {
		File tempFile = File.createTempFile("tmp", ".txt");
		tempFile.deleteOnExit();
		PrintStream out = new PrintStream(tempFile);
		out.println("@classifier weka.classifiers.lazy.KStar");
		out.println("@parameter globalBlend -B 20 integer [1,100]"); // 7 bits
		out.println("@parameter entropicAutoBlend -E false boolean"); // 1 bit
		out.println("@parameter missingMode -M a string {a,d,m,n}"); // 2 bits
		out.println("@end");
		out.println("@classifier weka.classifiers.rules.DecisionTable");
		out.println("@parameter evaluationMeasure -E acc string {acc, rmse, mae, auc}"); // 2
		out.println("@parameter useIbk -I false boolean"); // 1
		out.println("@parameter search -S weka.attributeSelection.BestFirst string {weka.attributeSelection.BestFirst,weka.attributeSelection.GreedyStepwise}"); // 1
		out.println("@parameter crossVal -X 1 integer {1,2,3}"); // 2
		out.println("@end");
		out.println("@classifier weka.classifiers.functions.SMO");
		out.println("@parameter kernel -K weka.classifiers.functions.supportVector.NormalizedPolyKernel string {weka.classifiers.functions.supportVector.NormalizedPolyKernel, weka.classifiers.functions.supportVector.PolyKernel, weka.classifiers.functions.supportVector.Puk, weka.classifiers.functions.supportVector.RBFKernel}"); // 2
		out.println("@sub-parameter -K weka.classifiers.functions.supportVector.NormalizedPolyKernel");
		out.println("@parameter exponent -E 1 double [0.2,5,1]"); // 6 bits
		out.println("@parameter useLowerOrder -L false boolean"); // 1 bit
		out.println("@end");
		out.println("@sub-parameter -K weka.classifiers.functions.supportVector.PolyKernel");
		out.println("@parameter exponent -E 1 double [0.2,5,1]"); // 6 bits
		out.println("@parameter useLowerOrder -L false boolean"); // 1 bits
		out.println("@conditional -L == true and -E == 5.0");
		out.println("@end");
		out.println("@sub-parameter -K weka.classifiers.functions.supportVector.Puk");
		out.println("@parameter sigma -S 1 double [0.1,10,1]"); // 7 bits
		out.println("@parameter omega -O 1 double [0.1,1,1]"); // 4 bits
		out.println("@end");
		out.println("@sub-parameter -K weka.classifiers.functions.supportVector.RBFKernel");
		out.println("@parameter gamma -G 0.01 double [0.0001,1,1]"); // 4 bits
		out.println("@end");
		out.println("@conditional -K == weka.classifiers.functions.supportVector.Puk");
		out.println("@end");
		out.println("@ensemble weka.classifiers.meta.Vote");
		out.println("@parameter seed -S 1 integer {1}"); // 0 bits
		out.println("@parameter combinationRule -R AVG string {AVG, PROD, MAJ, MIN, MAX, MED}"); // 3 bits
		out.println("@parameter-num-classifiers 2 [" + minBaseClassifiers + "," + maxBaseClassifiers + "]"); // ? bits
		// 2 bits
		out.println("@parameter classifier -B weka.classifiers.lazy.KStar classifier {weka.classifiers.lazy.KStar, weka.classifiers.rules.DecisionTable, weka.classifiers.functions.SMO}");
		out.println("@parameter-percent-homogeneous 0 {0.5}");
		out.println("@end");
		out.println("@ensemble weka.classifiers.meta.RandomCommittee");
		out.println("@parameter numIterations -I 10 integer [2,128]"); // 7 bits
		out.println("@parameter seed -S 1 integer {1}"); // 0
		out.println("@parameter classifier -W weka.classifiers.lazy.KStar classifier {weka.classifiers.lazy.KStar, weka.classifiers.rules.DecisionTable, weka.classifiers.functions.SMO}");
		out.println("@end");
		out.close();
		// 3 bit models

		Configuration configuration = new Configuration(tempFile);
		assertEquals(7, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-B")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-E")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar").parameters.get("-M")));
		assertEquals(3, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R")));
		assertEquals(10, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.lazy.KStar")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-E")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-I")));
		assertEquals(1, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-S")));
		assertEquals(2, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable").parameters.get("-X")));
		assertEquals(6, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.rules.DecisionTable")));
		assertEquals(13, Solve.getNumBits(configuration.classifiers.get("weka.classifiers.functions.SMO")));
		assertEquals(3, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.Vote")));
		assertEquals(15, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.Vote").classifierParameter));
		assertEquals(7, Solve.getNumBits(configuration.ensembles.get("weka.classifiers.meta.RandomCommittee")));

		return tempFile;
	}

	@Test
	public void test003SolveConfiguration() throws IOException, ParseException {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve = new Solve(configuration);
		assertEquals(10, solve.encode.length);
		assertFalse(solve.evaluated);

		configuration = new Configuration(getFile2());
		// model(1) | pModel(10)
		solve = new Solve(configuration);
		assertEquals(11, solve.encode.length);
		assertFalse(solve.evaluated);

		configuration = new Configuration(getFile1Base(3));
		// 44: model(1) | pModel(10) | bModel1(1) | bModel2(1) | bModel3(1) | pBModel1(10) | pBModel2(10) | pBmodel3(10)
		solve = new Solve(configuration);
		assertEquals(44, solve.encode.length);
		assertFalse(solve.evaluated);

		configuration = new Configuration(getFile2Base(2));
		// 44: model(2) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(10) | pBModel2(10)
		solve = new Solve(configuration);
		assertEquals(44, solve.encode.length);
		assertFalse(solve.evaluated);

		configuration = new Configuration(getFile3Base(1, 2));
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);
		assertEquals(51, solve.encode.length);
		assertFalse(solve.evaluated);
	}

	@Test
	public void test004SolveSolve() throws IOException, ParseException {
		Configuration configuration;
		Solve solve, clone;
		Random rand = new Random();

		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve = new Solve(configuration);
		solve.randomize(rand);
		clone = solve.clone();
		assertEquals(solve.encode.length, clone.encode.length);
		assertEquals(solve.evaluated, clone.evaluated);
		assertArrayEquals(solve.encode, clone.encode);

		configuration = new Configuration(getFile2());
		// model(1) | pModel(10)
		solve = new Solve(configuration);
		solve.randomize(rand);
		clone = solve.clone();
		assertEquals(solve.encode.length, clone.encode.length);
		assertEquals(solve.evaluated, clone.evaluated);
		assertArrayEquals(solve.encode, clone.encode);

		configuration = new Configuration(getFile1Base(3));
		// 44: model(1) | pModel(10) | bModel1(1) | bModel2(1) | bModel3(1) | pBModel1(10) | pBModel2(10) | pBmodel3(10)
		solve = new Solve(configuration);
		solve.randomize(rand);
		clone = solve.clone();
		assertEquals(solve.encode.length, clone.encode.length);
		assertEquals(solve.evaluated, clone.evaluated);
		assertArrayEquals(solve.encode, clone.encode);

		configuration = new Configuration(getFile2Base(2));
		// 44: model(2) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(10) | pBModel2(10)
		solve = new Solve(configuration);
		solve.randomize(rand);
		clone = solve.clone();
		assertEquals(solve.encode.length, clone.encode.length);
		assertEquals(solve.evaluated, clone.evaluated);
		assertArrayEquals(solve.encode, clone.encode);

		configuration = new Configuration(getFile3Base(1, 2));
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);
		solve.randomize(rand);
		clone = solve.clone();
		assertEquals(solve.encode.length, clone.encode.length);
		assertEquals(solve.evaluated, clone.evaluated);
		assertArrayEquals(solve.encode, clone.encode);
	}

	@Test
	public void test005GetValue() throws IOException, ParseException {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile1());
		solve = new Solve(configuration);

		Arrays.fill(solve.encode, false);
		assertEquals(0, solve.getValue(0, solve.encode.length));
		assertEquals(0, solve.getValue(0, 3));
		assertEquals(0, solve.getValue(3, 3));
		assertEquals(0, solve.getValue(3, 4));
		assertEquals(0, solve.getValue(0, 0));
		assertEquals(0, solve.getValue(0, 10));

		Arrays.fill(solve.encode, true);
		assertEquals(7, solve.getValue(0, 3));
		assertEquals(7, solve.getValue(3, 3));
		assertEquals(7, solve.getValue(6, 3));
		assertEquals(0, solve.getValue(0, 0));

		Arrays.fill(solve.encode, false);
		for (int i = 0; i < solve.encode.length; i += 2) {
			solve.encode[i] = true;
		}

		assertEquals(1, solve.getValue(0, 1));
		assertEquals(2, solve.getValue(0, 2));
		assertEquals(0, solve.getValue(1, 1));
		assertEquals(1, solve.getValue(1, 2));
		assertEquals(5, solve.getValue(0, 3));
		assertEquals(2, solve.getValue(1, 3));
		assertEquals(10, solve.getValue(0, 4));
		assertEquals(5, solve.getValue(1, 4));

		Arrays.fill(solve.encode, true);
		assertEquals(1023, solve.getValue(0, solve.encode.length));
	}

	@Test
	public void test006SetValue() throws IOException, ParseException {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile1());
		solve = new Solve(configuration);

		solve.setValue(0, 0, solve.encode.length);
		assertEquals(0, solve.getValue(0, solve.encode.length));
		solve.setValue(1023, 0, solve.encode.length);
		assertEquals(1023, solve.getValue(0, solve.encode.length));

		solve.setValue(1, 0, 4);
		assertEquals(1, solve.getValue(0, 4));
		solve.setValue(5, 0, 4);
		assertEquals(5, solve.getValue(0, 4));
		solve.setValue(0, 0, 4);
		assertEquals(0, solve.getValue(0, 4));
		solve.setValue(2, 0, 4);
		assertEquals(2, solve.getValue(0, 4));
		solve.setValue(3, 0, 4);
		assertEquals(3, solve.getValue(0, 4));
		solve.setValue(11, 0, 4);
		assertEquals(11, solve.getValue(0, 4));

		solve.randomize(new Random(0));
		solve.setValue(5, 0, 4);
		assertEquals(5, solve.getValue(0, 4));
		solve.randomize(new Random(0));
		solve.setValue(0, 0, 4);
		assertEquals(0, solve.getValue(0, 4));
		solve.randomize(new Random(0));
		solve.setValue(2, 0, 4);
		assertEquals(2, solve.getValue(0, 4));
		solve.randomize(new Random(0));
		solve.setValue(3, 0, 4);
		assertEquals(3, solve.getValue(0, 4));
		solve.randomize(new Random(0));
		solve.setValue(11, 0, 4);
		assertEquals(11, solve.getValue(0, 4));

		solve.randomize(new Random(0));
		solve.setValue(5, 3, 4);
		assertEquals(5, solve.getValue(3, 4));
		solve.randomize(new Random(0));
		solve.setValue(0, 3, 4);
		assertEquals(0, solve.getValue(3, 4));
		solve.randomize(new Random(0));
		solve.setValue(2, 3, 4);
		assertEquals(2, solve.getValue(3, 4));
		solve.randomize(new Random(0));
		solve.setValue(3, 3, 4);
		assertEquals(3, solve.getValue(3, 4));
		solve.randomize(new Random(0));
		solve.setValue(11, 3, 4);
		assertEquals(11, solve.getValue(3, 4));
	}

	@Test
	public void test007GetModelCode() throws IOException, ParseException {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile2Base(1));
		// model(2) | ...
		solve = new Solve(configuration);

		assertEquals(0, solve.getModelCode("weka.classifiers.meta.Vote"));
		assertEquals(1, solve.getModelCode("weka.classifiers.trees.RandomForest"));
		assertEquals(2, solve.getModelCode("weka.classifiers.lazy.KStar"));
		assertEquals(3, solve.getModelCode("weka.classifiers.rules.DecisionTable"));
	}

	@Test
	public void test008GetModelNameInt() throws IOException, ParseException {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile2Base(1));
		// model(2) | pModel(10) | ...
		solve = new Solve(configuration);

		assertEquals("weka.classifiers.meta.Vote", solve.getModelName(0));
		assertEquals("weka.classifiers.trees.RandomForest", solve.getModelName(1));
		assertEquals("weka.classifiers.lazy.KStar", solve.getModelName(2));
		assertEquals("weka.classifiers.rules.DecisionTable", solve.getModelName(3));
	}

	@Test
	public void test009GetModelName() throws IOException, ParseException {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile1());
		solve = new Solve(configuration);
		assertEquals("weka.classifiers.lazy.KStar", solve.getModelName());

		configuration = new Configuration(getFile2Base(1));
		// model(2) | pModel(10) | ...
		solve = new Solve(configuration);
		solve.setValue(0, 0, 2);
		assertEquals("weka.classifiers.meta.Vote", solve.getModelName());
		solve.setValue(1, 0, 2);
		assertEquals("weka.classifiers.trees.RandomForest", solve.getModelName());
		solve.setValue(2, 0, 2);
		assertEquals("weka.classifiers.lazy.KStar", solve.getModelName());
		solve.setValue(3, 0, 2);
		assertEquals("weka.classifiers.rules.DecisionTable", solve.getModelName());
	}

	@Test
	public void test010GetClassifiersCodes() throws IOException, ParseException {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile2Base(2));
		solve = new Solve(configuration);
		// 44: model(2) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(10) | pBModel2(10)

		solve.randomize(new Random(0));
		solve.setValue(0, 20, 2);
		solve.setValue(1, 22, 2);
		assertArrayEquals(new int[] { -1, 2 }, solve.getClassifiersCodes());

		configuration = new Configuration(getFile2Base(5));
		solve = new Solve(configuration);
		// 44: model(2) | pModel(18) | bModel1(2) | bModel2(2) ... bModel5(2) | ...

		solve.randomize(new Random(0));
		solve.setValue(0, 20, 2);
		solve.setValue(1, 22, 2);
		solve.setValue(2, 24, 2);
		solve.setValue(0, 26, 2);
		solve.setValue(2, 28, 2);
		assertArrayEquals(new int[] { -1, 2, 3, -1, 3 }, solve.getClassifiersCodes());
	}

	@Test
	public void test011GetParansIntParameter() throws IOException, ParseException {
		Configuration configuration;
		Solve solve;
		Parameter parameter;

		configuration = new Configuration(getFile3Base(1, 2));
		// 44: model(2) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(10) | pBModel2(10)
		solve = new Solve(configuration);

		// out.println("@ensemble weka.classifiers.meta.Vote");
		// out.println("@parameter seed -S 1 integer {1}"); // 0 bits
		// out.println("@parameter combinationRule -R AVG string {AVG, PROD, MAJ, MIN, MAX, MED}"); // 3 bits
		// out.println("@parameter-num-classifiers 2 [1," + maxBaseClassifiers + "]"); // ? bits
		// 2 bits
		// out.println("@parameter classifier -B weka.classifiers.lazy.KStar classifier {weka.classifiers.lazy.KStar,
		// weka.classifiers.rules.DecisionTable, weka.classifiers.functions.SMO}");

		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-S");
		assertEquals("-S 1", solve.getParans(0, parameter));
		solve.encode[0] = true;
		assertEquals("-S 1", solve.getParans(0, parameter));

		solve.setValue(0, 0, 3);
		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");
		assertEquals("-R AVG", solve.getParans(0, parameter));

		solve.setValue(1, 0, 3);
		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");
		assertEquals("-R PROD", solve.getParans(0, parameter));

		solve.setValue(2, 0, 3);
		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");
		assertEquals("-R MAJ", solve.getParans(0, parameter));

		solve.setValue(3, 0, 3);
		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");
		assertEquals("-R MIN", solve.getParans(0, parameter));

		solve.setValue(4, 0, 3);
		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");
		assertEquals("-R MAX", solve.getParans(0, parameter));

		solve.setValue(5, 0, 3);
		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");
		assertEquals("-R MED", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setValue(0, 0, 3);
		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");
		assertEquals("-R AVG", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setValue(1, 0, 3);
		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");
		assertEquals("-R PROD", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setValue(2, 0, 3);
		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");
		assertEquals("-R MAJ", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setValue(3, 0, 3);
		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");
		assertEquals("-R MIN", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setValue(4, 0, 3);
		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");
		assertEquals("-R MAX", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setValue(5, 0, 3);
		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");
		assertEquals("-R MED", solve.getParans(0, parameter));
	}

	@Test
	public void test012GetParansIntModel() throws IOException, ParseException {
		Configuration configuration;
		Solve solve;
		Parameter parameter;

		configuration = new Configuration(getFile3Base(1, 2));
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);

		// out.println("@parameter kernel -K weka.classifiers.functions.supportVector.NormalizedPolyKernel string
		// {weka.classifiers.functions.supportVector.NormalizedPolyKernel,
		// weka.classifiers.functions.supportVector.PolyKernel, weka.classifiers.functions.supportVector.Puk,
		// weka.classifiers.functions.supportVector.RBFKernel}"); // 2
		// out.println("@parameter exponent -E 1 double [0.2,5,1]"); // 5 bits
		// out.println("@parameter useLowerOrder -L false boolean"); // 1 bit
		parameter = configuration.classifiers.get("weka.classifiers.functions.SMO").parameters.get("-K");

		solve.setValue(1, 0, 2); // weka.classifiers.functions.supportVector.PolyKernel
		solve.setValue(2, 2, 6); // -E 0.4
		solve.setValue(1, 8, 1); // -L
		assertEquals("-K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"", solve.getParans(0, parameter));

		solve.setValue(0, 8, 1); // -L
		assertEquals("-K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4\"", solve.getParans(0, parameter));

		Arrays.fill(solve.encode, false);
		solve.setValue(0, 0, 3); // vote
		solve.setValue(1, 6, 1); // 2 classifiers
		solve.setValue(3, 21, 2); // smo
		solve.setValue(2, 23, 2); // decision table
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG", solve.getParans(0, configuration.ensembles.get("weka.classifiers.meta.Vote")));
	}

	@Test
	public void test013GetParans() throws IOException, ParseException {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve = new Solve(configuration);

		// out.println("@classifier weka.classifiers.lazy.KStar");
		// out.println("@parameter globalBlend -B 20 integer [1,100]"); // 7 bits
		// out.println("@parameter entropicAutoBlend -E false boolean"); // 1 bit
		// out.println("@parameter missingMode -M a string {a,d,m,n}"); // 2 bits

		assertEquals("weka.classifiers.lazy.KStar -B 1 -M a", solve.getParans());

		solve.setValue(99, 0, 7);
		solve.setValue(1, 7, 1);
		solve.setValue(3, 8, 2);
		assertEquals("weka.classifiers.lazy.KStar -B 100 -E -M n", solve.getParans());

		configuration = new Configuration(getFile3Base(1, 2));
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);

		Arrays.fill(solve.encode, false);
		solve.setValue(0, 0, 3); // vote
		solve.setValue(1, 6, 1); // 2 classifiers
		solve.setValue(3, 21, 2); // smo
		solve.setValue(2, 23, 2); // decision table

		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"", solve.getParans());
	}

	@Test
	public void test014SetParansStringParameterInt() throws Exception {

		Configuration configuration;
		Solve solve;
		Parameter parameter;

		configuration = new Configuration(getFile3Base(1, 2));
		// 44: model(2) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(10) | pBModel2(10)
		solve = new Solve(configuration);

		// out.println("@ensemble weka.classifiers.meta.Vote");
		// out.println("@parameter seed -S 1 integer {1}"); // 0 bits
		// out.println("@parameter combinationRule -R AVG string {AVG, PROD, MAJ, MIN, MAX, MED}"); // 3 bits
		// out.println("@parameter-num-classifiers 2 [1," + maxBaseClassifiers + "]"); // ? bits
		// 2 bits
		// out.println("@parameter classifier -B weka.classifiers.lazy.KStar classifier {weka.classifiers.lazy.KStar,
		// weka.classifiers.rules.DecisionTable, weka.classifiers.functions.SMO}");

		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-S");
		solve.setParans("-S 1", parameter, 0);
		assertEquals("-S 1", solve.getParans(0, parameter));
		solve.encode[0] = true;
		assertEquals("-S 1", solve.getParans(0, parameter));

		parameter = configuration.ensembles.get("weka.classifiers.meta.Vote").parameters.get("-R");

		Arrays.fill(solve.encode, true);
		solve.setParans("-R AVG", parameter, 0);
		assertEquals("-R AVG", solve.getParans(0, parameter));

		Arrays.fill(solve.encode, true);
		solve.setParans("-R PROD", parameter, 0);
		assertEquals("-R PROD", solve.getParans(0, parameter));

		Arrays.fill(solve.encode, true);
		solve.setParans("-R MAJ", parameter, 0);
		assertEquals("-R MAJ", solve.getParans(0, parameter));

		Arrays.fill(solve.encode, true);
		solve.setParans("-R MIN", parameter, 0);
		assertEquals("-R MIN", solve.getParans(0, parameter));

		Arrays.fill(solve.encode, true);
		solve.setParans("-R MAX", parameter, 0);
		assertEquals("-R MAX", solve.getParans(0, parameter));

		Arrays.fill(solve.encode, true);
		solve.setParans("-R MED", parameter, 0);
		assertEquals("-R MED", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-R AVG", parameter, 0);
		assertEquals("-R AVG", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-R PROD", parameter, 0);
		assertEquals("-R PROD", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-R MAJ", parameter, 0);
		assertEquals("-R MAJ", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-R MIN", parameter, 0);
		assertEquals("-R MIN", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-R MAX", parameter, 0);
		assertEquals("-R MAX", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-R MED", parameter, 0);
		assertEquals("-R MED", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-R AVG", parameter, 1);
		assertEquals("-R AVG", solve.getParans(1, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-R PROD", parameter, 1);
		assertEquals("-R PROD", solve.getParans(1, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-R MAJ", parameter, 1);
		assertEquals("-R MAJ", solve.getParans(1, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-R MIN", parameter, 1);
		assertEquals("-R MIN", solve.getParans(1, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-R MAX", parameter, 1);
		assertEquals("-R MAX", solve.getParans(1, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-R MED", parameter, 1);
		assertEquals("-R MED", solve.getParans(1, parameter));
	}

	@Test
	public void test015SetParansStringModelInt() throws Exception {
		Configuration configuration;
		Solve solve;
		Parameter parameter;

		configuration = new Configuration(getFile3Base(1, 2));
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);

		// out.println("@parameter kernel -K weka.classifiers.functions.supportVector.NormalizedPolyKernel string
		// {weka.classifiers.functions.supportVector.NormalizedPolyKernel,
		// weka.classifiers.functions.supportVector.PolyKernel, weka.classifiers.functions.supportVector.Puk,
		// weka.classifiers.functions.supportVector.RBFKernel}"); // 2
		// out.println("@parameter exponent -E 1 double [0.2,5,1]"); // 5 bits
		// out.println("@parameter useLowerOrder -L false boolean"); // 1 bit
		parameter = configuration.classifiers.get("weka.classifiers.functions.SMO").parameters.get("-K");

		solve.randomize(new Random(0));
		solve.setParans("-K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"", parameter, 0);
		assertEquals("-K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setParans("-K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4\"", parameter, 0);
		assertEquals("-K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4\"", solve.getParans(0, parameter));

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG", configuration.ensembles.get("weka.classifiers.meta.Vote"), 0);
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG", solve.getParans(0, configuration.ensembles.get("weka.classifiers.meta.Vote")));
	}

	@Test
	public void test016SetParansString() throws Exception {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve = new Solve(configuration);

		// out.println("@classifier weka.classifiers.lazy.KStar");
		// out.println("@parameter globalBlend -B 20 integer [1,100]"); // 7 bits
		// out.println("@parameter entropicAutoBlend -E false boolean"); // 1 bit
		// out.println("@parameter missingMode -M a string {a,d,m,n}"); // 2 bits

		solve.setParans("weka.classifiers.lazy.KStar -B 3 -M d");
		assertEquals("weka.classifiers.lazy.KStar -B 3 -M d", solve.getParans());

		solve.setParans("weka.classifiers.lazy.KStar -B 100 -E");
		assertEquals("weka.classifiers.lazy.KStar -B 100 -E -M a", solve.getParans());

		configuration = new Configuration(getFile3Base(1, 2));
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"");
		assertEquals("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"", solve.getParans());

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG", solve.getParans());

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \"", solve.getParans());

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"", solve.getParans());

		configuration = new Configuration(getFile4Base(1, 2));
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.meta.RandomCommittee -I 10 -S 1 -W weka.classifiers.functions.SMO -- -K \"weka.classifiers.functions.supportVector.PolyKernel -E 1.0\"");
		assertEquals("weka.classifiers.meta.RandomCommittee -I 10 -S 1 -W weka.classifiers.functions.SMO -- -K \"weka.classifiers.functions.supportVector.PolyKernel -E 1.0\"", solve.getParans());
	}

	@Test
	public void test017IsValidIntInt() throws IOException, ParseException {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve = new Solve(configuration);

		assertTrue(solve.isValid(0, 0));
		assertTrue(solve.isValid(1, 0));
		assertTrue(solve.isValid(2, 0));
		assertTrue(solve.isValid(16, 0));
		assertTrue(solve.isValid(0, 1));
		assertTrue(solve.isValid(1, 1));
		assertTrue(solve.isValid(2, 2));
		assertTrue(solve.isValid(16, 2));
		assertTrue(solve.isValid(1023, 0));

		Arrays.fill(solve.encode, true);
		assertFalse(solve.isValid(2, 0));
		assertFalse(solve.isValid(2, 1));
		assertFalse(solve.isValid(2, 2));
		assertFalse(solve.isValid(5, 0));
		assertFalse(solve.isValid(2, 1));
		assertFalse(solve.isValid(10, 2));
		assertFalse(solve.isValid(125, 2));

		assertTrue(solve.isValid(0, 0));
		assertTrue(solve.isValid(1, 0));
		assertTrue(solve.isValid(3, 0));
		assertTrue(solve.isValid(7, 0));
		assertTrue(solve.isValid(15, 0));
		assertTrue(solve.isValid(31, 0));
		assertTrue(solve.isValid(63, 0));
		assertTrue(solve.isValid(127, 0));
		assertTrue(solve.isValid(255, 0));
		assertTrue(solve.isValid(511, 0));
		assertTrue(solve.isValid(1023, 0));

		assertFalse(solve.isValid(2, 0));
		assertFalse(solve.isValid(4, 0));
		assertFalse(solve.isValid(8, 0));
		assertFalse(solve.isValid(16, 0));
		assertFalse(solve.isValid(32, 0));
		assertFalse(solve.isValid(64, 0));
		assertFalse(solve.isValid(128, 0));
		assertFalse(solve.isValid(256, 0));
		assertFalse(solve.isValid(512, 0));

		solve.randomize(new Random(0));
		assertTrue(solve.isValid(0, 0));
		assertTrue(solve.isValid(1, 0));
		assertTrue(solve.isValid(3, 0));
		assertTrue(solve.isValid(7, 0));
		assertTrue(solve.isValid(15, 0));
		assertTrue(solve.isValid(31, 0));
		assertTrue(solve.isValid(63, 0));
		assertTrue(solve.isValid(127, 0));
		assertTrue(solve.isValid(255, 0));
		assertTrue(solve.isValid(511, 0));
		assertTrue(solve.isValid(1023, 0));
	}

	@Test
	public void test018IsValidIntParameter() throws IOException, ParseException {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve = new Solve(configuration);

		Parameter parameter = Parameter.parse("@parameter intP -N 2 integer [0,10]");
		solve.setValue(11, 0, 4);
		assertFalse(solve.isValid(0, parameter));
		solve.setValue(12, 0, 4);
		assertFalse(solve.isValid(0, parameter));
		solve.setValue(13, 0, 4);
		assertFalse(solve.isValid(0, parameter));
		solve.setValue(14, 0, 4);
		assertFalse(solve.isValid(0, parameter));
		solve.setValue(15, 0, 4);
		assertFalse(solve.isValid(0, parameter));

		solve.setValue(0, 0, 4);
		assertTrue(solve.isValid(0, parameter));
		solve.setValue(1, 0, 4);
		assertTrue(solve.isValid(0, parameter));
		solve.setValue(2, 0, 4);
		assertTrue(solve.isValid(0, parameter));
		solve.setValue(3, 0, 4);
		assertTrue(solve.isValid(0, parameter));
		solve.setValue(4, 0, 4);
		assertTrue(solve.isValid(0, parameter));
		solve.setValue(5, 0, 4);
		assertTrue(solve.isValid(0, parameter));
		solve.setValue(6, 0, 4);
		assertTrue(solve.isValid(0, parameter));
		solve.setValue(7, 0, 4);
		assertTrue(solve.isValid(0, parameter));
		solve.setValue(8, 0, 4);
		assertTrue(solve.isValid(0, parameter));
		solve.setValue(9, 0, 4);
		assertTrue(solve.isValid(0, parameter));
		solve.setValue(10, 0, 4);
		assertTrue(solve.isValid(0, parameter));

		parameter = Parameter.parse("@parameter intP -N 2 integer [2,10]");
		solve.setValue(9, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.setValue(10, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.setValue(11, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.setValue(12, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.setValue(13, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.setValue(14, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.setValue(15, 1, 4);
		assertFalse(solve.isValid(1, parameter));

		solve.setValue(0, 1, 4);
		assertTrue(solve.isValid(1, parameter));
		solve.setValue(1, 1, 4);
		assertTrue(solve.isValid(1, parameter));
		solve.setValue(2, 1, 4);
		assertTrue(solve.isValid(1, parameter));
		solve.setValue(3, 1, 4);
		assertTrue(solve.isValid(1, parameter));
		solve.setValue(4, 1, 4);
		assertTrue(solve.isValid(1, parameter));
		solve.setValue(5, 1, 4);
		assertTrue(solve.isValid(1, parameter));
		solve.setValue(6, 1, 4);
		assertTrue(solve.isValid(1, parameter));
		solve.setValue(7, 1, 4);
		assertTrue(solve.isValid(1, parameter));
		solve.setValue(8, 1, 4);
		assertTrue(solve.isValid(1, parameter));
	}

	@Test
	public void test019IsValidIntModel() throws Exception {
		Configuration configuration;
		Solve solve;
		Model model;

		configuration = new Configuration(getFile3Base(1, 2));
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);

		// out.println("@parameter kernel -K weka.classifiers.functions.supportVector.NormalizedPolyKernel string
		// {weka.classifiers.functions.supportVector.NormalizedPolyKernel,
		// weka.classifiers.functions.supportVector.PolyKernel, weka.classifiers.functions.supportVector.Puk,
		// weka.classifiers.functions.supportVector.RBFKernel}"); // 2
		// out.println("@parameter exponent -E 1 double [0.2,5,1]"); // 6 bits
		// out.println("@parameter useLowerOrder -L false boolean"); // 1 bit
		model = configuration.classifiers.get("weka.classifiers.functions.SMO");

		solve.setParans("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -L -E 5.0\"");
		assertTrue(solve.isValid(3, model));

		solve.setParans("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"");
		assertTrue(solve.isValid(3, model));
		solve.setValue(0, 5, 6);
		assertTrue(solve.isValid(3, model));
		solve.setValue(2, 5, 6);
		assertTrue(solve.isValid(3, model));
		solve.setValue(8, 5, 6);
		assertTrue(solve.isValid(3, model));
		solve.setValue(47, 5, 6);
		assertTrue(solve.isValid(3, model));
		solve.setValue(48, 5, 6);
		assertTrue(solve.isValid(3, model));
		solve.setValue(49, 5, 6);
		assertFalse(solve.isValid(3, model));
		solve.setValue(50, 5, 6);
		assertFalse(solve.isValid(3, model));
		solve.setValue(54, 5, 6);
		assertFalse(solve.isValid(3, model));
		solve.setValue(63, 5, 6);
		assertFalse(solve.isValid(3, model));
	}

	@Test
	public void test020IsValid() throws Exception {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve = new Solve(configuration);

		// out.println("@classifier weka.classifiers.lazy.KStar");
		// out.println("@parameter globalBlend -B 20 integer [1,100]"); // 7 bits
		// out.println("@parameter entropicAutoBlend -E false boolean"); // 1 bit
		// out.println("@parameter missingMode -M a string {a,d,m,n}"); // 2 bits

		solve.setParans("weka.classifiers.lazy.KStar -B 3 -M d");
		assertEquals("weka.classifiers.lazy.KStar -B 3 -M d", solve.getParans());
		assertTrue(solve.isValid());

		solve.setParans("weka.classifiers.lazy.KStar -B 100 -E");
		assertEquals("weka.classifiers.lazy.KStar -B 100 -E -M a", solve.getParans());
		assertTrue(solve.isValid());

		configuration = new Configuration(getFile3Base(2, 5)); // 50% homogeneous (2,5) base classifiers
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13) |
		// pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"");
		assertEquals("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"", solve.getParans());

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG", solve.getParans());
		assertFalse("0 base classifiers, minimum 2.", solve.isValid());

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \"", solve.getParans());
		assertFalse("1 base classifiers, minimum 2.", solve.isValid());

		solve.randomize(new Random(0)); // 2 base
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"", solve.getParans());
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 2 base diff
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.rules.DecisionTable -E mae -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.rules.DecisionTable -E mae -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"", solve.getParans());
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 3 base (2 equals)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.rules.DecisionTable -E mae -S weka.attributeSelection.BestFirst -X 1 \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.rules.DecisionTable -E mae -S weka.attributeSelection.BestFirst -X 1 \"", solve.getParans());
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 3 base (all diff)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \"", solve.getParans());
		assertFalse("All diff base classifiers, minimum 2 equals", solve.isValid());

		solve.randomize(new Random(0)); // 4 base (2 equals)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 23 -M d \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 23 -M d \"", solve.getParans());
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 4 base (3 equals)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.lazy.KStar -B 30 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 33 -M d \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.lazy.KStar -B 30 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 33 -M d \"", solve.getParans());
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 4 base (4 equals)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.lazy.KStar -B 1 -M d \" -B \"weka.classifiers.lazy.KStar -B 2 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.lazy.KStar -B 1 -M d \" -B \"weka.classifiers.lazy.KStar -B 2 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \"", solve.getParans());
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 5 base (3 equals)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.lazy.KStar -B 30 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 33 -M d \" -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.PolyKernel -E 1.2\\\" \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.lazy.KStar -B 30 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 33 -M d \" -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.PolyKernel -E 1.2\\\" \"", solve.getParans());
		assertTrue(solve.isValid());
	}

	@Test
	public void test021IsSatisfiable() throws Exception {
		// out.println("@sub-parameter -K weka.classifiers.functions.supportVector.PolyKernel");
		// out.println("@parameter exponent -E 1 double [0.2,5,1]"); // 6 bits
		// out.println("@parameter useLowerOrder -L false boolean"); // 1 bits
		// out.println("@conditional -L == true and -E == 5.0");
		// out.println("@conditional -K == weka.classifiers.functions.supportVector.Puk");

		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile3Base(2, 5)); // 50% homogeneous (2,5) base classifiers
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13) |
		// pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"");
		assertTrue(solve.isValid());
		assertTrue(solve.isSatisfiable());

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4\"");
		assertTrue(solve.isValid());
		assertTrue(solve.isSatisfiable());

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -L -E 5.0\"");
		assertTrue(solve.isValid());
		assertFalse(solve.isSatisfiable());

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.Puk -S 1 -O 1\"");
		assertTrue(solve.isValid());
		assertFalse(solve.isSatisfiable());
	}

	@Test
	public void test022RepairIntParameterRandom() throws ParseException, IOException {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve = new Solve(configuration);

		Parameter parameter = Parameter.parse("@parameter intP -N 2 integer [0,10]");
		solve.setValue(11, 0, 4);
		assertFalse(solve.isValid(0, parameter));
		solve.repair(0, parameter, new Random(0));
		assertTrue(Integer.toString(solve.getValue(0, 4)), solve.isValid(0, parameter));
		solve.setValue(12, 0, 4);
		assertFalse(solve.isValid(0, parameter));
		solve.repair(0, parameter, new Random(0));
		assertTrue(Integer.toString(solve.getValue(0, 4)), solve.isValid(0, parameter));
		solve.setValue(13, 0, 4);
		assertFalse(solve.isValid(0, parameter));
		solve.repair(0, parameter, new Random(0));
		assertTrue(Integer.toString(solve.getValue(0, 4)), solve.isValid(0, parameter));
		solve.setValue(14, 0, 4);
		assertFalse(solve.isValid(0, parameter));
		solve.repair(0, parameter, new Random(0));
		assertTrue(Integer.toString(solve.getValue(0, 4)), solve.isValid(0, parameter));
		solve.setValue(15, 0, 4);
		assertFalse(solve.isValid(0, parameter));
		solve.repair(0, parameter, new Random(0));
		assertTrue(Integer.toString(solve.getValue(0, 4)), solve.isValid(0, parameter));

		parameter = Parameter.parse("@parameter intP -N 2 integer [2,10]");
		solve.setValue(9, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.repair(1, parameter, new Random(0));
		assertTrue(Integer.toString(solve.getValue(1, 4)), solve.isValid(1, parameter));
		solve.setValue(10, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.repair(1, parameter, new Random(0));
		assertTrue(Integer.toString(solve.getValue(1, 4)), solve.isValid(1, parameter));
		solve.setValue(11, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.repair(1, parameter, new Random(0));
		assertTrue(Integer.toString(solve.getValue(1, 4)), solve.isValid(1, parameter));
		solve.setValue(12, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.repair(1, parameter, new Random(0));
		assertTrue(Integer.toString(solve.getValue(1, 4)), solve.isValid(1, parameter));
		solve.setValue(13, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.repair(1, parameter, new Random(0));
		assertTrue(Integer.toString(solve.getValue(1, 4)), solve.isValid(1, parameter));
		solve.setValue(14, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.repair(1, parameter, new Random(0));
		assertTrue(Integer.toString(solve.getValue(1, 4)), solve.isValid(1, parameter));
		solve.setValue(15, 1, 4);
		assertFalse(solve.isValid(1, parameter));
		solve.repair(1, parameter, new Random(0));
		assertTrue(Integer.toString(solve.getValue(1, 4)), solve.isValid(1, parameter));
	}

	@Test
	public void test023RepairIntModelRandom() throws Exception {
		Configuration configuration;
		Solve solve;
		Model model;
		Random rand = new Random(0);

		configuration = new Configuration(getFile3Base(1, 2));
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);

		// out.println("@parameter kernel -K weka.classifiers.functions.supportVector.NormalizedPolyKernel string
		// {weka.classifiers.functions.supportVector.NormalizedPolyKernel,
		// weka.classifiers.functions.supportVector.PolyKernel, weka.classifiers.functions.supportVector.Puk,
		// weka.classifiers.functions.supportVector.RBFKernel}"); // 2
		// out.println("@parameter exponent -E 1 double [0.2,5,1]"); // 6 bits
		// out.println("@parameter useLowerOrder -L false boolean"); // 1 bit
		model = configuration.classifiers.get("weka.classifiers.functions.SMO");

		solve.setParans("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"");
		solve.setValue(49, 5, 6);
		assertFalse(solve.isValid(3, model));
		solve.repair(3, model, rand);
		assertTrue(Integer.toString(solve.getValue(5, 6)), solve.isValid(3, model));
		solve.setValue(50, 5, 6);
		assertFalse(solve.isValid(3, model));
		solve.repair(3, model, rand);
		assertTrue(Integer.toString(solve.getValue(5, 6)), solve.isValid(3, model));
		solve.setValue(54, 5, 6);
		assertFalse(solve.isValid(3, model));
		solve.repair(3, model, rand);
		assertTrue(Integer.toString(solve.getValue(5, 6)), solve.isValid(3, model));
		solve.setValue(63, 5, 6);
		assertFalse(solve.isValid(3, model));
		solve.repair(3, model, rand);
		assertTrue(Integer.toString(solve.getValue(5, 6)), solve.isValid(3, model));

		solve.setParans("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"");
		for (int i = 3; i < solve.encode.length; i++) {
			solve.encode[i] = true;
		}
		assertFalse(solve.isValid(3, model));
		solve.repair(3, model, rand);
		assertTrue(solve.isValid(3, model));

		solve.setParans("weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1");
		for (int i = 3; i < solve.encode.length; i++) {
			solve.encode[i] = true;
		}
		assertFalse(solve.isValid(3, model));
		solve.repair(3, model, rand);
		assertTrue(solve.isValid(3, model));
	}

	@Test
	public void test024RepairBaseModelsParans() throws Exception {
		Configuration configuration = new Configuration(getFile3Base(2, 5)); // 50% homogeneous (2,5) base classifiers
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | bModel3(2) | bModel4(2) | bModel5(2) | pBModel1(13) |
		// pBModel2(13) | pBModel1(13) | pBModel2(13)
		Solve solve = new Solve(configuration);
		Random rand = new Random(0);

		solve.randomize(rand); // 2 base
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"", solve.getParans());
		for (int i = 31; i < solve.encode.length; i++) {
			solve.encode[i] = true;
		}
		assertFalse(solve.isValid());
		solve.repairBaseModelsParans(rand);
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 2 base diff
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.rules.DecisionTable -E mae -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.rules.DecisionTable -E mae -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"", solve.getParans());
		for (int i = 31; i < solve.encode.length; i++) {
			solve.encode[i] = true;
		}
		assertFalse(solve.isValid());

		solve.repairBaseModelsParans(rand);
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 3 base (2 equals)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.rules.DecisionTable -E mae -S weka.attributeSelection.BestFirst -X 1 \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.rules.DecisionTable -E mae -S weka.attributeSelection.BestFirst -X 1 \"", solve.getParans());
		for (int i = 31; i < solve.encode.length; i++) {
			solve.encode[i] = true;
		}
		assertFalse(solve.isValid());
		solve.repairBaseModelsParans(rand);
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 4 base (2 equals)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 23 -M d \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 23 -M d \"", solve.getParans());
		for (int i = 31; i < solve.encode.length; i++) {
			solve.encode[i] = true;
		}
		assertFalse(solve.isValid());
		solve.repairBaseModelsParans(rand);
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 4 base (3 equals)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.lazy.KStar -B 30 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 33 -M d \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.lazy.KStar -B 30 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 33 -M d \"", solve.getParans());
		for (int i = 31; i < solve.encode.length; i++) {
			solve.encode[i] = true;
		}
		assertFalse(solve.isValid());
		solve.repairBaseModelsParans(rand);
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 4 base (4 equals)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.lazy.KStar -B 1 -M d \" -B \"weka.classifiers.lazy.KStar -B 2 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.lazy.KStar -B 1 -M d \" -B \"weka.classifiers.lazy.KStar -B 2 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \"", solve.getParans());
		for (int i = 31; i < solve.encode.length; i++) {
			solve.encode[i] = true;
		}
		assertFalse(solve.isValid());
		solve.repairBaseModelsParans(rand);
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 5 base (3 equals)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.lazy.KStar -B 30 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 33 -M d \" -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.PolyKernel -E 1.2\\\" \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.lazy.KStar -B 30 -M d \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \" -B \"weka.classifiers.lazy.KStar -B 33 -M d \" -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.PolyKernel -E 1.2\\\" \"", solve.getParans());
		for (int i = 31; i < solve.encode.length; i++) {
			solve.encode[i] = true;
		}
		assertFalse(solve.isValid());
		solve.repairBaseModelsParans(rand);
		assertTrue(solve.isValid());
	}

	@Test
	public void test025RepairNumClassifiers() throws Exception {
		Configuration configuration = new Configuration(getFile3Base(2, 5)); // 50% homogeneous (2,5) base classifiers
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13) |
		// pBModel1(13) | pBModel2(13)
		Solve solve = new Solve(configuration);

		Model model = configuration.ensembles.get("weka.classifiers.meta.Vote");
		Random rand = new Random(0);

		solve.randomize(rand);
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG", solve.getParans());
		assertFalse("0 base classifiers, minimum 2.", solve.isValid());
		solve.repairNumClassifiers(model, rand);
		solve.repairBaseModelsParans(rand);
		assertTrue(solve.isValid());

		solve.randomize(rand);
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \"", solve.getParans());
		assertFalse("1 base classifiers, minimum 2.", solve.isValid());
		solve.repairNumClassifiers(model, rand);
		solve.repairBaseModelsParans(rand);
		assertTrue(solve.isValid());
	}

	@Test
	public void test026RepairHomogeneousDistribution() throws Exception {
		Configuration configuration = new Configuration(getFile3Base(2, 5)); // 50% homogeneous (2,5) base classifiers
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13) |
		// pBModel1(13) | pBModel2(13)
		Solve solve = new Solve(configuration);
		Random rand = new Random(0);
		Model model = configuration.ensembles.get("weka.classifiers.meta.Vote");

		solve.randomize(new Random(0)); // 3 base (all diff)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \"", solve.getParans());
		assertFalse("All diff base classifiers, minimum 2 equals", solve.isValid());
		solve.repairHomogeneousDistribution(model, rand);
		solve.repairBaseModelsParans(rand);
		assertTrue(solve.isValid());
	}

	@Test
	public void test027RepairRandom() throws Exception {
		Configuration configuration;
		Solve solve;
		Random rand = new Random(0);

		configuration = new Configuration(getFile3Base(2, 5)); // 50% homogeneous (2,5) base classifiers
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13) |
		// pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG", solve.getParans());
		assertFalse("0 base classifiers, minimum 2.", solve.isValid());
		solve.repair(rand);
		assertTrue(solve.isValid());

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \"", solve.getParans());
		assertFalse("1 base classifiers, minimum 2.", solve.isValid());
		solve.repair(rand);
		assertTrue(solve.isValid());

		solve.randomize(new Random(0)); // 3 base (all diff)
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \"");
		assertEquals("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \" -B \"weka.classifiers.lazy.KStar -B 3 -M d \"", solve.getParans());
		assertFalse("All diff base classifiers, minimum 2 equals", solve.isValid());
		solve.repair(rand);
		assertTrue(solve.isValid());
	}

	@Test
	public void test028GetClassifier() throws Exception {
		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve = new Solve(configuration);

		// out.println("@classifier weka.classifiers.lazy.KStar");
		// out.println("@parameter globalBlend -B 20 integer [1,100]"); // 7 bits
		// out.println("@parameter entropicAutoBlend -E false boolean"); // 1 bit
		// out.println("@parameter missingMode -M a string {a,d,m,n}"); // 2 bits

		solve.setParans("weka.classifiers.lazy.KStar -B 3 -M d");
		assertNotNull(solve.getClassifier());
		assertTrue(solve.getClassifier() instanceof KStar);

		solve.setParans("weka.classifiers.lazy.KStar -B 100 -E");
		assertNotNull(solve.getClassifier());
		assertTrue(solve.getClassifier() instanceof KStar);

		configuration = new Configuration(getFile3Base(1, 2));
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"");
		assertNotNull(solve.getClassifier());
		assertTrue(solve.getClassifier() instanceof SMO);

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG");
		assertNotNull(solve.getClassifier());
		assertTrue(solve.getClassifier() instanceof Vote);

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \"");
		assertNotNull(solve.getClassifier());
		assertTrue(solve.getClassifier() instanceof Vote);

		solve.randomize(new Random(0));
		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"");
		assertNotNull(solve.getClassifier());
		assertTrue(solve.getClassifier() instanceof Vote);
	}

	@Test
	public void test029Evaluate() throws Exception {
		Instances instances = new Instances(new FileReader("Iris.arff"));
		instances.setClassIndex(instances.numAttributes() - 1);

		Configuration configuration;
		Solve solve;

		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve = new Solve(configuration);

		// out.println("@classifier weka.classifiers.lazy.KStar");
		// out.println("@parameter globalBlend -B 20 integer [1,100]"); // 7 bits
		// out.println("@parameter entropicAutoBlend -E false boolean"); // 1 bit
		// out.println("@parameter missingMode -M a string {a,d,m,n}"); // 2 bits

		solve.setParans("weka.classifiers.lazy.KStar -B 3 -M d");
		solve.evaluate(instances, 10);
		assertTrue(solve.percentualError < 0.1);
		assertTrue(solve.timeProcessed < 5000); // 5000 = 5sec

		solve.setParans("weka.classifiers.lazy.KStar -B 100 -E");
		solve.evaluate(instances, 10);
		assertTrue(solve.percentualError < 0.1);
		assertTrue(solve.timeProcessed < 5000);

		configuration = new Configuration(getFile3Base(1, 2));
		// 51: model(3) | pModel(18) | bModel1(2) | bModel2(2) | pBModel1(13) | pBModel2(13)
		solve = new Solve(configuration);

		solve.setParans("weka.classifiers.functions.SMO -K \"weka.classifiers.functions.supportVector.PolyKernel -E 0.4 -L\"");
		solve.evaluate(instances, 10);
		assertTrue(solve.percentualError < 0.5);
		assertTrue(solve.timeProcessed < 5000);

		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 0.2\\\" \"");
		solve.evaluate(instances, 10);
		assertTrue(solve.percentualError + "", solve.percentualError < 0.5);
		assertTrue(solve.timeProcessed < 5000);

		solve.setParans("weka.classifiers.meta.Vote -S 1 -R AVG -B \"weka.classifiers.functions.SMO -K \\\"weka.classifiers.functions.supportVector.PolyKernel -E 0.8\\\" \" -B \"weka.classifiers.rules.DecisionTable -E acc -S weka.attributeSelection.BestFirst -X 1 \"");
		solve.evaluate(instances, 10);
		assertTrue(solve.percentualError < 0.7);
		assertTrue(solve.timeProcessed < 5000);
	}

	@Test
	public void test030CompareTo() throws IOException, ParseException {
		Configuration configuration;
		Solve solve1, solve2;

		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve1 = new Solve(configuration);
		solve2 = new Solve(configuration);

		assertTrue(solve1.compareTo(solve2) == 0);

		solve1.evaluated = false;
		solve2.evaluated = true;

		assertTrue(solve1.compareTo(solve2) > 0);
		assertTrue(solve2.compareTo(solve1) < 0);

		solve1.evaluated = true;
		assertTrue(solve1.compareTo(solve2) == 0);

		solve1.evaluated = false;
		solve2.evaluated = false;
		solve2.percentualError = 0.9;

		assertTrue(solve1.compareTo(solve2) == 0);

		solve1.evaluated = false;
		solve2.evaluated = true;

		assertTrue(solve1.compareTo(solve2) > 0);
		assertTrue(solve2.compareTo(solve1) < 0);

		solve1.evaluated = true;
		assertTrue(solve1.compareTo(solve2) < 0);

		solve1.evaluated = false;
		solve1.percentualError = 0.1;
		solve2.evaluated = false;
		solve2.percentualError = 0.1;

		assertTrue(solve1.compareTo(solve2) == 0);

		solve1.evaluated = false;
		solve2.evaluated = true;

		assertTrue(solve1.compareTo(solve2) > 0);
		assertTrue(solve2.compareTo(solve1) < 0);

		solve1.evaluated = true;
		assertTrue(solve1.compareTo(solve2) == 0);

		solve1.evaluated = false;
		solve2.evaluated = false;
		solve2.percentualError = 0.9;

		assertTrue(solve1.compareTo(solve2) == 0);

		solve1.evaluated = false;
		solve2.evaluated = true;

		assertTrue(solve1.compareTo(solve2) > 0);
		assertTrue(solve2.compareTo(solve1) < 0);

		solve1.evaluated = true;
		assertTrue(solve1.compareTo(solve2) < 0);

		solve1.evaluated = false;
		solve1.percentualError = 0.1;
		solve2.evaluated = false;
		solve2.percentualError = 0.2;

		assertTrue(solve1.compareTo(solve2) == 0);

		solve1.evaluated = false;
		solve2.evaluated = true;

		assertTrue(solve1.compareTo(solve2) > 0);
		assertTrue(solve2.compareTo(solve1) < 0);

		solve1.evaluated = true;
		assertTrue(solve1.compareTo(solve2) < 0);

		solve1.evaluated = false;
		solve2.evaluated = false;
		solve2.percentualError = 0.9;

		assertTrue(solve1.compareTo(solve2) == 0);

		solve1.evaluated = false;
		solve2.evaluated = true;

		assertTrue(solve1.compareTo(solve2) > 0);
		assertTrue(solve2.compareTo(solve1) < 0);

		solve1.evaluated = true;
		assertTrue(solve1.compareTo(solve2) < 0);

		solve1.evaluated = false;
		solve1.percentualError = 0.2;
		solve2.evaluated = false;
		solve2.percentualError = 0.1;

		assertTrue(solve1.compareTo(solve2) == 0);

		solve1.evaluated = false;
		solve2.evaluated = true;

		assertTrue(solve1.compareTo(solve2) > 0);
		assertTrue(solve2.compareTo(solve1) < 0);

		solve1.evaluated = true;
		assertTrue(solve1.compareTo(solve2) > 0);

		solve1.evaluated = false;
		solve2.evaluated = false;
		solve2.percentualError = 0.9;

		assertTrue(solve1.compareTo(solve2) == 0);

		solve1.evaluated = false;
		solve2.evaluated = true;

		assertTrue(solve1.compareTo(solve2) > 0);
		assertTrue(solve2.compareTo(solve1) < 0);

		solve1.evaluated = true;
		assertTrue(solve1.compareTo(solve2) < 0);

		solve1.evaluated = false;
		solve1.percentualError = 0.1;
		solve1.timeProcessed = 100;
		solve2.evaluated = false;
		solve2.percentualError = 0.1;
		solve2.timeProcessed = 10;

		assertTrue(solve1.compareTo(solve2) == 0);

		solve1.evaluated = false;
		solve2.evaluated = true;

		assertTrue(solve1.compareTo(solve2) > 0);
		assertTrue(solve2.compareTo(solve1) < 0);

		solve1.evaluated = true;
		assertTrue(solve1.compareTo(solve2) > 0);

		solve1.evaluated = false;
		solve2.evaluated = false;
		solve2.percentualError = 0.9;

		assertTrue(solve1.compareTo(solve2) == 0);

		solve1.evaluated = false;
		solve2.evaluated = true;

		assertTrue(solve1.compareTo(solve2) > 0);
		assertTrue(solve2.compareTo(solve1) < 0);

		solve1.evaluated = true;
		assertTrue(solve1.compareTo(solve2) < 0);

		solve1.evaluated = true;
		solve1.percentualError = 0.1;
		solve1.timeProcessed = 100;
		solve2.evaluated = true;
		solve2.percentualError = 0.1;
		solve2.timeProcessed = 10;

		assertTrue(solve1.compareTo(solve2) > 0);
		assertTrue(solve2.compareTo(solve1) < 0);

		solve2.timeProcessed = 100;
		assertTrue(solve1.compareTo(solve2) == 0);
		assertTrue(solve2.compareTo(solve1) == 0);
	}

	@Test
	public void test031EqualsObject() throws IOException, ParseException {
		Configuration configuration;
		Solve solve1, solve2;

		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve1 = new Solve(configuration);
		solve2 = new Solve(configuration);

		solve1.encode[3] = true;
		solve2.encode[3] = true;
		assertTrue(solve1.equals(solve2));
		assertEquals(solve1.hashCode(), solve2.hashCode());

		solve1.evaluated = true;
		assertFalse(solve1.equals(solve2));
		assertFalse(solve1.hashCode() == solve2.hashCode());

		solve2.evaluated = true;
		assertTrue(solve1.equals(solve2));
		assertEquals(solve1.hashCode(), solve2.hashCode());
	}

	@Test
	public void test032ToString() throws Exception {
		Instances instances = new Instances(new FileReader("Iris.arff"));
		instances.setClassIndex(instances.numAttributes() - 1);
		Configuration configuration;
		Solve solve;
		configuration = new Configuration(getFile1());
		// model(0) | pModel(10)
		solve = new Solve(configuration);
		assertNotNull(solve.toString());
		solve.setParans("weka.classifiers.lazy.KStar -B 3 -M d");
		assertNotNull(solve.toString());
		solve.evaluate(instances, 10);
		assertNotNull(solve.toString());
		solve.setParans("weka.classifiers.lazy.KStar -B 23 -M d");
		solve.evaluate(instances, 10);
		assertNotNull(solve.toString());
	}
}
