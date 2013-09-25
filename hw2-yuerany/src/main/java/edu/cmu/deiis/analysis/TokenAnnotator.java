package edu.cmu.deiis.analysis;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

/**
 * Adds token annotations to the CAS.
 * The class is simply an adapter so that TestElementTokenizeProcessor could
 * be used in the UIMA pipeline (because UIMA annotators cannot inherit from abstract classes)
 * See that class for implementation details
 * @author yueran
 *
 */
public class TokenAnnotator extends JCasAnnotator_ImplBase {
  
  private TestElementTokenizeProcessor tokenizeProcessor = new TestElementTokenizeProcessor();
  
  // based off of official uima tutorial.  RoomNumberAnnotator
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    tokenizeProcessor.process(aJCas);
  }
}