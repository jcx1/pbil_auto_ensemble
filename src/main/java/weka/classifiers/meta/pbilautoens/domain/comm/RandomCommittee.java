package weka.classifiers.meta.pbilautoens.domain.comm;

import weka.classifiers.meta.pbilautoens.Committee;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.CommitteeType;

public class RandomCommittee extends Committee{

	public RandomCommittee(){
		super();
		this.name = CommitteeType.RANDOM_COMMITTEE.getInfo();
		this.classifierType = ClassifierType.COMMITTEE;
	}
	
}
