package edu.cmu.deiis.analysis;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

/**
 * Creates NGrams annotations based on the token annotations for each sentence.
 * The class is simply an adapter so that TestElementNGramProcessor could
 * be used in the UIMA pipeline (because UIMA annotators cannot inherit from abstract classes)
 * See that class for implementation details
 * @author yueran
 *
 */
public class NGramAnnotator extends JCasAnnotator_ImplBase {
  private TestElementNGramProcessor ngramProcessor = new TestElementNGramProcessor();
  
  // based off of official uima tutorial.  RoomNumberAnnotator
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    ngramProcessor.process(aJCas);
  }
}