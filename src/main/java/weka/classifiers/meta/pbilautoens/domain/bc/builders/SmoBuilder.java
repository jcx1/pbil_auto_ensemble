package weka.classifiers.meta.pbilautoens.domain.bc.builders;

import java.util.List;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.ClassifierBuilder;
import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.Parameter;
import weka.classifiers.meta.pbilautoens.PbilRandom;
import weka.classifiers.meta.pbilautoens.domain.bc.Smo;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;

public class SmoBuilder extends ClassifierBuilder{

	public SmoBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	public Classifier classifier;
	
	public Classifier defaultBuild() {
		classifier = new Smo();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter u = new Parameter("SEL",ParameterType.STRING);
		u.setValue("Defaut");
		List<Parameter> params = classifier.getParameters();
		params.add(u);
		classifier.setParameters(params);
		return classifier;
	}

	public Classifier randomBuild() {
		classifier = new Smo();
		classifier.setName("SMO");
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter u = new Parameter("SEL",ParameterType.STRING);
		u.setValue(randomValueForParameter(u));
		classifier.addParameter(u);
		return classifier;
	}

}
