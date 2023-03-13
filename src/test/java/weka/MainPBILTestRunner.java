package weka;


import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import weka.classifiers.meta.PopulationBasedIncrementalLearningTest;
import weka.classifiers.meta.pbil.ConditionalTest;
import weka.classifiers.meta.pbil.ConfigurationTest;
import weka.classifiers.meta.pbil.IntervalTest;
import weka.classifiers.meta.pbil.ModelTest;
import weka.classifiers.meta.pbil.ParameterTest;
import weka.classifiers.meta.pbil.SolveTest;

public class MainPBILTestRunner {

	// enable assertion -ea on virtual machine arguments
	public static void main(String[] args) throws Exception {
		Deque<Class<?>> testClasses = new ArrayDeque<>();
		testClasses.add(IntervalTest.class);
		testClasses.add(ParameterTest.class);
		testClasses.add(ConditionalTest.class);
		testClasses.add(ModelTest.class);
		testClasses.add(ConfigurationTest.class);
		testClasses.add(SolveTest.class);
		testClasses.add(PopulationBasedIncrementalLearningTest.class);

		while (!testClasses.isEmpty()) {
			Class<?> testClass = testClasses.pollFirst();
			Result result = JUnitCore.runClasses(testClass);

			for (Failure failure : result.getFailures()) {
				Throwable e = failure.getException();
				Deque<StackTraceElement> trace = new ArrayDeque<>(Arrays.asList(e.getStackTrace()));
				while (!trace.isEmpty() && !testClass.getCanonicalName().equals(trace.getLast().getClassName())) {
					trace.removeLast();
				}

				e.setStackTrace(trace.toArray(new StackTraceElement[trace.size()]));

				System.err.println(failure.getMessage());
				e.printStackTrace(System.err);

				System.out.println();

			}
		}

		System.out.println("End Test");
	}
}
