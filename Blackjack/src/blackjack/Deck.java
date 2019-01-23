package blackjack;

import java.util.ArrayList;
import java.util.List;

import main.Tools;

public class Deck {
	private ArrayList<Card> cards = new ArrayList<Card>();

	public Deck(Card... cards) {
		for (Card i : cards) {
			this.cards.add(i);
		}
	}

	public Deck(ArrayList<Card> cards) {
		this.cards = cards;
	}

	/**
	 * Get the current list of cards in the deck.
	 * 
	 * @return the current list of cards in the deck.
	 */
	public ArrayList<Card> getCards() {
		return this.cards;
	}

	/**
	 * Set the cards in the deck to your own list
	 * 
	 * @param cards
	 *            The list to set the list of cards to.
	 */
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	/**
	 * Put a card at the specified index.
	 * 
	 * @param card
	 *            the card to put
	 * @param index
	 *            The index to put the card at
	 */
	public void putCardAt(Card card, int index) {
		this.cards.add(index, card);
	}

	/**
	 * Put the card at the top of the deck (index 0)
	 * 
	 * @param card
	 *            the card to put
	 */
	public void putCardAtTop(Card card) {
		this.cards.add(0, card);
	}

	/**
	 * Put the card at the bottom of the deck (index {@code size() - 1})
	 * 
	 * @param card
	 *            the card to put
	 */
	public void putCardAtBottom(Card card) {
		this.cards.add(card);
	}

	/**
	 * Put the card at a random index of the deck.
	 * 
	 * @param card
	 *            the card to put
	 */
	public void putCardAtRandom(Card card) {
		putCardAt(card, Tools.Numbers.randomInt(0, cards.size() - 1));
	}

	/**
	 * Remove the card at the specified index from the deck.
	 * 
	 * @param index
	 *            the index to remove the card from.
	 */
	public void removeCard(int index) {
		this.cards.remove(index);
	}

	/**
	 * Remove a random card from the deck.
	 */
	public void removeRandomCard() {
		this.cards.remove(Tools.Numbers.randomInt(0, cards.size() - 1));
	}

	/**
	 * Remove the card at the top of the deck (index 0)
	 */
	public void removeTop() {
		removeCard(0);
	}

	/**
	 * Remove the card at the bottom of the deck (index {@code size() - 1})
	 */
	public void removeBottom() {
		removeCard(cards.size() - 1);
	}

	/**
	 * Remove the card at the specified index from the deck, and return it.
	 * 
	 * @param index
	 *            the index to draw the card from.
	 * @return The card that was drawn
	 */
	public Card drawCard(int index) {
		return this.cards.remove(index);
	}

	/**
	 * Remove the card at the bottom of the deck (index {@code size() - 1}), and
	 * return it.
	 * 
	 * @return The card that was drawn
	 */
	public Card drawBottom() {
		return drawCard(cards.size() - 1);
	}

	/**
	 * Remove the card at the top of the deck (index 0), and return it.
	 * 
	 * @return The card that was drawn
	 */
	public Card drawTop() {
		return drawCard(0);
	}

	/**
	 * Remove a random card from the deck, and return it.
	 * 
	 * @return The card that was drawn
	 */
	public Card drawRandomCard() {
		return drawCard(Tools.Numbers.randomInt(0, cards.size() - 1));
	}

	/**
	 * Get the card at the top of the deck (index 0)
	 * 
	 * @return the card at the top of the deck
	 */
	public Card getTop() {
		return cards.get(0);
	}

	/**
	 * Get the card at the bottom of the deck (index {@code size() - 1})
	 * 
	 * @return the card at the bottom of the deck
	 */
	public Card getBottom() {
		return cards.get(cards.size() - 1);
	}

	/**
	 * Get a random card in the deck.
	 * 
	 * @return a random card in the deck.
	 */
	public Card getRandom() {
		return cards.get(Tools.Numbers.randomInt(0, cards.size() - 1));
	}

	/**
	 * Take the current card in the deck, shuffle them around, and put them back
	 * into the deck.
	 * 
	 * @return the deck, or null if there was an error.
	 */
	public Deck shuffle() {
		ArrayList<Card> res = new ArrayList<>();
		ArrayList<Card> temp = (ArrayList<Card>) this.getCards().clone();
		int times = temp.size();

		for (int i = 0; i < times; i++) {
			int index = Tools.Numbers.randomInt(0, times - 1);
			res.add(temp.remove(index));
		}

		setCards(res);
		return this;
	}
}
