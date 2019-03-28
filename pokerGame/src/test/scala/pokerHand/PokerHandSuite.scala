package pokerHand

/**
  * Created by adrian on 2/5/17.
  */

@RunWith(classOf[JUnitRunner])
class PokerHandSuite extends FunSuite{

  test("Test areSameSuit"){
    val notSameSuit = new PokerHand(Array("JH", "4C", "4S", "JC", "9H"))
    assertResult(false)(notSameSuit.areSameSuit)
    val sameSuit = new PokerHand(Array("JH", "4H", "4H", "JH", "9H"))
    assertResult(true)(sameSuit.areSameSuit)
  }

  test("Test isAStraight"){
    val notAStraight = new PokerHand(Array("JH", "4C", "4S", "JC", "9H"))
    assertResult(false)(notAStraight.isAStraight)
    val aStraight = new PokerHand(Array("10H", "JH", "QH", "KH", "AH"))
    assertResult(true)(aStraight.isAStraight)
  }

  test("Test similarCards"){
    val twoPair = new PokerHand(Array("JH", "4C", "4S", "JC", "9H"))
    assertResult(2)(twoPair.similarCards.length)
    assertResult(2)(twoPair.similarCards(0).length)
    assertResult(2)(twoPair.similarCards(1).length)
    val aceHigh = new PokerHand(Array("10D", "JS", "QH", "KC", "AH"))
    assertResult(0)(aceHigh.similarCards.length)
    val fourOfAKind = new PokerHand(Array("4H", "4C", "4S", "4D", "9H"))
    assertResult(1)(fourOfAKind.similarCards.length)
    assertResult(4)(fourOfAKind.similarCards(0).length)
    val threeOfAKind = new PokerHand(Array("4H", "4C", "4S", "5D", "9H"))
    assertResult(1)(threeOfAKind.similarCards.length)
    assertResult(3)(threeOfAKind.similarCards(0).length)
    val onePair = new PokerHand(Array("4H", "4C", "6S", "5D", "9H"))
    assertResult(1)(onePair.similarCards.length)
    assertResult(2)(onePair.similarCards(0).length)
  }

  test("Test bestHand"){
    val royalFush = new PokerHand(Array("10H", "JH", "QH", "KH", "AH"))
    val royalFushMove =  royalFush.bestMove
    assertResult(PokerMoves.RoyalFlush)(royalFushMove.moveRank)

    val straightFlush = new PokerHand(Array("9H", "10H", "JH", "QH", "KH"))
    val straightFlushMove = straightFlush.bestMove
    assertResult(PokerMoves.StraightFlush)(straightFlushMove.moveRank)

    val fourOfAKind = new PokerHand(Array("4H", "4C", "4S", "4D", "9H"))
    val fourOfAKindMove = fourOfAKind.bestMove
    assertResult(PokerMoves.FourOfAKind)(fourOfAKindMove.moveRank)

    val fullHouse = new PokerHand(Array("JH", "4C", "4S", "JC", "4H"))
    val fullHouseMove = fullHouse.bestMove
    assertResult(PokerMoves.FullHouse)(fullHouseMove.moveRank)

    val flush = new PokerHand(Array("9H", "10H", "6H", "QH", "2H"))
    val flushMove = flush.bestMove
    assertResult(PokerMoves.Flush)(flushMove.moveRank)

    val straight = new PokerHand(Array("9S", "10H", "6C", "8H", "7D"))
    val straightMove = straight.bestMove
    assertResult(PokerMoves.Straight)(straightMove.moveRank)

    val threeOfAKind = new PokerHand(Array("3H", "3C", "3S", "4D", "9H"))
    val threeOfAKindMove = threeOfAKind.bestMove
    assertResult(PokerMoves.ThreeOfAKind)(threeOfAKindMove.moveRank)

    val twoPair = new PokerHand(Array("JH", "4C", "4S", "JC", "9H"))
    val twoPairMove = twoPair.bestMove
    assertResult(PokerMoves.TwoPair)(twoPairMove.moveRank)

    val onePair = new PokerHand(Array("JH", "4C", "5S", "JC", "9H"))
    val onePairMove = onePair.bestMove
    assertResult(PokerMoves.OnePair)(onePairMove.moveRank)

    val aceHigh = new PokerHand(Array("10D", "JS", "8H", "KC", "AH"))
    val aceHighMove = aceHigh.bestMove
    assertResult(PokerMoves.HighCard)(aceHighMove.moveRank)

  }

}
