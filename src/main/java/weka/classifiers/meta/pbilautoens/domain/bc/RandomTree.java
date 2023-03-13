package weka.classifiers.meta.pbilautoens.domain.bc;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.enums.BaseClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;

public class RandomTree extends Classifier{
	public RandomTree() {
		super();
		this.name = BaseClassifierType.RANDOM_TREE.getInfo();
		this.classifierType = ClassifierType.BASE_CLASSIFIER;
	}
}
