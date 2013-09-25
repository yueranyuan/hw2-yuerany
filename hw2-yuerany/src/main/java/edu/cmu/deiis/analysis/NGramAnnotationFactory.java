package edu.cmu.deiis.analysis;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;

import edu.cmu.deiis.types.NGram;
import edu.cmu.deiis.types.Token;

/**
 * Factory that creates NGram Annotations and adds it to the Index
 * @author yueran
 *
 */
public class NGramAnnotationFactory extends AbstractAnnotationFactory {

  public NGramAnnotationFactory(String annotatorName) {
    super(annotatorName, true);
  }
  
  /**
   * Create an NGram annotation.  The NGram annotation begins at the beginning of the first token
   * and ends at the end of the final token.
   * @param aJCas     The CAS that the annotation will be added to
   * @param tokens    A list of Token Annotations which the NGram consists of
   *                  The Tokens should be in sorted order
   */
  public void Annotate(JCas aJCas, List<Token> tokens) {
    NGram ngram = new NGram(aJCas);
    
    // annotate tokens
    int len = tokens.size();
    FSArray tokenArray = new FSArray(aJCas, len);
    for (int i = 0; i < len; i++) {
      tokenArray.set(i, tokens.get(i));
    }
    ngram.setElements(tokenArray);
    // annotate from the beginning of the first token to the end of the final token
    AnnotationHelper(ngram, tokens.get(0).getBegin(), tokens.get(len - 1).getEnd());
  }
}
