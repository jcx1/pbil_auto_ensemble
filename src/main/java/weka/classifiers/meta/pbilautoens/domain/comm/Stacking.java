package weka.classifiers.meta.pbilautoens.domain.comm;

import weka.classifiers.meta.pbilautoens.Committee;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.CommitteeType;

public class Stacking extends Committee{
	public Stacking() {
		super();
		this.name = CommitteeType.STACKING.getInfo();
		this.classifierType = ClassifierType.COMMITTEE;
	}
}
