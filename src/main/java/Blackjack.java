import UtilFromCasino.AnsiColor;
import UtilFromCasino.IOConsole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Blackjack {
    private Deck deck = new Deck();
    private final IOConsole console = new IOConsole(AnsiColor.BLUE);
    private Map<BlackjackPlayer, List<Card>> playerHand = new HashMap<>();
    private Map<BlackjackPlayer, Boolean> blackjack = new HashMap<>();

    public Blackjack(){}

    public void play(List<BlackjackPlayer> playerList){
        boolean succeed = false;
        int bet;
        for (BlackjackPlayer player:playerList) {
            bet = getBet(player);
            setBet(player, bet);
            playerHand.put(player, drawFirst2Cards(player));
            if (blackjackCheck(player)){
                blackjack.put(player, true);
            }
            aceCheckFlag;
            sumHand();
        }
    }

    public boolean blackjackCheck(BlackjackPlayer player) {
        List<Card> temp = playerHand.get(player);
        if (this.cardValue(temp.get(0))==10)
        {
            if (temp.get(1).getCardFace().equals(CardFace.Ace)) {
                return true;
            }
            else
                return false;
        }
        else if (this.cardValue(temp.get(1))==10) {
            if (temp.get(0).getCardFace().equals(CardFace.Ace)) {
                return true;
            }
            else
                return false;
        }
        return false;
    }

    public int sumHand(List<Card> list){
        int value = 0;
        for (Card hand: list){
            value+=cardValue(hand);
        }
        return value;
    }

    public int cardValue(Card card){
        int value = 0;
        CardFace cardFace = card.getCardFace();
        switch (cardFace){
            case Ace: value = 11; break;
            case Two: value = 2; break;
            case Three: value = 3; break;
            case Four: value = 4; break;
            case Five: value = 5; break;
            case Six: value = 6; break;
            case Seven: value = 7; break;
            case Eight: value = 8; break;
            case Nine: value = 9; break;
            case Ten: case Jack: case Queen:
            case King: value = 10; break;
        }
        return value;
    }

    public boolean setBet(BlackjackPlayer player, int bet) {
        return player.applyBet(bet);
    }

    public int getBet(BlackjackPlayer player){
        int walletBalance = player.getBalance();
        int bet = console.getIntegerInput(
                "Hello " +player.getName() + ", how much would you like to bet?" +
                        " Your current balance is " + player.getBalance());
        while (bet>walletBalance || bet<=0){
            bet= console.getIntegerInput("Bet note valid, try again");
        }
        return bet;
    }

    public Card draw(){return deck.getTopCard();}

    public List<Card> drawFirst2Cards(BlackjackPlayer player){
        List hand = new ArrayList<>();
        hand.add(draw());
        hand.add(draw());
        return hand;
    }
}
