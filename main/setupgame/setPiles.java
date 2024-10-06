public class readJson(string filename){
    filePath = parse("./src/", filename );
    try (InputStream fInputStream = new FileInputStream(filePath);
    Scanner scanner = new Scanner(fInputStream, "UTF-8").useDelimiter("\\A")) {

        // Read the entire JSON file into a String
        String jsonString = scanner.hasNext() ? scanner.next() : "";

        // Parse the JSON string into a JSONObject
        JSONObject jsonObject = new JSONObject(jsonString);

        // Get the "cards" array from the JSONObject
        JSONArray cardsArray = jsonObject.getJSONArray("cards");

        return cardsArray

    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void setPiles(int nrPlayers) {
    ArrayList<Card> deckPepper = new ArrayList<>();
    ArrayList<Card> deckLettuce = new ArrayList<>();
    ArrayList<Card> deckCarrot = new ArrayList<>();
    ArrayList<Card> deckCabbage = new ArrayList<>();
    ArrayList<Card> deckOnion = new ArrayList<>();
    ArrayList<Card> deckTomato = new ArrayList<>();


        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject cardJson = cardsArray.getJSONObject(i);

            // Get the criteria object from the card JSON
            JSONObject criteriaObj = cardJson.getJSONObject("criteria");

            // Add each vegetable card to the deck with its corresponding criteria
            deckPepper.add(new Card(Card.Vegetable.PEPPER, criteriaObj.getString("PEPPER")));
            deckLettuce.add(new Card(Card.Vegetable.LETTUCE, criteriaObj.getString("LETTUCE")));
            deckCarrot.add(new Card(Card.Vegetable.CARROT, criteriaObj.getString("CARROT")));
            deckCabbage.add(new Card(Card.Vegetable.CABBAGE, criteriaObj.getString("CABBAGE")));
            deckOnion.add(new Card(Card.Vegetable.ONION, criteriaObj.getString("ONION")));
            deckTomato.add(new Card(Card.Vegetable.TOMATO, criteriaObj.getString("TOMATO")));
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

    // Shuffle each deck
    shuffleDeck(deckPepper);
    shuffleDeck(deckLettuce);
    shuffleDeck(deckCarrot);
    shuffleDeck(deckCabbage);
    shuffleDeck(deckOnion);
    shuffleDeck(deckTomato);

    int cardsPerVeggie = nrPlayers/2 * 6;

    ArrayList<Card> deck = new ArrayList<>();
    for(int i = 0; i < cardsPerVeggie; i++) {
        deck.add(deckPepper.remove(0));
        deck.add(deckLettuce.remove(0));
        deck.add(deckCarrot.remove(0));
        deck.add(deckCabbage.remove(0));
        deck.add(deckOnion.remove(0));
        deck.add(deckTomato.remove(0));
    }
    shuffleDeck(deck);

    //divide the deck into 3 piles
    ArrayList<Card> pile1 = new ArrayList<>();
    ArrayList<Card> pile2 = new ArrayList<>();
    ArrayList<Card> pile3 = new ArrayList<>();
    for (int i = 0; i < deck.size(); i++) {
        if (i % 3 == 0) {
            pile1.add(deck.get(i));
        } else if (i % 3 == 1) {
            pile2.add(deck.get(i));
        } else {
            pile3.add(deck.get(i));
        }
    }
    piles.add(new Pile(pile1));
    piles.add(new Pile(pile2));
    piles.add(new Pile(pile3));
}