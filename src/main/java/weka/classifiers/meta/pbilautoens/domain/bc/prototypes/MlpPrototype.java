package weka.classifiers.meta.pbilautoens.domain.bc.prototypes;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.ParameterPrototype;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;
import weka.classifiers.meta.pbilautoens.exception.InvalidParameterTypeException;

public class MlpPrototype extends ClassifierPrototype{
	
	public MlpPrototype() throws InvalidParameterTypeException {
		
		ParameterPrototype l = new ParameterPrototype("L", ParameterType.DOUBLE);
		l = buildDoubleParameter(10, 100, 10, l);
		parameters.put("L", l);
		
		ParameterPrototype m = new ParameterPrototype("M", ParameterType.DOUBLE);
		m = buildDoubleParameter(10, 100, 10,m);
		parameters.put("M", m);
		
		ParameterPrototype b = new ParameterPrototype("B", ParameterType.BOOLEAN);
		List<Boolean> bValaues = new ArrayList<Boolean>();
		bValaues.add(true);
		bValaues.add(false);
		b.setPossibilities(bValaues);
		parameters.put("B", b);
				
		ParameterPrototype h = new ParameterPrototype("H", ParameterType.STRING);
		List<String> hValaues = new ArrayList<String>();
		hValaues.add("a");
		hValaues.add("i");
		hValaues.add("o");
		hValaues.add("t");
		h.setPossibilities(hValaues);
		parameters.put("H", h);
		
		ParameterPrototype c = new ParameterPrototype("C", ParameterType.BOOLEAN);
		List<Boolean> cValaues = new ArrayList<Boolean>();
		cValaues.add(true);
		cValaues.add(false);
		c.setPossibilities(cValaues);
		parameters.put("C", c);
		
		ParameterPrototype r = new ParameterPrototype("R", ParameterType.BOOLEAN);
		List<Boolean> rValaues = new ArrayList<Boolean>();
		rValaues.add(true);
		rValaues.add(false);
		r.setPossibilities(rValaues);
		parameters.put("R", r);
		
		ParameterPrototype d = new ParameterPrototype("D", ParameterType.BOOLEAN);
		List<Boolean> dValaues = new ArrayList<Boolean>();
		dValaues.add(true);
		dValaues.add(false);
		d.setPossibilities(dValaues);
		parameters.put("D", d);
		
		ParameterPrototype s = new ParameterPrototype("S", ParameterType.STRING);
		s = buildIntParamete(1, 255, 1,s);
		parameters.put("S", s);
	}
}
