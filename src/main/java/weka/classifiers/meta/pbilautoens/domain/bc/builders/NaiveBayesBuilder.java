package weka.classifiers.meta.pbilautoens.domain.bc.builders;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.ClassifierBuilder;
import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.Parameter;
import weka.classifiers.meta.pbilautoens.PbilRandom;
import weka.classifiers.meta.pbilautoens.domain.bc.NaiveBayes;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;

public class NaiveBayesBuilder extends ClassifierBuilder{

	public NaiveBayesBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	public Classifier defaultBuild() {
		classifier = new NaiveBayes();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		Parameter d = new Parameter("D",ParameterType.BOOLEAN);
		d.setValue("false");
		parameters.add(d);
		
		d.setName("K");
		parameters.add(d);
		
		classifier.setParameters(parameters);
		
		return classifier;
	}

	public Classifier randomBuild() {
		classifier = new NaiveBayes();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		Parameter d = new Parameter("D",ParameterType.BOOLEAN);
		d.setValue(randomValueForParameter(d));
		parameters.add(d);
		
		Parameter k = new Parameter("K", ParameterType.BOOLEAN);
		k.setValue(randomValueForParameter(k));
		parameters.add(k);
		
		classifier.setParameters(parameters); 
		return classifier;
	}

	public Classifier wheitedDrawBuild() {
		return null;
	}
}
