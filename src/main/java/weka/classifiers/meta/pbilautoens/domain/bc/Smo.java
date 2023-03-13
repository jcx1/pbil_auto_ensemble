package weka.classifiers.meta.pbilautoens.domain.bc;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.enums.BaseClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;

public class Smo extends Classifier {
	
	public Smo(){
		super();
		this.name = BaseClassifierType.SMO.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
