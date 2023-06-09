package weka.classifiers.meta.pbilautoens.domain.comm.wekabuilders;

import weka.classifiers.meta.pbilautoens.PossibilityKeySet;
import weka.classifiers.meta.pbilautoens.domain.bc.wekabuilders.WekaBuilder;
import weka.classifiers.Classifier;
import weka.classifiers.meta.Stacking;

public class StackingWekaBuilder {

	public static Stacking buildForWeka(PossibilityKeySet pks){
		Stacking st = new Stacking();
		Classifier[] classifiers = new Classifier[pks.getBranchClassifiers().size()];

		for (int i = 0; i < classifiers.length; i++) {
			classifiers[i] = WekaBuilder.buildClassifier(pks.getBranchClassifiers().get(i));
		}

		st.setClassifiers(classifiers);

		try {
			st.setNumFolds(Integer.valueOf(pks.getKeyValuesPairs().get("X")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		st.setSeed(Integer.parseInt(pks.getKeyValuesPairs().get("S")));

		return st;
	}

}
