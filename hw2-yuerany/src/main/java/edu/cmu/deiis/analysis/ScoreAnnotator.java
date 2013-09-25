package edu.cmu.deiis.analysis;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

/**
 * Creates answerScore annotations by scoring answers.
 * The class is simply an adapter so that TestElementScoringProcessor could
 * be used in the UIMA pipeline (because UIMA annotators cannot inherit from abstract classes)
 * See that class for implementation details
 * @author yueran
 *
 */
public class ScoreAnnotator extends JCasAnnotator_ImplBase {
  
  private TestElementScoringProcessor scoringProcessor = new TestElementScoringProcessor();
  
  // based off of official uima tutorial.  RoomNumberAnnotator
  public void process(JCas aJCas) {
    scoringProcessor.process(aJCas);
  }
}