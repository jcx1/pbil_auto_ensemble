package weka.classifiers.meta.pbilautoens.domain.comm.prototypes;


import weka.classifiers.meta.pbilautoens.ParameterPrototype;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;
import weka.classifiers.meta.pbilautoens.exception.InvalidParameterTypeException;

public class StackingPrototype extends CommitteePrototype{
	public StackingPrototype() throws InvalidParameterTypeException {
		ParameterPrototype x = new ParameterPrototype("X", ParameterType.INT);
		x = buildIntParamete(1, 10, 1, x);
		parameters.put(x.getName(),x);
		
		ParameterPrototype s = new ParameterPrototype("S", ParameterType.INT);
		s = buildIntParamete(1, 255, 1, s);
		parameters.put(s.getName(),s);
		
		ParameterPrototype b = new ParameterPrototype("B", ParameterType.INT);
		b = buildIntParamete(1, 9, 1, b);
		parameters.put(b.getName(),b);
		
		ParameterPrototype num = new ParameterPrototype("num", ParameterType.INT);
		num = buildIntParamete(1, 10, 1, num);
		parameters.put(num.getName(),num);

		this.setNumberOfBranchClassifiers(2);
	}
}
