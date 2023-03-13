package weka.classifiers.meta.pbilautoens.domain.comm.prototypes;

import java.util.Arrays;

import weka.classifiers.meta.pbilautoens.ParameterPrototype;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;
import weka.classifiers.meta.pbilautoens.exception.InvalidParameterTypeException;

public class AdaBoostPrototype extends CommitteePrototype {
	
	public AdaBoostPrototype() throws InvalidParameterTypeException {
		ParameterPrototype q = new ParameterPrototype("Q", ParameterType.BOOLEAN);
		q.setPossibilities(Arrays.asList(true, false));
		parameters.put(q.getName(), q);

		ParameterPrototype p = new ParameterPrototype("P", ParameterType.INT);
		p = buildIntParamete(50, 100, 1, p);
		parameters.put(p.getName(), p);

		ParameterPrototype i = new ParameterPrototype("I", ParameterType.INT);
		i = buildIntParamete(2, 128, 1, i);
		parameters.put(i.getName(), i);

		ParameterPrototype s = new ParameterPrototype("S", ParameterType.INT);
		s = buildIntParamete(1, 255, 1, s);
		parameters.put(s.getName(), s);
		
		this.setNumberOfBranchClassifiers(1);
	}
}
