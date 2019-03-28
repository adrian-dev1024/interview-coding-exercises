package game

/**
  * Created by adrian on 2/5/17.
  */

@RunWith(classOf[JUnitRunner])
class PokerGameSuite extends FunSuite{

  def factorial(n: Int): Int = if(n == 0 ) 1 else n * factorial(n - 1)

  // total is (n!/(k!(n-k)!))
  def totalComb(n: Int, k: Int) = factorial(n) / (factorial(k) * factorial(n -k))

  test("Test combination pick 5 from 7"){
    val listOf7 = List("a", "b", "c", "d", "e", "f","h")
    val pick5 = PokerGame.combination(5, listOf7)
    val numOfCombs = totalComb(listOf7.length, 5)
    assertResult(numOfCombs)(pick5.length)
    // check that are no duplicates
    val set = pick5.map(l => Set(l)).toSet
    assertResult(numOfCombs)(set.size)
  }

  test("Test createPokerHand with 5 cards"){
    val cards5 = new JsonHand(List("JH", "4C", "4S", "JC", "9H"))
    val pokerHands = PokerGame.createPokerHand(cards5)
    assertResult(1)(pokerHands.length)
  }

  test("Test createPokerHand with 7 cards"){
    val cards7 = new JsonHand(List("JH", "4C", "4S", "JC", "9H", "AD", "7D"))
    val pokerHands = PokerGame.createPokerHand(cards7)
    assertResult(totalComb(cards7.cards.length, 5))(pokerHands.length)
  }

}
