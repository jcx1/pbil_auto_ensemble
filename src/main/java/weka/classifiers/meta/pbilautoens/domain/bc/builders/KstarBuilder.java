package weka.classifiers.meta.pbilautoens.domain.bc.builders;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.ClassifierBuilder;
import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.Parameter;
import weka.classifiers.meta.pbilautoens.PbilRandom;
import weka.classifiers.meta.pbilautoens.domain.bc.Kstar;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;

public class KstarBuilder extends ClassifierBuilder{

	public KstarBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	@Override
	public Classifier defaultBuild() {
		// TODO Auto-generated method stub
		classifier = new Kstar();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter b = new Parameter("B",ParameterType.INT);
		b.setValue("20");
		classifier.addParameter(b);
		
		Parameter e = new Parameter("E", ParameterType.BOOLEAN);
		e.setValue("false");
		classifier.addParameter(e);
		
		Parameter m = new Parameter("M", ParameterType.STRING);
		m.setValue("a");
		classifier.addParameter(m);
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new Kstar();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter b = new Parameter("B", ParameterType.INT);
		b.setValue(randomValueForParameter(b));
		classifier.addParameter(b);
		
		Parameter e = new Parameter("E", ParameterType.BOOLEAN);
		e.setValue(randomValueForParameter(e));
		classifier.addParameter(e);
		
		Parameter m = new Parameter("M", ParameterType.STRING);
		m.setValue(randomValueForParameter(m));
		classifier.addParameter(m);
		
		return classifier;
	}
}
