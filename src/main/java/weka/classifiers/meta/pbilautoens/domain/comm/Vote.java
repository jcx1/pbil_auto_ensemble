package weka.classifiers.meta.pbilautoens.domain.comm;

import weka.classifiers.meta.pbilautoens.Committee;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.CommitteeType;

public class Vote extends Committee{
	public Vote() {
		super();
		this.name = CommitteeType.VOTE.getInfo();
		this.classifierType = ClassifierType.COMMITTEE;
	}
}
