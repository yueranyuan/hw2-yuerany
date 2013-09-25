package edu.cmu.deiis.analysis;

import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Answer;

/**
 * Factory that creates Answer Annotations and adds it to the Index
 * @author yueran
 *
 */
public class AnswerAnnotationFactory extends AbstractAnnotationFactory {

  public AnswerAnnotationFactory(String annotatorName) {
    super(annotatorName, true);
  }
  
  public void Annotate(JCas aJCas, int begin, int end, boolean isCorrect) {
    Answer answer = new Answer(aJCas);
    answer.setIsCorrect(isCorrect);
    AnnotationHelper(answer, begin, end);
  }
}
