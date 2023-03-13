package weka.classifiers.meta.pbilautoens.domain.comm;

import java.util.ArrayList;
import java.util.HashMap;

import weka.classifiers.meta.pbilautoens.ClassifierBuilder;
import weka.classifiers.meta.pbilautoens.ClassifierFactory;
import weka.classifiers.meta.pbilautoens.PbilRandom;
import weka.classifiers.meta.pbilautoens.domain.comm.builders.AdaBoostBuilder;
import weka.classifiers.meta.pbilautoens.domain.comm.builders.BaggingBuilder;
import weka.classifiers.meta.pbilautoens.domain.comm.builders.RandomCommitteeBuilder;
import weka.classifiers.meta.pbilautoens.domain.comm.builders.RandomForestBuilder;
import weka.classifiers.meta.pbilautoens.domain.comm.builders.StackingBuilder;
import weka.classifiers.meta.pbilautoens.domain.comm.builders.VoteBuilder;
import weka.classifiers.meta.pbilautoens.domain.comm.prototypes.AdaBoostPrototype;
import weka.classifiers.meta.pbilautoens.domain.comm.prototypes.BaggingPrototype;
import weka.classifiers.meta.pbilautoens.domain.comm.prototypes.RandomCommitteePrototype;
import weka.classifiers.meta.pbilautoens.domain.comm.prototypes.RandomForestPrototype;
import weka.classifiers.meta.pbilautoens.domain.comm.prototypes.StackingPrototype;
import weka.classifiers.meta.pbilautoens.domain.comm.prototypes.VotePrototype;
import weka.classifiers.meta.pbilautoens.enums.CommitteeType;
import weka.classifiers.meta.pbilautoens.exception.InvalidParameterTypeException;

public class CommitteeFactory extends ClassifierFactory{

	public CommitteeFactory(PbilRandom pbilRandom) throws InvalidParameterTypeException {
		
		builders = new HashMap<String, ClassifierBuilder>();
		buildNames();
		AdaBoostBuilder adaBuilder = new AdaBoostBuilder(new AdaBoostPrototype(), pbilRandom);
		builders.put(CommitteeType.ADA_BOOST.getInfo(),adaBuilder);
		
		BaggingBuilder baggignbuilder = new BaggingBuilder(new BaggingPrototype(), pbilRandom);
		builders.put(CommitteeType.BAGGING.getInfo(),baggignbuilder);
		
		RandomCommitteeBuilder rcbuilder = new RandomCommitteeBuilder(new RandomCommitteePrototype(), pbilRandom);
		builders.put(CommitteeType.RANDOM_COMMITTEE.getInfo(),rcbuilder);
		
		RandomForestBuilder rfbuilder = new RandomForestBuilder(new RandomForestPrototype(), pbilRandom);
		builders.put(CommitteeType.RANDOM_FOREST.getInfo(),rfbuilder);

		StackingBuilder stbuilder = new StackingBuilder(new StackingPrototype(), pbilRandom);
		builders.put(CommitteeType.STACKING.getInfo(),stbuilder);
		
		VoteBuilder votebuilder = new VoteBuilder(new VotePrototype(), pbilRandom);
		builders.put(CommitteeType.VOTE.getInfo(),votebuilder);
	}
	
	private void buildNames() {
		classifierNames = new ArrayList<String>();
		classifierNames.add(CommitteeType.ADA_BOOST.getInfo());
		classifierNames.add(CommitteeType.BAGGING.getInfo());
		classifierNames.add(CommitteeType.RANDOM_COMMITTEE.getInfo());
		classifierNames.add(CommitteeType.RANDOM_FOREST.getInfo());
		classifierNames.add(CommitteeType.STACKING.getInfo());
		classifierNames.add(CommitteeType.VOTE.getInfo());
	}
}
