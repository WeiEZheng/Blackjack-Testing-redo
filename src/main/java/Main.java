import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<BlackjackPlayer> playerList = new ArrayList<>();
        playerList.add(new BlackjackPlayer());
        new Blackjack().play(playerList);
    }
}
