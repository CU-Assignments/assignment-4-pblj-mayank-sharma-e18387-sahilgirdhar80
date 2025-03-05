import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Card {
    private String symbol;
    private String rank;

    public Card(String symbol, String rank) {
        this.symbol = symbol;
        this.rank = rank;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + symbol;
    }
}

public class CardCollection {
    private Map<String, List<Card>> cardCollection;
    private Scanner scanner;

    public CardCollection() {
        cardCollection = new HashMap<>();
        scanner = new Scanner(System.in);
        initializeCards();
    }

    private void initializeCards() {
        String[] symbols = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String symbol : symbols) {
            List<Card> cards = new ArrayList<>();
            for (String rank : ranks) {
                cards.add(new Card(symbol, rank));
            }
            cardCollection.put(symbol, cards);
        }
    }

    public void findCardsBySymbol() {
        System.out.print("Enter the symbol (e.g., Hearts, Spades): ");
        String symbol = scanner.nextLine();
        List<Card> cards = cardCollection.get(symbol);

        if (cards != null && !cards.isEmpty()) {
            System.out.println("Cards of " + symbol + ":");
            for (Card card : cards) {
                System.out.println(card);
            }
        } else {
            System.out.println("No cards found for the symbol: " + symbol);
        }
    }

    public void displayMenu() {
        System.out.println("Card Collection System");
        System.out.println("1. Find Cards by Symbol");
        System.out.println("2. Exit");
    }

    public static void main(String[] args) {
        CardCollection collection = new CardCollection();
        while (true) {
            collection.displayMenu();
            System.out.print("Choose an option: ");
            int choice = collection.scanner.nextInt();
            collection.scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    collection.findCardsBySymbol();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    collection.scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
