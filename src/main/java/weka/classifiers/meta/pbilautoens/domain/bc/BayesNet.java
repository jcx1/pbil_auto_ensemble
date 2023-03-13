package weka.classifiers.meta.pbilautoens.domain.bc;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.enums.BaseClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;

public class BayesNet extends Classifier{
	public BayesNet() {
		// TODO Auto-generated constructor stub
		super();
		this.name = BaseClassifierType.NET.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}
	

}
