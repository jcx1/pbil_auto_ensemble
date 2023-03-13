package weka.classifiers.meta.pbilautoens.domain.bc.builders;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.ClassifierBuilder;
import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.Parameter;
import weka.classifiers.meta.pbilautoens.PbilRandom;
import weka.classifiers.meta.pbilautoens.domain.bc.RandomTree;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;

public class RandomTreeBuilder extends ClassifierBuilder{

	public RandomTreeBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	@Override
	public Classifier defaultBuild() {
		classifier = new RandomTree();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter m = new Parameter("M",ParameterType.INT);
		m.setValue("1");
		classifier.addParameter(m);
		
		Parameter k = new Parameter("K",ParameterType.INT);
		k.setValue("2");
		classifier.addParameter(k);
		
		Parameter depth = new Parameter("depth",ParameterType.INT);
		depth.setValue("2");
		classifier.addParameter(depth);
		
		Parameter n = new Parameter("N",ParameterType.INT);
		n.setValue("3");
		classifier.addParameter(n);
		
		Parameter u = new Parameter("U",ParameterType.BOOLEAN);
		u.setValue("false");
		classifier.addParameter(u);
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new RandomTree();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter m = new Parameter("M",ParameterType.INT);
		m.setValue(randomValueForParameter(m));
		classifier.addParameter(m);
		
		Parameter k = new Parameter("K",ParameterType.INT);
		k.setValue(randomValueForParameter(k));
		classifier.addParameter(k);
		
		Parameter depth = new Parameter("depth",ParameterType.INT);
		depth.setValue(randomValueForParameter(depth));
		classifier.addParameter(depth);
		
		Parameter n = new Parameter("N",ParameterType.INT);
		n.setValue(randomValueForParameter(n));
		classifier.addParameter(n);
		
		Parameter u = new Parameter("U",ParameterType.BOOLEAN);
		u.setValue(randomValueForParameter(u));
		classifier.addParameter(u);
		return classifier;
	}

}
