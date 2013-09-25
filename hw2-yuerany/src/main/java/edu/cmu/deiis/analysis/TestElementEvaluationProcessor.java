package edu.cmu.deiis.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.AnswerScore;

/**
 * This processor evaluates how well the answers were scored using the scores from
 * the AnswerScore annotations.  The answers are ranked by their score and the best N
 * (where N is the number of true correct answers) is used to compute Precision@N.
 * Precision@N = correct_answers_in_top_N / N
 * 
 * This processor does not modify or create any CAS but the
 * details of the evaluation is written to the console to be viewed.
 * 
 * Example console output:
 * 
 * Question: "Does John love Mary?"
 * + 0.8 "John loves Mary"
 * - 0.5 "Mary loves John"
 * + 0.2 "John totally loves Mary"
 * Precision: 0.5
 * @author yueran
 *
 */
public class TestElementEvaluationProcessor extends AbstractTestElementProcessor {
  
  /**
   * This method overrides the process method of the AbstractTestElementProcessor because
   * processing is done on AnswerScore annotations rather than Answer annotations
   * 
   * The method can be broken down into 3 steps
   * 1: sort the answers according to score
   * 2: print the test element (question and answers)
   * 3: compute and print precision@N
   * 
   * Example console output:
   * 
   * Question: "Does John love Mary?"
   * + 0.8 "John loves Mary"
   * - 0.5 "Mary loves John"
   * + 0.2 "John totally loves Mary"
   * Precision: 0.5
   * @param aJCas         UIMA Cas that the question, answers, and scores are read from
   */
  @SuppressWarnings("unchecked")
  //we are feeding in the proper type so casting should not be an issue
  @Override
  public void process(JCas aJCas) {
    
    // STEP 1: sort the answers by score
    Iterator<AnswerScore> answerScoreIter = 
            (Iterator<AnswerScore>) getAnnotationsFromIndex(aJCas, AnswerScore.type);
    List<AnswerScore> answerScoreList = new ArrayList<AnswerScore>();
    while (answerScoreIter.hasNext()) {
      answerScoreList.add(answerScoreIter.next());
    }
    // the following few lines to sort the AnswerScore by score are inspired by
    // http://stackoverflow.com/questions/5805602/how-to-sort-list-of-objects-by-some-property
    Collections.sort(answerScoreList, new Comparator<AnswerScore>() {
      public int compare(AnswerScore a1, AnswerScore a2) {
          return Double.compare(a2.getScore(), a1.getScore());
      }
    });
    
    // STEP 2: print test element
    // STEP 2.1: print question
    Iterator<Question> questionIter = (Iterator<Question>) getAnnotationsFromIndex(aJCas,
            Question.type);
    // there should only be one question
    if (questionIter.hasNext()) {
      Question question = (Question)questionIter.next();
      System.out.println("Question: " + question.getCoveredText());
    } else {
      // TODO: output error, no question
    }
    // STEP 2.2:  print the answers.  Count the correct ones while we're at it.
    int len = answerScoreList.size();
    int N = 0;
    for (int i = 0; i < len; i ++) {
      AnswerScore answerScore = answerScoreList.get(i);
      boolean correct = false;
      if (answerScore.getAnswer().getIsCorrect()) {
        correct = true;
        N ++;
      }
      String correctMarker = correct ? "+" : "-";
      System.out.println(correctMarker + " " + Double.toString(answerScore.getScore()) +
              " " + answerScore.getCoveredText());
    }
    
    // STEP 3: compute and print precision
    int correctAtN = 0;
    for (int i = 0; i < N; i ++) {
      AnswerScore answerScore = answerScoreList.get(i);
      if (answerScore.getAnswer().getIsCorrect()) {
        correctAtN ++;
      }
    }
    System.out.println("Precision at " + N + ": " + ((double)correctAtN/N)); // TODO: what if N was 0, throw exception
  }
}