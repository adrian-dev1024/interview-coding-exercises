package pokerHand

import deck.Card
import deck.Suit.Suit
import pokerHand.PokerMoves.PokerMoves

/**
  * Created by adrian on 2/5/17.
  */

/**
  * Enum for Poker Hand Strength Lower the value the Stronger the Hand
  */
object PokerMoves extends Enumeration {
  type PokerMoves = Value
  val RoyalFlush, StraightFlush, FourOfAKind, FullHouse, Flush, Straight, ThreeOfAKind, TwoPair, OnePair, HighCard = Value
}

/**
  * Class that represents the a Poker Move.
  *
  * @param moveRank Name and Hand Strength
  * @param play     cards in play
  * @param kickers  kicker cards
  */
case class Move(moveRank: PokerMoves, play: Array[Card], kickers: Array[Card]){
  override def toString: String = {
    val rankAndPlay = s"${moveRank}, cards in play ${play.mkString(", ")}"
    if (kickers == null || kickers.isEmpty) rankAndPlay
    else s"${rankAndPlay} with kickers ${kickers.mkString(" ,")}"
  }
}

/**
  * Class that represents a Poker Hand.
  *
  * @param cardStrings  _
  */
class PokerHand(cardStrings: Array[String]) {

  val cards = for (cardStr <- cardStrings) yield cardBuilder(cardStr)

  lazy val getValues = for (card <- cards) yield card.value
  lazy val getSuits = for (card <- cards) yield card.suit
  lazy val areSameSuit = {
    val suits = getSuits

    // checking that all the other suits match the first card
    def loop(suit: Suit, index: Int): Boolean = {
      if (suits.length == index) true
      // exit it on first miss-match
      else if (suits(index) == suit) loop(suit, index + 1)
      else false
    }

    loop(suits(0), 1)
  }
  lazy val isAStraight = {
    val orderedVals = getValues.sortWith(_ > _)

    def loop(index: Int): Boolean = {
      if (orderedVals.length == index) true
      //checking that the current val and the previous are different by 1
      //positive bc values are sorted in reverse order
      else if (orderedVals(index - 1) - orderedVals(index) == 1) loop(index + 1)
      else false
    }

    loop(1)
  }
  // returns an a 2d-array of cards with the same value
  // ex. "JH", "4C", "4S", "JC", "9H" => {{"JH", "JC"}, {"4C", "4S"}}
  lazy val similarCards = {
    cards.groupBy(_.value) filter { case (_, lst) => lst.size > 1 } values
  }.toArray
  lazy val bestMove: Move = {
    val sortedCards = sortCards(cards)

    def getKickers(play: Array[Card]) = sortedCards.filter { case (card) => !play.contains(card) }

    if (areSameSuit && isAStraight) {
      // if the Straight contains an ace then it's a Royal Flush
      if (getValues.contains(14)) new Move(PokerMoves.RoyalFlush, sortedCards, Array())
      // every other one is is just a Straight Flush
      else new Move(PokerMoves.StraightFlush, sortedCards, null)
    } else if (areSameSuit) new Move(PokerMoves.Flush, sortedCards, null)
    else if (isAStraight) new Move(PokerMoves.Straight, sortedCards, null)
    else if (!similarCards.isEmpty) {
      // two sets of similar cards
      if (similarCards.length == 2) {
        if (similarCards(0).length + similarCards(1).length == 5) new Move(PokerMoves.FullHouse, sortedCards, null)
        else {
          val twoPair = similarCards(0) ++ similarCards(1)
          new Move(PokerMoves.TwoPair, twoPair, getKickers(twoPair))
        }
      } else {
        if (similarCards(0).length == 4) new Move(PokerMoves.FourOfAKind, similarCards(0), getKickers(similarCards(0)))
        else if (similarCards(0).length == 3) new Move(PokerMoves.ThreeOfAKind, similarCards(0), getKickers(similarCards(0)))
        else new Move(PokerMoves.OnePair, similarCards(0), getKickers(similarCards(0)))
      }
    } else {
      // todo: look for better way to do this
      val sortedCardsList = sortedCards.toList
      new Move(PokerMoves.HighCard, Array(sortedCardsList.head), sortedCardsList.tail.toArray)
    }
  }

  def sortCards(cards: Array[Card]): Array[Card] = cards.sortWith((l, r) => l.value > r.value)

  private def cardBuilder(cardStr: String): Card = {

    def valueConverted(char: Char) = {
      val value = char match {
        case 'A' => 14
        case 'K' => 13
        case 'Q' => 12
        case 'J' => 11
        case _ => char.asDigit
      }
      if (value < 2 || value > 14) {
        throw new Error("Card Value out of range: " + char)
      } else {
        value
      }
    }

    if (cardStr.length == 2) new Card(valueConverted(cardStr.charAt(0)), Card.charToSuit(cardStr.charAt(1)))
    else if (cardStr.length == 3 && cardStr.charAt(0) == '1' && cardStr.charAt(1) == '0') {
      new Card(10, Card.charToSuit(cardStr.charAt(2)))
    } else throw new Error("Invalid Card: " + cardStr)
  }
}
