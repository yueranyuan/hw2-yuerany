package edu.cmu.deiis.analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Token;
import edu.cmu.deiis.types.Annotation;

/**
 * This Processor generates NGram Annotations for each sentence using Token annotations.
 * Specifically the processor maintains no upper-bound on the length of the NGrams
 * @author yueran
 *
 */
public class TestElementNGramProcessor extends AbstractTestElementProcessor {
  
  private static NGramAnnotationFactory ngramAnnotationFactory = 
          new NGramAnnotationFactory("NGramAnnotator");
  
  /**
   * annotates a text annotation with NGrams.  The NGrams are computed from
   * Token annotations which are within the span of the input text annotation
   * @param textAnnotation  the annotation span over which to generate NGrams
   * @param aJCas           JCas object for UIMA
   */
  @SuppressWarnings("unchecked")
  //we are feeding in the proper type so casting should not be an issue
  private void annotateWithNGrams(Annotation textAnnotation, JCas aJCas) {
    
    int annotationBegin = textAnnotation.getBegin();
    int annotationEnd = textAnnotation.getEnd();
    Iterator<Token> tokenIter = (Iterator<Token>) getAnnotationsFromIndex(aJCas, Token.type);
    List<Token> tokensSoFar = new ArrayList<Token>(); // keep a running list of tokens
    while (tokenIter.hasNext()) {
      Token token = (Token)tokenIter.next();
      // check that the token is for this sentence
      if (token.getBegin() >= annotationBegin && token.getEnd() <= annotationEnd) {
        // build a list of tokens, taking advantage of the fact that the tokens are arranged
        // in sorted order
        tokensSoFar.add(token);
        int end = tokensSoFar.size() - 1;
        // for each new token we add, one NGram is generated for each previous span
        for (int start = 0; start <= end; start++) {
          ngramAnnotationFactory.Annotate(aJCas, tokensSoFar.subList(start, end + 1));
        }
      }
    }
  }

  @Override
  protected void processQuestion(Question question, JCas aJCas) {
    annotateWithNGrams(question, aJCas);
  }

  @Override
  protected void processAnswer(Answer answer, JCas aJCas) {
    annotateWithNGrams(answer, aJCas);
  }
}