package weka.classifiers.meta.pbilautoens.domain.bc;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.enums.BaseClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;

public class NaiveBayes extends Classifier{
	public NaiveBayes( ) {
		super();
		this.name = BaseClassifierType.NAIVE_BAYES.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
