package weka.classifiers.meta.pbilautoens.domain.bc;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.enums.BaseClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;

public class Kstar extends Classifier{
	public Kstar() {
		super();
		this.name = BaseClassifierType.K_STAR.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
