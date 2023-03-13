package weka.classifiers.meta.pbilautoens.domain.comm;

import weka.classifiers.meta.pbilautoens.Committee;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.CommitteeType;

public class Bagging extends Committee{
	public Bagging() {
		super();
		this.name = CommitteeType.BAGGING.getInfo();
		this.classifierType =ClassifierType.COMMITTEE;
	}
}
