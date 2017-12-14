/*******************************************************************************
 * Copyright (C) 2017, Zijun Zhao 
 * 
 * All rights reserved. 
 * 
 * Contributors: 
 *     Zijun Zhao - Initial coder and developer
 * 
 ******************************************************************************/

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Implements Cnf class, storing the Boolean formula. 
 * 
 * @author zijun
 */ 
public class Cnf {
	/** 
	 * list of all the symbols in the formula
	 */
	private ArrayList<Integer> symbols;
	
	/**
	 * Get the symbols in the current clause
	 * 
	 * @return the current symbol list
	 */
	public ArrayList<Integer> getSymbols() {
		return symbols;
	}
	
	/** 
	 * list of all the clauses in the formula
	 */
	private ArrayList<Clause> clauses;

	/**
	 * constructor for creating an empty formula
	 */
	public Cnf() {
		this.clauses = new ArrayList<Clause>();
		this.symbols = new ArrayList<Integer>();
	}

	/**
	 * constructor for creating a formula from a given stream
	 * 
	 * @param fstream the given stream
	 */
	public Cnf(InputStream fstream) {
		this.clauses = new ArrayList<Clause>();
		this.symbols = new ArrayList<Integer>();
		this.parse(fstream);
	}
	
	/**
	 * constructor for creating a formula with given number of symbols
	 * 
	 * @param numSymbols the given number of symbols
	 */
	public Cnf(int numSymbols) {
		this.clauses = new ArrayList<Clause>();
		this.symbols = new ArrayList<Integer>();
		for (int i = 1; i <= numSymbols; ++i) {
			symbols.add(i);
		}
	}

	/**
	 * Adds a new clause to this CNF.
	 * 
	 * @param l the literals contained in the clause
	 */
	public void addClause(ArrayList<Literal> l) {
		clauses.add(new Clause(l));
	}
	
	/**
	 * Get the clauses of the current formula
	 * 
	 * @return the clause lists
	 */
	public ArrayList<Clause> getClauses() {
		return clauses;
	}
	
	/**
	 * Print the current formula as a String
	 * 
	 * @return a String representing the current clause
	 */
	public String printClauses() {
		String s="";
		for (Clause c : this.clauses) {
			s+=c.print()+"0\n";
		}
		return s;
	}
	
	/**
	 * Parse an input stream to generate the formula
	 * 
	 * @param fstream the input stream
	 */
	public void parse(InputStream fstream) {
		int numVariables = 0;
		int numClauses = 0;
		try {

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			int index = 0;

			while ((line = br.readLine()) != null && line.length() > 0) {
				if (line.length() == 0)
					continue;
				if (line.charAt(0) != 'c') {
					if (line.charAt(0) == 'p') {
						String[] content = line.split(" ");
						numVariables = Integer.parseInt(content[2]);
						numClauses = Integer.parseInt(content[3]);

						for (int i = 1; i <= numVariables; ++i)
							symbols.add(i);
						for (int i = 0; i < numClauses; ++i)
							clauses.add(new Clause());
					} else {
						// String f = line.substring(0, line.length() - 2).trim();
						String f = line.replaceAll("\\s+", " ");
						String[] content = f.split(" ");
						// System.out.println("length" + content.length);
						for (String s : content) {
							if (!s.equals("0")) {
								clauses.get(index).addLiteral(Integer.parseInt(s));
							} else {
								index += 1;
							}
						}
					}

				}

			}
		} catch (IOException e) {
		 	e.printStackTrace();
		}
			// printClauses();

	}
}
