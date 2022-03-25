import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class BlackjackTest {
    Blackjack blackjack = new Blackjack();

    @org.junit.jupiter.api.Test
    void draw() {
        Card card = blackjack.draw();
        Card card1 = blackjack.draw();
        Assertions.assertNotNull(card);
        Assertions.assertNotEquals(card1,card);
    }

    @org.junit.jupiter.api.Test
    void cardValue() {
        Card card = new Card(CardFace.Two, Suit.Diamonds);
        Assertions.assertEquals(2,blackjack.cardValue(card));
        Card card1 = new Card(CardFace.Three, Suit.Diamonds);
        Assertions.assertEquals(3,blackjack.cardValue(card1));
        Card card2 = new Card(CardFace.Six, Suit.Diamonds);
        Assertions.assertEquals(6,blackjack.cardValue(card2));
        Card card3 = new Card(CardFace.Seven, Suit.Diamonds);
        Assertions.assertEquals(7,blackjack.cardValue(card3));
        Card card4 = new Card(CardFace.Eight, Suit.Diamonds);
        Assertions.assertEquals(8,blackjack.cardValue(card4));
        Card card5 = new Card(CardFace.Nine, Suit.Diamonds);
        Assertions.assertEquals(9,blackjack.cardValue(card5));
        Card card6 = new Card(CardFace.Ten, Suit.Diamonds);
        Assertions.assertEquals(10,blackjack.cardValue(card6));
    }

    @org.junit.jupiter.api.Test
    void sumHand() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(CardFace.Jack, Suit.Diamonds));
        hand.add(new Card(CardFace.Ace, Suit.Diamonds));
        Integer sum = blackjack.sumHand(hand);
        Assertions.assertEquals(21, sum);
    }

    @org.junit.jupiter.api.Test
    void addToSum() {
        Card card = new Card(CardFace.Four, Suit.Diamonds);
        Integer sum = blackjack.addToSum(card, 10);
        Assertions.assertEquals(14, sum);
    }

    @org.junit.jupiter.api.Test
    void aceCheck() {
        Card card1 = new Card(CardFace.Five, Suit.Diamonds);
        Assertions.assertFalse(blackjack.aceCheck(card1));
        Assertions.assertEquals(5, blackjack.cardValue(card1));
        Card card = new Card(CardFace.Ace, Suit.Diamonds);
        Assertions.assertTrue(blackjack.aceCheck(card));
    }


    @org.junit.jupiter.api.Test
    void setBet() {
        BlackjackPlayer player = new BlackjackPlayer("name", 100);
        blackjack.setBet(player,100);
        Assertions.assertEquals(0, player.getBalance());
    }

    @org.junit.jupiter.api.Test
    void displayCard() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(CardFace.Jack, Suit.Diamonds));
        String actual = blackjack.displayCard(hand, "");
        String cards= " hand:\nJack of Diamonds | ";
        Assertions.assertEquals(cards, actual);
    }

    @org.junit.jupiter.api.Test
    void testDisplayCard() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(CardFace.Jack, Suit.Diamonds));
        hand.add(new Card(CardFace.Jack, Suit.Clubs));
        String actual = blackjack.displayCard(hand, "", 1);
        String cards= " hand:\nJack of Diamonds | ";
        Assertions.assertEquals(cards, actual);
    }

    @org.junit.jupiter.api.Test
    void blackjackCheck() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(CardFace.Jack, Suit.Diamonds));
        hand.add(new Card(CardFace.Ace, Suit.Clubs));
        List<Card> hand2 = new ArrayList<>();
        hand2.add(new Card(CardFace.Jack, Suit.Diamonds));
        hand2.add(new Card(CardFace.Jack, Suit.Clubs));
        List<Card> hand3 = new ArrayList<>();
        hand3.add(new Card(CardFace.Ace, Suit.Clubs));
        hand3.add(new Card(CardFace.Jack, Suit.Diamonds));
        Assertions.assertTrue(blackjack.blackjackCheck(hand));
        Assertions.assertFalse(blackjack.blackjackCheck(hand2));
        Assertions.assertTrue(blackjack.blackjackCheck(hand3));
    }

    @org.junit.jupiter.api.Test
    void bustCheck() {
        BlackjackPlayer player = new BlackjackPlayer();
        blackjack.setUp(player, 0);
        Assertions.assertTrue(blackjack.bustCheck(30, player));
        Assertions.assertFalse(blackjack.bustCheck(20, player));
    }

    @org.junit.jupiter.api.Test
    void winConditionCheck() {
        BlackjackPlayer player = new BlackjackPlayer();
        blackjack.setUp(player, 0);
        blackjack.dealerFlag();
        blackjack.dealerPlays(20);
        Assertions.assertTrue(blackjack.winConditionCheck(player, 21));
    }

    @org.junit.jupiter.api.Test
    void testAceCheck() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(CardFace.Jack, Suit.Diamonds));
        hand.add(new Card(CardFace.Ace, Suit.Diamonds));
        Assertions.assertTrue(blackjack.aceCheck(hand));
        List<Card> hand1 = new ArrayList<>();
        hand1.add(new Card(CardFace.Jack, Suit.Diamonds));
        hand1.add(new Card(CardFace.King, Suit.Diamonds));
        Assertions.assertFalse(blackjack.aceCheck(hand1));
    }

    @Test
    void postGameEval() {
        BlackjackPlayer player = new BlackjackPlayer();
        BlackjackPlayer player1 = new BlackjackPlayer();
        blackjack.setUp(player, 1);
        blackjack.setUp(player1, 1);
        blackjack.postGameEval(player, true);
        blackjack.postGameEval(player1, false);
        Assertions.assertEquals(2,player.getBalance());
        Assertions.assertEquals(0,player1.getBalance());
    }

    @Test
    void dealerTest(){
        BlackjackPlayer player = new BlackjackPlayer();
        blackjack.dealerFlag();
        blackjack.setUp(player, 0);
        blackjack.dealerPlays(8);
        Assertions.assertFalse(blackjack.winConditionCheck(player,8));
    }
}