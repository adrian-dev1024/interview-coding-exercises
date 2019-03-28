package game

import java.io.File

import pokerHand.{Move, PokerHand}
import net.liftweb.json._

import scala.io.Source

/**
  * Created by adrian on 2/5/17.
  */

/**
  * Wrapper class for json object:
  *
  * {"hand":{"cards" : [<card>, ...]}}
  *
  * @param cards _
  */
case class JsonHand(cards: List[String])

/**
  * object that runs the main program
  */
object PokerGame {

  implicit val formats = DefaultFormats

  val usage = "Usage: pokerGame filename"


  /**
    * JSON file should contain the following json object
    */
  val JSON_OBJECT_FORMAT =
    """
                              { "hands":[
                                    {"hand": {"cards" : [<card>, ...]} // at least 5 cards
                                    }, ...
                                  ]
                              }
                           """

  def readInHands(fileName: String): Array[JsonHand] = {
    if (new File(fileName).exists()) {
      val jsonString: String = {
        for (line <- Source.fromFile(fileName).getLines()) yield line
      }.mkString
      val parsedJson = parse(jsonString)
      val elements = (parsedJson \\ "hand").children
      val hands = for (jsonBlock <- elements) yield (jsonBlock.extract[JsonHand])
      hands.toArray
    } else {
      throw new Exception(String.format("File %s does not exist", fileName))
    }
  }

  def combination(n: Int, list: List[String]): List[List[String]] = {
    list match {
      case Nil => Nil
      case head :: xs =>
        if (n <= 0 || n > list.length) {
          Nil
        } else if (n == 1) {
          list.map(List(_))
        } else {
          combination(n - 1, xs).map(head :: _) ::: combination(n, xs)
        }
    }
  }

  def createPokerHand(jsonHand: JsonHand): Array[PokerHand] = {
    if (jsonHand.cards.length < 5) throw new Exception(s"Player ${jsonHand.cards} needs at least 5 cards")
    else if (jsonHand.cards.length > 5) {
      {
        for (hand <- combination(5, jsonHand.cards)) yield new PokerHand(hand.toArray)
      }.toArray
    } else {
      Array(new PokerHand(jsonHand.cards.toArray))
    }
  }

  def winningHand(pokerHands: Array[PokerHand]): PokerHand = {
    if (pokerHands.isEmpty) {
      throw new Exception("No hands!")
    } else if (pokerHands.length == 1) pokerHands.head
    else {
      // best hand has the lowest number
      val sortedSorces = pokerHands.sortWith(_.bestMove.moveRank < _.bestMove.moveRank)
      // checking for ties
      val duplicates = sortedSorces.groupBy(_.bestMove.moveRank) filter { case (_, lst) => lst.size > 1 }
      // all ranks are different
      if (duplicates.keys.isEmpty) sortedSorces.head
      else {
        // not a tie for first
        if (!duplicates.keySet.contains(sortedSorces.head.bestMove.moveRank)) sortedSorces.head
        else {
          //todo: need to add logic for to breaking ties
          println("There was a tie for first")
          sortedSorces.head
        }
      }
    }

  }

  def main(args: Array[String]): Unit = {
    if (args.length != 1) println(usage)
    else {
      val fileName = args(0)
      val jsonHands = readInHands(fileName)
      val pokerHands: Array[PokerHand] = {
        for (hand <- jsonHands) yield createPokerHand(hand)
      }.flatten
      val winningMove = winningHand(pokerHands).bestMove
      println(s"The winning hand is a ${winningMove}")
    }
  }

}
