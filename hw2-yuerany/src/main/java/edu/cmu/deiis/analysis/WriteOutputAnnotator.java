package edu.cmu.deiis.analysis;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

/**
 * Evaluates the test element and writes the evaluation results to console.
 * The class is simply an adapter so that TestElementEvaluationProcessor could
 * be used in the UIMA pipeline (because UIMA annotators cannot inherit from abstract classes)
 * See that class for implementation details
 * @author yueran
 *
 */
public class WriteOutputAnnotator extends JCasAnnotator_ImplBase {

  private TestElementEvaluationProcessor evaluationProcessor = new TestElementEvaluationProcessor();
  
  // based off of official uima tutorial.  RoomNumberAnnotator
  public void process(JCas aJCas) {
    evaluationProcessor.process(aJCas);
  }
}