package weka.classifiers.meta.pbilautoens.domain.bc.wekabuilders;

import weka.classifiers.meta.pbilautoens.PossibilityKeySet;
import weka.classifiers.lazy.IBk;
import weka.core.SelectedTag;

public class IbkWekaBuilder {

	public static IBk buildForWeka(PossibilityKeySet pks) {
		
  IBk ibk = new IBk();
		ibk.setKNN(Integer.valueOf(pks.getKeyValuesPairs().get("K")));
		ibk.setMeanSquared(Boolean.valueOf(pks.getKeyValuesPairs().get("E")));
		ibk.setCrossValidate(Boolean.valueOf(pks.getKeyValuesPairs().get("X")));
		ibk.setDistanceWeighting(setF(pks));
		
		return ibk;
	}
	
	private static SelectedTag setF(PossibilityKeySet pks) {
		
		boolean f = Boolean.valueOf(pks.getKeyValuesPairs().get("F"));
		boolean i = Boolean.valueOf(pks.getKeyValuesPairs().get("I"));
		
		SelectedTag tag = null;
		
		if(i == true) {
			tag = new SelectedTag(IBk.WEIGHT_INVERSE, IBk.TAGS_WEIGHTING);
		}
		else if(f == false && i == false) {
			tag = new SelectedTag(IBk.WEIGHT_NONE, IBk.TAGS_WEIGHTING);
		}
		else if (f == true) {
			tag = new SelectedTag(IBk.WEIGHT_SIMILARITY, IBk.TAGS_WEIGHTING);
		}
		return tag;
	}
	
	
	
}
