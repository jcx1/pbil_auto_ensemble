package weka.classifiers.meta.pbilautoens.domain.comm.builders;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.meta.pbilautoens.Classifier;
import weka.classifiers.meta.pbilautoens.ClassifierBuilder;
import weka.classifiers.meta.pbilautoens.ClassifierPrototype;
import weka.classifiers.meta.pbilautoens.Committee;
import weka.classifiers.meta.pbilautoens.PbilRandom;
import weka.classifiers.meta.pbilautoens.domain.bc.BaseClassifierFactory;
import weka.classifiers.meta.pbilautoens.exception.InvalidParameterTypeException;

public abstract class CommitteeBuilder extends ClassifierBuilder{
	
	public Committee committee;
	
	public CommitteeBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}
	
	// build a list of classifier with size equals to the number of classifiers each committee need
	protected List<Classifier> buildClassifiers(int numberOfClassifiers) throws InvalidParameterTypeException {
		
		List<Classifier> classifiers = new ArrayList<Classifier>();
		BaseClassifierFactory factory = new BaseClassifierFactory(pbilRandom);
		
		for (int i = 0; i < numberOfClassifiers; i++) {	
			int indexOfClassifier = pbilRandom.nextInt(9);
			String nameOfClassifier = factory.getClassifierNames().get(indexOfClassifier);
			
			Classifier solucao = factory.buildClassifierRandomly(nameOfClassifier);
			classifiers.add(solucao);
		}
		return classifiers;
	}
	
}
