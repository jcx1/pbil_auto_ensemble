package weka.classifiers.meta.pbilautoens.domain.comm.wekabuilders;

import weka.classifiers.meta.pbilautoens.PossibilityKeySet;
import weka.classifiers.meta.pbilautoens.domain.bc.wekabuilders.WekaBuilder;
import weka.classifiers.meta.RandomCommittee;

public class RandomCommitteeWekaBuilder{
	
	public static RandomCommittee buildForWeka(PossibilityKeySet pks) {
		
		RandomCommittee rc = new RandomCommittee();
		rc.setNumIterations(Integer.valueOf(pks.getKeyValuesPairs().get("I")));
		rc.setSeed(Integer.valueOf(pks.getKeyValuesPairs().get("S")));
		
		rc.setClassifier(WekaBuilder.buildClassifier(pks.getBranchClassifiers().get(0)));
		return rc;
	}
	
	
}
