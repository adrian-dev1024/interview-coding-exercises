# Poker Game

## Description

A simple Scala program that reads in a json file containing Poker hands and returns the hand with the highest ranking based on the following list.

1. Royal Flush
2. Straight Flush
3. Four of a Kind
4. Full House
5. Flush
6. Straight
7. Three  of a Kind
8. Two Pair
9. One Pair
10. High Card

## Quick Start

With a Json file “hands.json” containing the following:
```json
{ "hands":[
  {"hand": {"cards" : ["10H", "JH", "QH", "KH", "AH"]}},
  {"hand": {"cards" : ["JH", "4C", "4S", "JC", "4H"]}}
]}
```

In a terminal go to the project’s directory:
```
$ cd /<Path to Project>/poker_game
```

Start SBT:
```
$ sbt
```

Compile and run:
```
$ compile
$ run /<Path to Json File>/hands.json
```

The expected output:

The winning hand is a RoyalFlush, cards in play 14 of Hearts, 13 of Hearts, 12 of Hearts, 11 of Hearts, 10 of Hearts.

## Problem statement

Poker is a game played with a standard 52-card deck of cards ( https://en.wikipedia.org/wiki/Standard_52-card_deck ), in which players attempt to make the best possible 5-card hand according to the ranking of the categories given at the following site:  http://www.pokerlistings.com/poker-hand-ranking. If you are unfamiliar with poker we recommend that you familiarize yourself with this list. The provided link also has a short video explaining how these hands work.


In this challenge, you may assume:

A single 52 card deck will be in use

No wild cards

Aces are treated as high cards only


Cards will be represented by their number or first letter for the non-numeric cards (J, Q, K, A) and the suits will be represented by their first letter (H, C, D, S) and stored as a JSON array. So for example a hand J♥ 4♣ 4♠ J♣ 9♥ will be represented as ["JH", "4C", "4S", "JC", "9H"] .


When a category involves less than 5 cards, the next highest cards are added as “kickers” for the sake of breaking ties.  For example, a pair of queens with a king beats a pair of queens with a 10.


1. Write a function that takes a 5-card hand as a JSON array and determines its category, with any tie breaking information that is necessary.  For example, the input  ["JH", "4C", "4S", "JC", "9H"] would have the value of two pair: jacks and 4s with a 9 kicker. You may choose your own representation for the output.


2. Write a function that takes 2 or more 5-card hands and determines the winner.


Some poker variations use more than 5 cards per player, and the player chooses the best subset of 5 cards to play.


3. Write a function that takes 5 or more cards and returns the best 5-card hand that can be made with those cards.  For example, the input [“3H”, “7S”, “3S”, “QD”, “AH”, “3D”, “4S”] should return [“3H”, “3S”, “3D”, “AH”, “QD”], which is a 3-of-a-kind with 3s, ace and queen kickers.

## Setup

Scala’s Simple Build Tool (SBT) is required to build and run this program.

### Installing SBT

One of the simplest ways to install SBT is using SDKMAN.

Run the following command to install SDKMAN:
```
$ curl -s “https://get.sdkman.io” | bash
```

Then follow the instruction printed out by the script.

Run the following command to install SBT:
```
$ sdk install sbt
```

Open a new terminal.

## Usage
In a terminal go to the project’s directory:
```
$ cd /<Path to Project>/poker_game
```

Start SBT:
```
$ sbt
```

Compile and run:
```
$ compile
$ run /<Path to Json File>/<Json File Name>
```

Note: Should only need to Compile before first run.

The expected format of the json object is:
```
{ "hands":[
  {"hand": {"cards" : [<card>, ...]}}, // at least 5 cards
  {"hand": {"cards" : [<card>, ...]}}, ...
]}
```

The hands array requires at least one hand and the cards array requires a minimum of five cards. The cards arrays can be of varying length

Note: Examples can found in the “/jsonExamples” directory.

### Choosing a Winner

Each hand gets assigned the best possible move it can make. The hand with the best rank is returned as the winner.  A list of all the moves and the ranking order is shown above.

TODO: Implement logic to determine the better hand when both hands have the same rank. (AKA a tie for first)
