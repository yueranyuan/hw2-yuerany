package edu.cmu.deiis.analysis;

import edu.cmu.deiis.types.NGram;

public class FuzzyTestElementNGramScoringProcessor extends AbstractTestElementNGramScoringProcessor {

  /**
   * NGram scorer weighted by similiarity of compared NGrams
   */
  @Override
  protected double generateNGramWeight(NGram referenceGram, NGram myGram) {
    return NGramUtil.Similarity(referenceGram, myGram);
  }
}
