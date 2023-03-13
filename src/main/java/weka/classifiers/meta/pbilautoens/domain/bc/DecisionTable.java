package weka.classifiers.meta.pbilautoens.domain.bc;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.enums.BaseClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;

public class DecisionTable extends Classifier{

	public DecisionTable(){
		super();
		this.name = BaseClassifierType.DECISION_TABLE.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}
	
}
