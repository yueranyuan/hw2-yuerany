package edu.cmu.deiis.analysis;

import edu.cmu.deiis.types.NGram;

public class BasicTestElementNGramScoringProcessor extends AbstractTestElementNGramScoringProcessor {

  /**
   * Evenly weighed NGram scorer
   */
  @Override
  protected double generateNGramWeight(NGram referenceGram, NGram myGram) {
    if (NGramUtil.Compare(referenceGram, myGram)) {
      return 1.0;
    } else {
      return 0.0;
    }
  }
}
