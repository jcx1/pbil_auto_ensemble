package weka.classifiers.meta.pbilautoens.domain.bc.prototypes;


import java.util.Arrays;

import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.ParameterPrototype;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;
import weka.classifiers.meta.pbilautoens.exception.InvalidParameterTypeException;

public class KstarPrototype extends ClassifierPrototype{
	public KstarPrototype() throws InvalidParameterTypeException {
		// TODO Auto-generated constructor stub
		ParameterPrototype b = new ParameterPrototype("B",ParameterType.INT);
		b = buildIntParamete(1, 100,1, b);
		parameters.put(b.getName(), b);
		
		ParameterPrototype e =new ParameterPrototype("E", ParameterType.BOOLEAN);
		e.setPossibilities(Arrays.asList(true,false));
		parameters.put(e.getName(), e);
		
		ParameterPrototype m = new ParameterPrototype("M", ParameterType.STRING);
		m.setPossibilities(Arrays.asList("a","d","m","n"));
		parameters.put(m.getName(), m);
	}
}
