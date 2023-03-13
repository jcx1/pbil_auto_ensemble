package weka.classifiers.meta.pbilautoens.domain.bc;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.enums.BaseClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;

public class Ibk extends Classifier{
	public Ibk(){
		super();
		this.name = BaseClassifierType.IBK.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
