package weka.classifiers.meta.pbilautoens.domain.bc.prototypes;

import java.util.Arrays;

import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.ParameterPrototype;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;
import weka.classifiers.meta.pbilautoens.exception.InvalidParameterTypeException;

public class DecisionTablePrototype extends ClassifierPrototype{
	
	public DecisionTablePrototype() throws InvalidParameterTypeException{
		ParameterPrototype e = new ParameterPrototype("E", ParameterType.STRING);
		e.setPossibilities(Arrays.asList("acc", "rmse", "mae", "auc"));
		parameters.put(e.getName(), e);
		
		ParameterPrototype i = new ParameterPrototype("I", ParameterType.BOOLEAN);
		i.setPossibilities(Arrays.asList(true,false));
		parameters.put(i.getName(), i);
		
		ParameterPrototype s = new ParameterPrototype("S", ParameterType.STRING);
		s.setPossibilities(Arrays.asList("BestFirst -D 1 -N 5", "GreedyStepwise -T -1.7976931348623157E308 -N 1 -num-slots 1"));
		parameters.put(s.getName(), s);
		
		ParameterPrototype x= new ParameterPrototype("X", ParameterType.INT);
		x = buildIntParamete(1, 4, 1, x);
		parameters.put(x.getName(), x);
	}
	
}
