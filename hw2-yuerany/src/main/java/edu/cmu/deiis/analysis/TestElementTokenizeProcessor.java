package edu.cmu.deiis.analysis;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import edu.stanford.nlp.process.Tokenizer;

import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Annotation;

/**
 * This Processor creates token annotations for the sentences.  The tokenization is done using
 * the Stanford NLP Tokenizer.  Punctuation are tagged as tokens.
 * @author yueran
 *
 */
public class TestElementTokenizeProcessor extends AbstractTestElementProcessor {
  
  public static String AnnotatorName = "TokenAnnotator";
  private static TokenAnnotationFactory tokenAnnotationFactory = 
          new TokenAnnotationFactory(AnnotatorName);
  private String[] stopWords = {"?",".",";",","}; // a fairly simple way of throwing away punctuation
  
  protected void processQuestion(Question question, JCas aJCas) {
    annotateWithTokens(question, aJCas);
  }
  
  protected void processAnswer(Answer answer, JCas aJCas) {
    annotateWithTokens(answer, aJCas);
  }
  
  /**
   * Create tokens using the Stanford NLP Tokenizer.  Punctuations are skipped.
   * @param textAnnotation      The text to be tokenized
   * @param aJCas               UIMA Cas system used in the pipeline
   */
  private void annotateWithTokens(Annotation textAnnotation, JCas aJCas) {
    TokenizerFactory<Word> factory = PTBTokenizerFactory.newTokenizerFactory(); // TODO: initialize
    Tokenizer<Word> tokenizer = factory.getTokenizer(new StringReader(textAnnotation.getCoveredText()));
    List<Word> tokens = tokenizer.tokenize();
    Iterator<Word> tokIter = tokens.iterator();
    int annotationBegin = textAnnotation.getBegin();
    outerloop:
    while (tokIter.hasNext()) {
      Word word = tokIter.next();
      // ignore stop words
      for (int i = 0; i < stopWords.length; i++) {
        if (word.value().equals(stopWords[i])) {
          continue outerloop;
        }
      }
      tokenAnnotationFactory.Annotate(aJCas,
              annotationBegin + word.beginPosition(),
              annotationBegin + word.endPosition());
    }
  }
}