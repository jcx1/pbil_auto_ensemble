package weka.classifiers.meta.pbilautoens.domain.bc;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.enums.BaseClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;

public class J48 extends Classifier{
	public J48(){
		super();
		this.name = BaseClassifierType.J48.getInfo();
		this.classifierType = ClassifierType.BASE_CLASSIFIER;
	}

}
