package edu.cmu.deiis.analysis;

import java.util.Iterator;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Question;

/**
 * Abstract Processor that allows for structured processing of a test element i.e. a question
 * and some answers.  Processors could extend this class and override the processQuestion and/or the
 * processAnswer methods to input their own logic about what to do with those annotations.
 * @author yueran
 *
 */
public abstract class AbstractTestElementProcessor {
  
  /**
   * Process the question part of a test element
   * @param question      The Question
   * @param aJCas         UIMA Cas for the Pipeline
   */
  protected void processQuestion(Question question, JCas aJCas) {
    // default does nothing
  }

  /**
   * Process the answer part of a test element
   * @param answer        The Answer
   * @param aJCas         UIMA Cas for the Pipeline
   */
  protected void processAnswer(Answer answer, JCas aJCas) {
    // default does nothing
  }

  /**
   * Read the question and answer annotations from the CAS and call processor methods to perform
   * desired processing
   * @param aJCas         UIMA Cas to read annotations from and write annotations to
   * @throws AnalysisEngineProcessException 
   */
  @SuppressWarnings("unchecked")
  // we are feeding in the proper type so casting should not be an issue
  public void process(JCas aJCas) throws AnalysisEngineProcessException {

    // process question
    Iterator<Question> questionIter = (Iterator<Question>) getAnnotationsFromIndex(aJCas,
            Question.type);
    // there should only be one question
    if (questionIter.hasNext()) {
      Question question = questionIter.next();
      processQuestion(question, aJCas);
    } else {
      throw new AnalysisEngineProcessException();
    }

    // process answers
    Iterator<Answer> answerIter = (Iterator<Answer>) getAnnotationsFromIndex(aJCas, Answer.type);
    while (answerIter.hasNext()) {
      Answer answer = (Answer) answerIter.next();
      processAnswer(answer, aJCas);
    }
  }

  /**
   * Get iterator for a particular type of annotation.  
   * This function makes the class a bit of an adapter for dealing
   * with FSIndex and saves a lot of redundant code.
   * @param aJCas         The UIMA Cas to read the annotations from
   * @param type          The Annotation Type to read
   * @return
   */
  @SuppressWarnings("rawtypes") // we need to support the various aJCas types
  protected static Iterator getAnnotationsFromIndex(JCas aJCas, int type) {
    FSIndex annotationIndex = aJCas.getAnnotationIndex(type);
    return annotationIndex.iterator();
  }
}
