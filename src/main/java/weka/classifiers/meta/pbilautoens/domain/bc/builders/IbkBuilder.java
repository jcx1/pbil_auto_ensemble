package weka.classifiers.meta.pbilautoens.domain.bc.builders;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.ClassifierBuilder;
import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.Parameter;
import weka.classifiers.meta.pbilautoens.PbilRandom;
import weka.classifiers.meta.pbilautoens.domain.bc.Ibk;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;

public class IbkBuilder extends ClassifierBuilder{

	public IbkBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	@Override
	public Classifier defaultBuild() {
		classifier = new Ibk();
		Parameter e = new Parameter("E", ParameterType.BOOLEAN);
		e.setValue("false");
		classifier.addParameter(e);
		
		Parameter k = new Parameter("K",ParameterType.INT);
		k.setValue("1");
		classifier.addParameter(k);
		
		Parameter i = new Parameter("I", ParameterType.BOOLEAN);
		i.setValue("false");
		classifier.addParameter(i);
		
		Parameter f = new Parameter("F", ParameterType.BOOLEAN);
		f.setValue("false");
		classifier.addParameter(f);
		
		Parameter x = new Parameter("X", ParameterType.BOOLEAN);
		x.setValue("false");
		classifier.addParameter(x);
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new Ibk();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter e = new Parameter("E", ParameterType.BOOLEAN);
		e.setValue(randomValueForParameter(e));
		classifier.addParameter(e);
		
		Parameter k = new Parameter("K",ParameterType.INT);
		k.setValue(randomValueForParameter(k));
		classifier.addParameter(k);
		
		Parameter i = new Parameter("I", ParameterType.BOOLEAN);
		i.setValue(randomValueForParameter(i));
		classifier.addParameter(i);
		
		Parameter f = new Parameter("F", ParameterType.BOOLEAN);
		f.setValue(randomValueForParameter(f));
		classifier.addParameter(f);
		
		Parameter x = new Parameter("X", ParameterType.BOOLEAN);
		x.setValue(randomValueForParameter(x));
		classifier.addParameter(x);
		
		return classifier;
	}

}
