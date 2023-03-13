package weka.classifiers.meta.pbilautoens.domain.comm;

import weka.classifiers.meta.pbilautoens.Committee;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.CommitteeType;

public class AdaBoost extends Committee{
	public AdaBoost() {
		super();
		this.name = CommitteeType.ADA_BOOST.getInfo();
		this.classifierType = ClassifierType.COMMITTEE;
	}
}
