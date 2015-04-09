package com.app.utility;

/**
 * 
 * @author David Romero Alcaide
 * 
 * @param <A>
 * @param <B>
 */
public class Pair<A, B> {
	private A first;
	private B second;

	/**
	 * 
	 * Constructor
	 * 
	 * @param first
	 * @param second
	 */
	public Pair(A first, B second) {
		super();
		this.first = first;
		this.second = second;
	}

	/**
	 * 
	 */
	public int hashCode() {
		int hashFirst = first != null ? first.hashCode() : 0;
		int hashSecond = second != null ? second.hashCode() : 0;

		return (hashFirst + hashSecond) * hashSecond + hashFirst;
	}

	/**
	 * 
	 */
	public boolean equals(Object other) {
		if (other instanceof Pair) {
			@SuppressWarnings("rawtypes")
			Pair otherPair = (Pair) other;
			return (this.first == otherPair.first ||
					(this.first != null
					&& otherPair.first != null && 
					this.first.equals(otherPair.first))) && 
					(this.second == otherPair.second || (this.second != null && 
					otherPair.second != null && 
					this.second.equals(otherPair.second)));
		}

		return false;
	}

	/**
	 * 
	 */
	public String toString() {
		return "(" + first + ", " + second + ")";
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public A getFirst() {
		return first;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param first
	 */
	public void setFirst(A first) {
		this.first = first;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public B getSecond() {
		return second;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param second
	 */
	public void setSecond(B second) {
		this.second = second;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param a
	 * @param b
	 * @return
	 */
	public static <A, B> Pair<A, B> create(A a, B b) {
		return new Pair<A, B>(a, b);
	}
}