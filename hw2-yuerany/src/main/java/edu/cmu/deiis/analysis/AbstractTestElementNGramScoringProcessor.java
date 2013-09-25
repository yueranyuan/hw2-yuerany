package edu.cmu.deiis.analysis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Annotation;
import edu.cmu.deiis.types.NGram;

/**
 * This processor generates AnswerScore annotations for each Answer based on an Ngram score
 * @author yueran
 *
 */
public abstract class AbstractTestElementNGramScoringProcessor extends AbstractTestElementScoringProcessor {
  
  List<NGram> questionNGrams;
  
  /**
   * Computes a score of how similar a list of ngram L is to a reference list of ngrams
   * The algorithm is 
   * # of Grams_in_L_in_reference / # of Grams_in_L
   * @param referenceGrams    The list of NGrams to be matched against
   * @param myGrams           The list of NGrams we want to evaluate
   * @return                  Score according to algorithm
   */
  private double scoreWithNGrams(List<NGram> referenceGrams, List<NGram> myGrams) {
    // handle the edge case of an empty answer
    if (myGrams.size() == 0) {
      return 0.0; // an empty answer is definitely not correct for any reasonable question
    }
    // handle the general case
    Iterator<NGram> myGramIter = myGrams.iterator();
    double scoreCounter = 0.0;
    while (myGramIter.hasNext()) {
      NGram myGram = myGramIter.next();
      Iterator<NGram> referenceGramIter = referenceGrams.iterator();
      while (referenceGramIter.hasNext()) {
        NGram referenceGram = referenceGramIter.next();
        scoreCounter += generateNGramWeight(referenceGram, myGram);
      }
    }
    return scoreCounter / (referenceGrams.size() * myGrams.size());
  }
  
  /**
   * How the NGrams are weighed.  This is how different NGram scorers differ from each other
   * @param referenceGram   Gram to be compared against
   * @param myGram          Gram to compare
   * @param allGrams        Other Grams that will be compared
   * @return                Weight of gram to compare
   */
  protected abstract double generateNGramWeight(NGram referenceGram, NGram myGram);
  
  /**
   * gets a list of NGrams inside the span of a text annotation
   * @param textAnnotation      the text to look for NGrams in
   * @param aJCas               UIMA Cas that contains the NGrams
   * @return                    list of NGrams inside the text annotation
   */
  @SuppressWarnings("unchecked")
  //we are feeding in the proper type so casting should not be an issue
  private List<NGram> getNGrams(Annotation textAnnotation, JCas aJCas) {
    int annotationBegin = textAnnotation.getBegin();
    int annotationEnd = textAnnotation.getEnd();
    Iterator<NGram> ngramIter = (Iterator<NGram>) getAnnotationsFromIndex(aJCas, NGram.type);
    // Linked List used because we will be converting to an iterator
    List<NGram> relevantNGrams = new LinkedList<NGram>();
    while (ngramIter.hasNext()) {
      NGram ngram = (NGram)ngramIter.next();
      // check that the token is for this sentence
      if (ngram.getBegin() >= annotationBegin && ngram.getEnd() <= annotationEnd) {
        relevantNGrams.add(ngram);
      }
    }
    return relevantNGrams;
  }

  @Override
  protected void processQuestion(Question question, JCas aJCas) {
    questionNGrams = getNGrams(question, aJCas);
  }

  /**
   * Gets NGrams for each answer and scores the answer according to NGram
   * match with the question
   */
  @Override
  // assumes that questionNgrams already exists
  protected void processAnswer(Answer answer, JCas aJCas) {
    List<NGram> answerNGrams = getNGrams(answer, aJCas);
    double ngramScore = scoreWithNGrams(questionNGrams, answerNGrams);
    // TODO: develop more sensible confidence score
    answerScoreAnnotationFactory.Annotate(aJCas, answer, ngramScore, 1.0);
  }
}