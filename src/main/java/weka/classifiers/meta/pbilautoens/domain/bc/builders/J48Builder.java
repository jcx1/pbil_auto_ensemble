package weka.classifiers.meta.pbilautoens.domain.bc.builders;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.ClassifierBuilder;
import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.Parameter;
import weka.classifiers.meta.pbilautoens.PbilRandom;
import weka.classifiers.meta.pbilautoens.domain.bc.J48;
import weka.classifiers.meta.pbilautoens.enums.ClassifierType;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;

public class J48Builder extends ClassifierBuilder{

	public J48Builder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}
	
	@Override
	public Classifier defaultBuild() {
		classifier = new J48();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter o = new Parameter("O",ParameterType.BOOLEAN);
		o.setValue("false");
		classifier.addParameter(o);
		
		Parameter u = new Parameter("U",ParameterType.BOOLEAN);
		u.setValue("false");
		classifier.addParameter(u);
		
		Parameter a = new Parameter("A",ParameterType.BOOLEAN);
		a.setValue("false");
		classifier.addParameter(a);
		
		Parameter b = new Parameter("B",ParameterType.BOOLEAN);
		b.setValue("false");
		classifier.addParameter(b);
		
		Parameter j = new Parameter("J",ParameterType.BOOLEAN);
		j.setValue("false");
		classifier.addParameter(j);
		
		Parameter s = new Parameter("S",ParameterType.BOOLEAN);
		s.setValue("false");
		classifier.addParameter(s);
		
		Parameter m = new Parameter("M",ParameterType.INT);
		m.setValue("2");
		classifier.addParameter(m);
		
		Parameter c = new Parameter("C",ParameterType.DOUBLE);
		c.setValue("0.25");
		classifier.addParameter(c);
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new J48();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter o = new Parameter("O",ParameterType.BOOLEAN);
		o.setValue(randomValueForParameter(o));
		classifier.addParameter(o);
		
		Parameter u = new Parameter("U",ParameterType.BOOLEAN);
		u.setValue(randomValueForParameter(u));
		classifier.addParameter(u);
		
		Parameter a = new Parameter("A",ParameterType.BOOLEAN);
		a.setValue(randomValueForParameter(a));
		classifier.addParameter(a);
		
		Parameter b = new Parameter("B",ParameterType.BOOLEAN);
		b.setValue(randomValueForParameter(b));
		classifier.addParameter(b);
		
		Parameter j = new Parameter("J",ParameterType.BOOLEAN);
		j.setValue(randomValueForParameter(j));
		classifier.addParameter(j);
		
		Parameter s = new Parameter("S",ParameterType.BOOLEAN);
		s.setValue(randomValueForParameter(s));
		classifier.addParameter(s);
		
		Parameter m = new Parameter("M",ParameterType.INT);
		m.setValue(randomValueForParameter(m));
		classifier.addParameter(m);
		
		Parameter c = new Parameter("C",ParameterType.DOUBLE);
		c.setValue(randomValueForParameter(c));
		classifier.addParameter(c);
		return classifier;
	}
}
