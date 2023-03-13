package weka.classifiers.meta.pbilautoens.domain.bc.builders;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.ClassifierBuilder;
import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.Parameter;
import weka.classifiers.meta.pbilautoens.PbilRandom;
import weka.classifiers.meta.pbilautoens.domain.bc.BayesNet;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;

public class BayesNetBuilder extends ClassifierBuilder{

	public BayesNetBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	@Override
	public Classifier defaultBuild() {
		classifier =new BayesNet();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		
		Parameter d = new Parameter("D", ParameterType.BOOLEAN);
		d.setValue("false");
		classifier.addParameter(d);

		Parameter q = new Parameter("Q",ParameterType.STRING);
		q.setValue("weka.classifiers.bayes.net.search.local.K2");
		classifier.addParameter(q);
		
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new BayesNet();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		
		Parameter d = new Parameter("D", ParameterType.BOOLEAN);
		d.setValue(randomValueForParameter(d));
		classifier.addParameter(d);

		Parameter q = new Parameter("Q", ParameterType.STRING);
		q.setValue(randomValueForParameter(q));
		classifier.addParameter(q);
		
		
		return classifier;
	}
}
