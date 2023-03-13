package weka.classifiers.meta.pbilautoens.domain.bc;


import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.enums.BaseClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;

public class BaseClassifier extends Classifier {

	private BaseClassifierType baseClassifierType;

	public BaseClassifier() {
		super();
		this.setClassifierType(ClassifierType.BASE_CLASSIFIER);
	}
	
	public BaseClassifierType getBaseClassifierType() {
		return baseClassifierType;
	}

	public void setBaseClassifierType(BaseClassifierType baseClassifierType) {
		this.baseClassifierType = baseClassifierType;
	}
	
}
