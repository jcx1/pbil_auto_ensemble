package weka.classifiers.meta.pbilautoens.domain.comm.builders;

import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.Committee;
import weka.classifiers.meta.pbilautoens.Parameter;
import weka.classifiers.meta.pbilautoens.PbilRandom;
import weka.classifiers.meta.pbilautoens.domain.comm.RandomForest;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;
import weka.classifiers.meta.pbilautoens.exception.InvalidParameterTypeException;

public class RandomForestBuilder extends CommitteeBuilder{

	public RandomForestBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}
	
	@Override
	public Committee defaultBuild() {
		committee = new RandomForest();
		
		Parameter i = new Parameter("I", ParameterType.INT);
		i.setValue("10");
		committee.addParameter(i);
		
		Parameter k = new Parameter("K", ParameterType.INT);
		k.setValue("2");
		committee.addParameter(k);
		
		Parameter w = new Parameter("W", ParameterType.INT);
		w.setValue("2");
		committee.addParameter(w);
		try {
			committee.setClassifiers(buildClassifiers(1));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		
		
		return committee;
	}

	@Override
	public Committee randomBuild() {
		committee = new RandomForest();
		
		Parameter i = new Parameter("I", ParameterType.INT);
		i.setValue(randomValueForParameter(i));
		committee.addParameter(i);
		
		Parameter k = new Parameter("K", ParameterType.INT);
		k.setValue(randomValueForParameter(k));
		committee.addParameter(k);
		
		Parameter w = new Parameter("W", ParameterType.INT);
		w.setValue(randomValueForParameter(w));
		committee.addParameter(w);
		
		try {
			committee.setClassifiers(buildClassifiers(1));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		
		return committee;
	}
}
