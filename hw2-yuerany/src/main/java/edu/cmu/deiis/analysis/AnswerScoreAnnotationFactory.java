package edu.cmu.deiis.analysis;

import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.Answer;

/**
 * Factory that creates AnswerScore Annotations and adds it to the Index
 * @author yueran
 *
 */
public class AnswerScoreAnnotationFactory extends AbstractAnnotationFactory {

  public AnswerScoreAnnotationFactory(String annotatorName) {
    super(annotatorName, false);
  }
  
  public void Annotate(JCas aJCas, Answer answer, double score, double confidence) {
    AnswerScore answerScore = new AnswerScore(aJCas);
    answerScore.setScore(score);
    answerScore.setAnswer(answer);
    answerScore.setConfidence(confidence);
    AnnotationHelper(answerScore, answer.getBegin(), answer.getEnd());
  }
}
