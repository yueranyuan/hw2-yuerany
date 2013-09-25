package edu.cmu.deiis.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

/**
 * Annotator to generate question and answer annotations from a document
 * 
 * Expected format:
 * Q [question]
 * A [correctness] [answer1]
 * A [correctness] [answer2]
 * (...)
 * where [correctness] is 1 if correct and 2 if incorrect
 * 
 * Only 1 question is handled.  Any number of answers is okay.
 * @author yueran
 *
 */
public class TestElementAnnotator extends JCasAnnotator_ImplBase {
  
  private Pattern mQuestionPattern = Pattern.compile("Q ([^\r]+)");
  private Pattern mAnswerPattern = Pattern.compile("A (\\d) ([^\r]+)");
  
  public static String AnnotatorName = "TestElementAnnotator";
  private static AnswerAnnotationFactory answerAnnotationFactory = 
          new AnswerAnnotationFactory(AnnotatorName);
  private static QuestionAnnotationFactory questionAnnotationFactory = 
          new QuestionAnnotationFactory(AnnotatorName);
  
  /**
   * 
   * Reads a document from the CAS and creates annotations for the test element components - 
   * questions and answers.
   * 
   * Expected format:
   * Q [question]
   * A [correctness] [answer1]
   * A [correctness] [answer2]
   * (...)
   * where [correctness] is 1 if correct and 2 if incorrect
   * 
   * Only 1 question is handled.  Any number of answers is okay.
   */
  public void process(JCas aJCas) {
    // get document text
    String docText = aJCas.getDocumentText();
    Matcher matcher = mQuestionPattern.matcher(docText);
    // there is only one question, so match once
    if (matcher.find(0)) {
      questionAnnotationFactory.Annotate(aJCas, matcher.start(1), matcher.end(1));
    } else {
      // TODO: error question not found
    }
    // get answers
    matcher = mAnswerPattern.matcher(docText);
    int pos = 0;
    while (matcher.find(pos)) {
      answerAnnotationFactory.Annotate(aJCas, matcher.start(2), matcher.end(2),
              "1".equals(matcher.group(1)));
      pos = matcher.end();
    }
  }
}