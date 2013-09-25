package edu.cmu.deiis.analysis;

import org.apache.uima.jcas.cas.FSArray;

import edu.cmu.deiis.types.NGram;
import edu.cmu.deiis.types.Token;

/**
 * Holds functions which are useful for processing NGrams.  e.g. comparator
 * @author yueran
 *
 */
public class NGramUtil {
  /**
   * Compares that the string values of the tokens of the two NGrams are equal
   * @param gramA       NGram to be compared
   * @param gramB       Other NGram to be compared
   * @return            True of the NGrams are equal
   */
  public static boolean Compare(NGram gramA, NGram gramB) {
    if (Similarity(gramA, gramB) == 1) {
      return true;
    }
    return false;
  }

  /**
   * Computes a similarity value between the two NGrams
   * The similarity is the number of overlaps in each ngram position divided
   * by the number of grams in the longest NGram
   * Similarity is 1 for 2 identical NGrams and 0 for 2 completely different NGrams
   * @param gramA     NGram to be compared
   * @param gramB     Other NGram to be compared
   * @return          Similarity value
   */
  public static double Similarity(NGram gramA, NGram gramB) {
    FSArray tokensA = gramA.getElements();
    FSArray tokensB = gramB.getElements();
    int len = Math.min(tokensA.size(), tokensB.size());
    int overlap = 0;
    for (int i = 0; i < len; i++) {
      Token tokenA = (Token)tokensA.get(i);
      Token tokenB = (Token)tokensB.get(i);
      if (tokenA.getCoveredText().equals(tokenB.getCoveredText())) {
        overlap += 1.0;
      }
    }
    double similarity = ((double) overlap) / Math.max(tokensA.size(), tokensB.size());
    return similarity;
  }
}
