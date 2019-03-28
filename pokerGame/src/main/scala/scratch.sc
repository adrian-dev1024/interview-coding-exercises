//import pokerHand.{PokerHand, PokerMoves}

import net.liftweb.json.DefaultFormats
import net.liftweb.json._

val l = List(Set("a", "b", "c"), Set("a", "b", "c"))

val s = l.toSet

val d = List(1, 2, 3, 4)

def combination(n: Int, list: List[Int]): List[List[Int]]  = {
  list match {
    case Nil => Nil
    case head :: xs =>
      if (n <= 0 || n > list.length){
        Nil
      }else if (n == 1){
        list.map(List(_))
      }else{
        val l1 = combination(n-1,xs).map(head :: _)
        val l2 = combination(n, xs)
        val l3 = l1 ::: l2
        println(l1)
        println(l2)
        println(l3)
        l3
        //combination(n-1,xs).map(head :: _) ::: combination(n, xs)
      }
  }
}

val c = combination(3, d).length

//val royalFush = new PokerHand(Array("10H", "JH", "QH", "KH", "AH"))
//val straightFlush = new PokerHand(Array("9H", "10H", "JH", "QH", "KH"))
//val aceHigh = new PokerHand(Array("10D", "JS", "8H", "KC", "AH"))
//
//royalFush.bestMove
//straightFlush.bestMove
//val hands = Array(straightFlush, aceHigh, royalFush)
//hands.sortWith(_.bestMove.moveRank < _.bestMove.moveRank)
//
//PokerMoves.Straight > PokerMoves.Flush
//PokerMoves.Flush.toString


val json =
  """
    |{ "hands" : [
    |    {"hand" : {"cards" : ["JH", "4C", "4S", "JC", "9H"]}},
    |    {"hand" : {"cards" : ["KD", "6S", "9D", "6C", "5D"]}}
    |  ]
    |}
  """.stripMargin

implicit val formats = DefaultFormats
val parsedJson = parse(json)

case class JHand(cards: List[String])

val elements = (parsedJson \\ "hand").children

val players = for(jsonBlock  <- elements) yield (jsonBlock.extract[JHand])



//val hList = parsedJson match {
//  case Some(m: Map[String, Any]) => m("players") match {
//    case l : List[Map[String, Any]] => l
//  }
//}
//val harray = hList.map(lst => lst.toArray).toArray
//
//
//val l1 = List("10H", "JH", "QH", "KH", "AH").mkString