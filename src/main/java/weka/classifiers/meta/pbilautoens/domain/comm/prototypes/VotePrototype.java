package weka.classifiers.meta.pbilautoens.domain.comm.prototypes;

import java.util.Arrays;

import weka.classifiers.meta.pbilautoens.ParameterPrototype;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;
import weka.classifiers.meta.pbilautoens.exception.InvalidParameterTypeException;

public class VotePrototype extends CommitteePrototype{
	
	public VotePrototype() throws InvalidParameterTypeException {
		ParameterPrototype s = new ParameterPrototype("S", ParameterType.INT);
		s = buildIntParamete(1, 255, 1, s);
		parameters.put(s.getName(), s);
		
		ParameterPrototype r = new ParameterPrototype("R", ParameterType.STRING);
		r.setPossibilities(Arrays.asList("AVG", "PROD", "MAJ", "MIN", "MAX", "MED"));
		parameters.put(r.getName(), r);
		
		ParameterPrototype b = new ParameterPrototype("B", ParameterType.INT);
		b = buildIntParamete(1, 9, 1, b);
		parameters.put(b.getName(), b);
		
		ParameterPrototype num = new ParameterPrototype("num", ParameterType.INT);
		num = buildIntParamete(1, 10, 1, num);
		parameters.put(num.getName(), num);
		
		this.setNumberOfBranchClassifiers(2);
	}
}
