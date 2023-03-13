package weka.classifiers.meta.pbilautoens.domain.bc.builders;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.ClassifierBuilder;
import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.Parameter;
import weka.classifiers.meta.pbilautoens.PbilRandom;
import weka.classifiers.meta.pbilautoens.domain.bc.DecisionTable;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;

public class DecisionTableBuilder extends ClassifierBuilder {

	public DecisionTableBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	@Override
	public Classifier defaultBuild() {
		classifier = new DecisionTable();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter e = new Parameter("E", ParameterType.STRING);
		e.setValue("acc");
		classifier.addParameter(e);
		
		Parameter s = new Parameter("S", ParameterType.STRING);
		s.setValue("BestFirst -D 1 -N 5");
		classifier.addParameter(s);
		
		Parameter i = new Parameter("I", ParameterType.BOOLEAN);
		i.setValue("false");
		classifier.addParameter(i);
		
		Parameter x = new Parameter("X", ParameterType.INT);
		x.setValue("1");
		classifier.addParameter(x);
		
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new DecisionTable();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter e = new Parameter("E", ParameterType.STRING);
		e.setValue(randomValueForParameter(e));
		classifier.addParameter(e);
		
		Parameter s = new Parameter("S", ParameterType.STRING);
		s.setValue(randomValueForParameter(s));
		classifier.addParameter(s);
		
		Parameter i = new Parameter("I", ParameterType.BOOLEAN);
		i.setValue(randomValueForParameter(i));
		classifier.addParameter(i);
		
		Parameter x = new Parameter("X", ParameterType.INT);
		x.setValue(randomValueForParameter(x));
		classifier.addParameter(x);
		
		return classifier;
	}

}
