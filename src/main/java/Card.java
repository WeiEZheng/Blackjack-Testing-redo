public class Card {
    private Suit suit;
    private CardFace cardFace;

    public Card (CardFace cardFace, Suit suit){
        this.cardFace=cardFace;
        this.suit=suit;
    }

    public Suit getSuit(){
        return this.suit;
    }

    public CardFace getCardFace() {
        return cardFace;
    }

    public String toString(){
        return this.cardFace + " of " + this.suit;
    }
}
