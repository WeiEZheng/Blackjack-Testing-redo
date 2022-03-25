import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;

public class BlackjackPlayer {
    private int balance;
    private String name;

    public BlackjackPlayer(){
        this("",1);
    }

    public BlackjackPlayer(String name, int balance){
        this.name=name;
        this.balance=balance;
    }

    public boolean applyBet(int bet){
        if (bet<=balance)
            balance-=bet;
        return false;
    }

    public int getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public int wins(Integer bet) {
        balance+=bet*2;
        return balance;
    }
}