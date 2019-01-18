package blackjack;

import java.util.ArrayList;

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
	
	public ArrayList<Card> getCards() {
		return this.cards;
	}
	
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	public void addCard(Card card) {
		this.cards.add(card);
	}
	
	public void putCardAt(Card card, int index) {
		this.cards.add(index, card);
	}
	
	public void putCardAtTop(Card card) {
		this.cards.add(0, card);
	}
	
	public void putCardAtBottom(Card card) {
		this.cards.add(card);
	}
	
	public void putCardAtRandom(Card card) {
		putCardAt(card, Tools.Numbers.randomInt(0, cards.size() - 1));
	}
	
	public void removeCard(int index) {
		this.cards.remove(index);
	}
	
	public void removeRandomCard() {
		this.cards.remove(Tools.Numbers.randomInt(0, cards.size() - 1));
	}
	
	public void removeTop() {
		removeCard(0);
	}
	
	public void removeBottom() {
		removeCard(cards.size() - 1);
	}
	
	public Card drawCard(int index) {
		return this.cards.remove(index);
	}
	
	public Card drawTop() {
		return drawCard(cards.size() - 1);
	}
	
	public Card drawRandomCard() {
		return drawCard(Tools.Numbers.randomInt(0, cards.size() - 1));
	}
	
	public Card getTop() {
		return cards.get(0);
	}
	
	public Card getBottom() {
		return cards.get(cards.size() - 1);
	}
	
	public Card getRandom() {
		return cards.get(Tools.Numbers.randomInt(0, cards.size() - 1));
	}
}
