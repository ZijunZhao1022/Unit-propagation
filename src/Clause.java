/*******************************************************************************
 * Copyright (C) 2017, Zijun Zhao 
 * 
 * All rights reserved. 
 * 
 * Contributors: 
 *     Zijun Zhao - Initial coder and developer
 * 
 ******************************************************************************/

import java.util.ArrayList;

/**
 * Implements Clause class, storing clauses in Boolean formula. 
 * 
 * @author zijun
 */ 
public class Clause {

	/**
	 * constructor for creating an empty clause
	 */
	public Clause() {
		this.literals = new ArrayList<Literal>();
	}

	/**
	 * constructor for creating a clause containing the given literals
	 * 
	 * @param literals a list of all input literals
	 */
	public Clause(ArrayList<Literal> literals) {
		this.literals = literals;
	}

	/**
	 * Add a new literal with given symbol and sign into the current clause
	 * 
	 * @param symbol the symbol of the given literal
	 * @param sign the sign of the given literal
	 */
	public void addLiteral(int symbol, boolean sign) {
		literals.add(new Literal(symbol, sign));
	}

	/**
	 * Add a new literal with given symbolinto the current clause
	 * 
	 * @param symbol the symbol of the given literal, noted that the symbol can be negative in this function
	 */
	public void addLiteral(int symbol) {
		if (symbol < 0)
		{
			literals.add(new Literal(-symbol, false));
		}
		else
			literals.add(new Literal(symbol, true));
	}
	
	/**
	 * Detect whether a literal is included in current clause
	 * 
	 * @param l the literal to be detected
	 * 
	 * @return a boolean value indicating whether the literal is contained
	 */
	public boolean hasLiteral(Literal l) {
		return literals.contains(l);
	}
	
	/**
	 * Remove a given literal retrieved from a unit clause
	 * 
	 * @param unit the given literal
	 * 
	 * @return a boolean value indicating whether the removal succeeds
	 */
	public boolean removeUnit(Literal unit) {
		int size = literals.size();
		
		// If contradiction found, return false
		if(size==1)
		{
			Literal l = literals.get(0);
			if(l.equals(unit) && unit.sign != l.sign)
				return false;
		}
		
		int index = -1;
		for(int i=0;i<size;++i) {
			if(literals.get(i).equals(unit))
				index = i;
		}
		
		if(index > -1)
		{
			if(unit.sign != literals.get(index).sign)
				literals.remove(index);
			else
				literals.clear();
		}
		
		return true;
	}
	
	/**
	 * Get the literals in the current clause
	 * 
	 * @return the current literal list
	 */
	public ArrayList<Literal> getLiterals() {
		return literals;
	}
	
	/**
	 * Print the current clause as a String
	 * 
	 * @return a String representing the current clause
	 */
	public String print() {
		String s="";
		for(Literal l : literals) {
			s+= l.toString() + " ";
		}
		return s;
	}
	
	/** 
	 * list of all the literals in the clause
	 */
	private ArrayList<Literal> literals;
}
