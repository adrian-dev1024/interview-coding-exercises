package deck

import deck.Suit.Suit

/**
  * Created by adrian on 2/7/17.
  */

/**
  * Enum for Card Suit, Strongest Suit has the lowest value.
  * Using ascending alphabetical order.
  */
object Suit extends Enumeration {
  type Suit = Value
  val Spades, Hearts, Diamonds, Clubs = Value
}


/**
  * Class that represents a card
  *
  * @param value _
  * @param suit  _
  */
case class Card(value: Int, suit: Suit){
  override def toString: String = s"${value} of ${suit} "
}

object Card {

  def charToSuit(c: Char): Suit = {
    c match {
      case 'S' => Suit.Spades
      case 'H' => Suit.Hearts
      case 'D' => Suit.Diamonds
      case 'C' => Suit.Clubs
      case _ => throw new Exception("Invalid Suit " + c)
    }
  }

}
