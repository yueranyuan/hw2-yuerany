package edu.cmu.deiis.analysis;

import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Token;

/**
 * Factory that creates Token Annotations and adds it to the Index
 * @author yueran
 *
 */
public class TokenAnnotationFactory extends AbstractAnnotationFactory {

  public TokenAnnotationFactory(String annotatorName) {
    super(annotatorName, true);
  }
  
  public void Annotate(JCas aJCas, int begin, int end) {
    Token token = new Token(aJCas);
    AnnotationHelper(token, begin, end);
  }
}
