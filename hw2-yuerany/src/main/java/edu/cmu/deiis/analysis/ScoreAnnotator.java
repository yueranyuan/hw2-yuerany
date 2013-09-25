package edu.cmu.deiis.analysis;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * Creates answerScore annotations by scoring answers.
 * The class is simply an adapter so that we could plug in an AbstractTestElementScoringProcessor
 * for use in the UIMA pipeline (because UIMA annotators cannot inherit from abstract classes)
 * See that class for implementation details
 * 
 * different scoring algorithms could be selected by setting the ScoringAlgorithm parameter
 * The two available algorithms are "NGram basic" and "NGram fuzzy"
 * 
 * @author yueran
 *
 */
public class ScoreAnnotator extends JCasAnnotator_ImplBase {
  
  private AbstractTestElementScoringProcessor scoringProcessor;
  
  /**
   * read the scoring algorithm and initialize the specified scorer
   */
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    
    String scoringAlgorithm = (String) aContext.getConfigParameterValue("ScoringAlgorithm");
    if (scoringAlgorithm.equals("NGram basic")) {
      scoringProcessor = new BasicTestElementNGramScoringProcessor();
    } else if (scoringAlgorithm.equals("NGram fuzzy")) {
      scoringProcessor = new FuzzyTestElementNGramScoringProcessor();
    } else {
      throw new ResourceInitializationException();
    }
  }
  
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    scoringProcessor.process(aJCas);
  }
}