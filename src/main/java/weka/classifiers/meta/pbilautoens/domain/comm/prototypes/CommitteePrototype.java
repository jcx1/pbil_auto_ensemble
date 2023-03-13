package weka.classifiers.meta.pbilautoens.domain.comm.prototypes;

import weka.classifiers.meta.pbilautoens.ClassifierPrototype;

public class CommitteePrototype extends ClassifierPrototype {

	private int numberOfBranchClassifiers;

	public int getNumberOfBranchClassifiers() {
		return numberOfBranchClassifiers;
	}

	public void setNumberOfBranchClassifiers(int numberOfBranchClassifiers) {
		this.numberOfBranchClassifiers = numberOfBranchClassifiers;
	}
	
}
