package weka.classifiers.meta.pbilautoens.domain.comm.builders;

import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.Committee;
import weka.classifiers.meta.pbilautoens.Parameter;
import weka.classifiers.meta.pbilautoens.PbilRandom;
import weka.classifiers.meta.pbilautoens.domain.comm.Vote;
import weka.classifiers.meta.pbilautoens.enums.ParameterType;
import weka.classifiers.meta.pbilautoens.exception.InvalidParameterTypeException;

public class VoteBuilder extends CommitteeBuilder{

	public VoteBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	@Override
	public Committee defaultBuild() {
		committee = new Vote();
		Parameter s = new Parameter("S", ParameterType.INT);
		s.setValue("10");
		committee.addParameter(s);
		
		Parameter r = new Parameter("R", ParameterType.STRING);
		r.setValue("AVG");
		committee.addParameter(r);
		
		Parameter b = new Parameter("B", ParameterType.INT);
		b.setValue("2");
		committee.addParameter(b);
		
		Parameter num = new Parameter("num",ParameterType.INT);
		String value = "1";
		num.setValue(value);
		committee.addParameter(num);
		
		try {
			committee.setClassifiers(buildClassifiers(Integer.parseInt(value)));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		return committee;
	}

	@Override
	public Committee randomBuild() {
		committee = new Vote();
		Parameter s = new Parameter("S", ParameterType.INT);
		s.setValue(randomValueForParameter(s));
		committee.addParameter(s);
		
		Parameter r = new Parameter("R", ParameterType.STRING);
		r.setValue(randomValueForParameter(r));
		committee.addParameter(r);
		
		Parameter b = new Parameter("B", ParameterType.INT);
		b.setValue(randomValueForParameter(b));
		committee.addParameter(b);
		
		Parameter num = new Parameter("num",ParameterType.INT);
		String value = randomValueForParameter(num);
		num.setValue(value);
		committee.addParameter(num);
		
		try {
			committee.setClassifiers(buildClassifiers(Integer.parseInt(value)));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		return committee;
	}
}
