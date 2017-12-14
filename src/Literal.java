/*******************************************************************************
 * Copyright (C) 2017, Zijun Zhao 
 * 
 * All rights reserved. 
 * 
 * Contributors: 
 *     Zijun Zhao - Initial coder and developer
 * 
 ******************************************************************************/

/**
 * Implements literal, aka variables in Boolean formula. 
 * 
 * @author zijun
 */ 
public class Literal {

	/**
	 * constructor for creating a literal with the given sign and symbol index
	 * 
	 * @param symbol the symbol of the literal 
	 * @param sign the sign of the literal
	 */
	public Literal(int symbol, boolean sign) {
	//	System.out.println("add new literal s = " + symbol + " sign = " + sign);
		this.symbol = symbol;
		this.sign = sign;
	}
	
	/**
	 * output the literal in string format
	 * 
	 * @return a string expressing the literal
	 */
	public String toString() {
		String s ="";
		if(sign == false) {
			s+="-"+ symbol;
		}
		else {
			s+=symbol;
		}
		return s;
	}
	
	/**
	 * check whether the given literal is equal to this literal
	 * 
	 * @param l the literal to compare 
	 * 
	 * @return a boolean variable indicating whether the two literals are equal
	 */
	public boolean equals(Literal l) {
		return l.symbol == this.symbol;
	}
	
	/** 
	 * sign of the literal: true = unnegated, false = negated 
	 * */
	public boolean sign;

	/** 
	 * index of proposition symbol 
	 * */
	public int symbol;

}
