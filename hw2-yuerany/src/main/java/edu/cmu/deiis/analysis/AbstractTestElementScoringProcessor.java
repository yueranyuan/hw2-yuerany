package edu.cmu.deiis.analysis;

/**
 * Abstract processor that to be used to generate answerScore annotations based
 * on some algorithm
 * @author yueran
 *
 */
public abstract class AbstractTestElementScoringProcessor extends AbstractTestElementProcessor {
  public static String AnnotatorName = "ScoreAnnotator";
  protected static AnswerScoreAnnotationFactory answerScoreAnnotationFactory = 
          new AnswerScoreAnnotationFactory(AnnotatorName);

}
