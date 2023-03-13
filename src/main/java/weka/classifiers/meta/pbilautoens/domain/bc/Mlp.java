package weka.classifiers.meta.pbilautoens.domain.bc;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.enums.BaseClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;

public class Mlp extends Classifier{

	public Mlp(){
		super();
		this.name = BaseClassifierType.MULTI_LAYER_PECEPTRON.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}
}
