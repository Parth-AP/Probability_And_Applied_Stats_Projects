import java.util.ArrayList;
import java.util.Random;

/* Directions, Please Pay Attention

 *60, there's a catch, in order to take a turn you needed to have a basic pokemon in your hand.

 *Let's write a monte carlo simulation. That means, using raw brute force, try to figure out
 *something interesting

 *What if your deck had exactly 1 pokemon. How many times would you expect to have to
 *"Mulligan" in order to have your only pokemon in your hand.

 *What if your deck had 2? Etc.

 *Write a simulation that shows the expected mulligans at 1 - 60 pokemons in your deck.

 *HW What are the odds of finding the object after 1000 runs??
 *Re-shuffle the deck until we get the pokemon?
 */


public class PokemonMonteCarloSimulation {
	
		//Counter variable kept track of all the pokemon cards detected within the hand
		int counter = 0;
		
		//boolean variable for the evaluateOpeningHand method
		boolean havePokemon;
		
		// deckSize variable is used to set the length of how big the deck should be
		int deckSize = 60;
		
		//pokemonCardLimit variable is used to set the limit of the loop that controls how many pokemon cards are
		//added to the deck
		int pokemonCardLimit = 1;
		
		//energyCardLimit variable is used to set the limit of the loop that controls how many energy cards are
		//added to the deck
		int energyCardLimit = 60;
		
		//numberOfTimesDrawn variable is used to set the number of times each trail will run before adding more
		//pokemon and energy cards into the deck
		int numberOfTimesDrawn = 10000;
		
		//loopCount variable is used to track the number of times a pokemon card is added to the deck after each re-shuffle
		int loopCount = 0;

		
		//A deck and hand of cards.
		private ArrayList<Card> deck;  //This is the constructors job. = new Card[];
		private ArrayList<Card> hand;
		
	//Constructor is instantiating the deck and hand. It is also calling the updateDeck method	
	public PokemonMonteCarloSimulation() {
			deck = new ArrayList<Card>();
			hand = new ArrayList<Card>();
			
			updateDeck();
		
		}
	
	//The updateDeck method clears the deck and then re-populates the deck with pokemon and energy cards
	public void updateDeck() {
		deck.clear();
		
		for(int i = 0; i < pokemonCardLimit; i++) {
			deck.add(new Pokemon());
		}
		
		for( int i = 1; i < energyCardLimit; i++) {
			deck.add(new Energy());
		}
		
	}

	//The drawCard method is drawing a random card from the deck and returning it
	public Card drawCard() {
		Random rng = new Random();
		int cardIndex = rng.nextInt(deck.size() ); //Find random card
		Card drawnCard = deck.get(cardIndex);
		deck.remove(cardIndex);
		return drawnCard;
		
	}

	//The getPercent method is getting the decmial value of counter divided by number of times drawn and 
	//converting that into a percentage by multiplying by 100.
	public double getPercent() {
		Double equation = Double.valueOf((double)counter / numberOfTimesDrawn);    //divide final number by loop size
		return (equation * 100);
	}
	
	//The reshuffle method is adding all the cards withtin the hand to the deck and clearing the hand
	public void reShuffle() {
		deck.addAll(hand);
		hand.clear();
	}

	//The drawhand method is calling the drawCard method to add 7 cards into the hand
	public void drawHand() {
		for(int i = 0; i < 7; i++) {//We're counting to 7
			hand.add(drawCard());
		}
	}

	//The evaulateOpeningHand method is checking the hand to see if any of the cards in the hand are actually pokemon cards
	public boolean evaulateOpeningHand() {
		havePokemon = false;
		for(int i = 0; i < hand.size(); i++) {
			Card currentCard = hand.get(i);
			if(currentCard instanceof Pokemon) {
				counter++;
			//	 System.out.println(hand);
				return true;
			}
		}
		return false;
	}

	 //The run method is basically the engine of the program. The method calls the drawhand method to get 7 cards into the hand.
	 //Then the evaulateOpeningHand method checks to see if there are any pokemon cards in the hand. If there are then the counter
	 //will increase to keep track of how many are present in the hand. Then the reShuffle method is called to clear the hand
	 //and reshuffle the deck. The counter is also reset for the next hand. Lastly, the updateDeck method is called to repopulate
	 // all the cards within the deck.
	public void run() {
		
	while(pokemonCardLimit != 60 && energyCardLimit != 1) {
		loopCount++;
		for (int number = 0; number < numberOfTimesDrawn; number++) {
			drawHand();
			
			evaulateOpeningHand();
		
			reShuffle();

		}
		System.out.printf("\n%s%.3f%s", "The Chance Of Pulling A Pokemon Card From The Deck Is ",
				getPercent(), "%");
		
		System.out.println("\nTotal Number Of Times Pokemon Card Was Found " + counter + 
				" Number Of Pokemon Cards In Deck: " + loopCount);
		
		counter = 0;
		pokemonCardLimit++;
		energyCardLimit--;
		updateDeck();
		}
	}
}
