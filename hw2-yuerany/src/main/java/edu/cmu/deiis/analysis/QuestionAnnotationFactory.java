package edu.cmu.deiis.analysis;

import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Question;

/**
 * Factory that creates Question Annotations and adds it to the Index
 * @author yueran
 *
 */
public class QuestionAnnotationFactory extends AbstractAnnotationFactory {

  public QuestionAnnotationFactory(String annotatorName) {
    super(annotatorName, true);
  }
  
  public void Annotate(JCas aJCas, int begin, int end) {
    Question question = new Question(aJCas);
    AnnotationHelper(question, begin, end);
  }
}
