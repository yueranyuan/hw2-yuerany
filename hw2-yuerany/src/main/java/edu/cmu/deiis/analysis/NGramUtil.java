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
    FSArray tokensA = gramA.getElements();
    FSArray tokensB = gramB.getElements(); // TODO: note that this is a design choice to make things clearer
    int len = tokensA.size();
    if (len != tokensB.size()) {
      return false; // short cut if the lengths don't match.  Tokens can't be equal
    }
    for (int i = 0; i < len; i++) {
      Token tokenA = (Token)tokensA.get(i);
      Token tokenB = (Token)tokensB.get(i);
      if (! tokenA.getCoveredText().equals(tokenB.getCoveredText())) {
        return false;
      }
    }
    return true;
  }
}
