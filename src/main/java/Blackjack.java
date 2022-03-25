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
    private Map<BlackjackPlayer, Boolean> aceFlag = new HashMap<>();
    private Map<BlackjackPlayer, Integer> playerHandSum = new HashMap<>();
    private Map<BlackjackPlayer, Integer> playerBet= new HashMap<>();
    private Map<BlackjackPlayer, Boolean> bustedFlag = new HashMap<>();
    private BlackjackPlayer dealer = new BlackjackPlayer();

    public Blackjack(){}

    public void play(List<BlackjackPlayer> playerList){
        boolean succeed = false;
        int bet;
        Card temp;
        for (BlackjackPlayer player:playerList) { //Setup
            bet = getBet(player);
            setBet(player, bet);
            bustedFlag.put(player,false);
        }
        playerList.add(dealer);
        for (int i =0; i<2;i++){ //Game starting
            for (BlackjackPlayer player: playerList){
                if (playerHand.get(player)==null)
                    playerHand.put(player, new ArrayList<>());
                playerHand.get(player).add(draw());
            }
        }
        for (BlackjackPlayer player:playerList){ //First round flag checks
            blackjack.put(player, blackjackCheck(playerHand.get(player)));
            aceFlag.put(player, aceCheck(playerHand.get(player)));
            playerHandSum.put(player, sumHand(playerHand.get(player)));
        }
        String input;
        for (BlackjackPlayer player: playerList) {
            if (blackjack.get(dealer)){
                break;
            }
            while (true) {
                if (player.equals(dealer))
                    break;
                System.out.println(displayCard(playerHand.get(dealer),"Dealer", 1));
                System.out.println(displayCard(playerHand.get(player), player.getName()));
                if (blackjack.get(player))
                    break;
                if (bustCheck(playerHand.get(player), player)) {
                    bustedFlag.put(player, true);
                    break;
                }
                input = console.getStringInput(player.getName() + ", do you want to hit, double, or stay?");
                if (input.equalsIgnoreCase("hit")) {
                    temp = draw();
                    playerHand.get(player).add(temp);
                    playerHandSum.put(player, addToSum(temp, player));
                    if (aceCheck(temp))
                        aceFlag.put(player,true);
                } else if (input.equalsIgnoreCase("stay"))
                    break;
                else if (input.equalsIgnoreCase("double")) {
                    temp = draw();
                    playerHand.get(player).add(temp);
                    playerHandSum.put(player, addToSum(temp, player));
                    if (aceCheck(temp))
                        aceFlag.put(player,true);
                    setBet(player,playerBet.get(player)*2);
                    break;
                }
            }
        }
        if (!blackjack.get(dealer) && bustedFlag.containsValue(false)) {
            while (playerHandSum.get(dealer) < 17) {
                temp = draw();
                playerHand.get(dealer).add(temp);
                playerHandSum.put(dealer, addToSum(temp, dealer));
            }
        }
        System.out.println(displayCard(playerHand.get(dealer),"Dealer"));
        System.out.println("For a total of "+playerHandSum.get(dealer));
        for (BlackjackPlayer player: playerList){
            if (player.equals(dealer)){
                continue;
            }
            System.out.println(displayCard(playerHand.get(player),player.getName()));
            System.out.println(player.getName()+" got a total of "+playerHandSum.get(player));
            if (winConditionCheck(player, playerHandSum.get(player))) {
                player.wins(playerBet.get(player));
                System.out.println(player.getName() + " wins!");
            }
             else
                System.out.println(player.getName()+" loses!");
        }
    }

    public Card draw(){return deck.getTopCard();}

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

    public int sumHand(List<Card> list){
        int value = 0;
        for (Card hand: list){
            value+=cardValue(hand);
        }
        return value;
    }

    public int addToSum(Card card, BlackjackPlayer player){
        return playerHandSum.get(player)+cardValue(card);
    }

    public Boolean aceCheck(List<Card> cards) {
        for (Card card: cards){
            if (card.getCardFace().equals(CardFace.Ace)){
                return true;
            }
        }
        return false;
    }


    public Boolean aceCheck(Card card) {
        if (card.getCardFace().equals(CardFace.Ace))
            return true;
        return false;
    }

    public boolean setBet(BlackjackPlayer player, int bet) {
        playerBet.put(player, bet);
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

    public String displayCard(List<Card> hand, String name){
        String cards= name+" hand:\n";
        for (Card s: hand){
            cards+=s.toString()+" | ";
        }
        return cards;
    }

    public String displayCard(List<Card> hand, String name, int index){
        List<Card> tempHand= new ArrayList<>();
        for (int i=0;i<index;i++)
            tempHand.add(hand.get(i));
        return displayCard(tempHand, name);
    }

    public boolean blackjackCheck(List<Card> hand) {
        if (this.cardValue(hand.get(0))==10)
        {
            if (hand.get(1).getCardFace().equals(CardFace.Ace)) {
                return true;
            }
            else
                return false;
        }
        else if (this.cardValue(hand.get(1))==10) {
            if (hand.get(0).getCardFace().equals(CardFace.Ace)) {
                return true;
            }
            else
                return false;
        }
        return false;
    }

    public Boolean bustCheck(List<Card> hand, BlackjackPlayer player) {
        while (playerHandSum.get(player) > 21) {
            if (aceFlag.get(player)) {
                playerHandSum.put(player, playerHandSum.get(player)-10);
                aceFlag.put(player, false);
            } else {
                System.out.println("Busted!");
                return true;
            }
        }
        return false;
    }

    public boolean winConditionCheck(BlackjackPlayer blackJackPlayer, int playerSum) {
        if (blackjack.get(dealer)) {
            if (blackjack.get(blackJackPlayer))
                return true;
            else
                return false;
        }
        else if (playerHandSum.get(dealer)>21){
            if (playerSum>21)
                return false;
            else
                return true;
        }
        else {
            if (blackjack.get(blackJackPlayer))
                return true;
            else if (playerSum > playerHandSum.get(dealer) && playerSum <= 21)
                return true;
            else
                return false;
        }
    }
}
