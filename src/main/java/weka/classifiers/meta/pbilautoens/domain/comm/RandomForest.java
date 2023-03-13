package weka.classifiers.meta.pbilautoens.domain.comm;

import weka.classifiers.meta.pbilautoens.Committee;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.CommitteeType;

public class RandomForest extends Committee{
	public RandomForest() {
		super();
		this.name = CommitteeType.RANDOM_FOREST.getInfo();
		this.classifierType =ClassifierType.COMMITTEE;
	}
}
