package io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CNFParser {

	/** Character of starting variable in a formula */
	final static String START_VAR = "";

	/** Character of ending variable in a formula */
	final static String END_VAR = "";

	public Formula bf;
	
	public CNFParser(Formula bf) {
		this.bf = bf;
	}
	
	public void parse(InputStream fstream) {
		int numVariables = 0;
		int numClausules = 0;
		// System.out.println(filename);
		
		ArrayList<String> variables = bf.getVariables();
		ArrayList<String> CNFS = bf.getCNFS();
		HashMap<String, Boolean> vmap = bf.getVmap();
		
		try {
			// Open the file that is the first
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;

			int formulaIndex = 1;

			// System.out.println("Extracting clausules");

			// Read File Line By Line
			while ((line = br.readLine()) != null && line.length() > 0) {
				if (line.length() == 0)
					break;
				if (line.charAt(0) != 'c') {
					if (line.charAt(0) == 'p') {
						String[] content = line.split(" ");
						numVariables = Integer.parseInt(content[2]);
						numClausules = Integer.parseInt(content[3]);

						for (int i = 1; i <= numVariables; i++) {
							variables.add(START_VAR + "" + i + END_VAR);
							vmap.put(START_VAR + "" + i + END_VAR, false);
						}
					} else {
						String f = line.substring(0, line.length() - 2).trim();
						f = f.replaceAll("\\s+", " ");
						if (f.charAt(0) == ' ') {
							f = f.substring(1);
						}
						f = f.replaceAll("\\s+", "||");
						f = f.replaceAll("-", "!");
						f = f.replaceAll("  ", " ");
						f += " ";
						// System.out.println(f);
						f = f.replaceAll("(\\d+)", START_VAR + "$1" + END_VAR);
						f = f.trim();
						for (String var : vmap.keySet()) {
							if (f.contains(var)) {
								vmap.put(var, true);
							}
						}

						CNFS.add(f);

//						System.out.println(
//								"Extracting clause " + (formulaIndex) + " / " + numClausules + ": " + f);

						formulaIndex++;
						if (formulaIndex > numClausules)
							break;
					}
				}
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		/*System.out.println("Formula: ");
		for (int i = 0; i < CNFS.size(); ++i) {
			String s = CNFS.get(i);
			System.out.print("( " + s + " )");
			if (i + 1 != CNFS.size())
				System.out.print(" && ");
		}*/
		
		bf.setVariables(variables);
		bf.setCNFS(CNFS);
		bf.setVmap(vmap);
		
	}

	public void parse(String filename) {
		int numVariables = 0;
		int numClausules = 0;
		System.out.println(filename);
		
		ArrayList<String> variables = bf.getVariables();
		ArrayList<String> CNFS = bf.getCNFS();
		HashMap<String, Boolean> vmap = bf.getVmap();
		
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filename);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;

			int formulaIndex = 1;

			// System.out.println("Extracting clauses");

			// Read File Line By Line
			while ((line = br.readLine()) != null && line.length() > 0) {
				if (line.length() == 0)
					break;
				if (line.charAt(0) != 'c') {
					if (line.charAt(0) == 'p') {
						String[] content = line.split(" ");
						numVariables = Integer.parseInt(content[2]);
						numClausules = Integer.parseInt(content[3]);

						for (int i = 1; i <= numVariables; i++) {
							variables.add(START_VAR + "" + i + END_VAR);
							vmap.put(START_VAR + "" + i + END_VAR, false);
						}
					} else {
						String f = line.substring(0, line.length() - 2).trim();
						f = f.replaceAll("\\s+", " ");
						if (f.charAt(0) == ' ') {
							f = f.substring(1);
						}
						f = f.replaceAll("\\s+", "||");
						f = f.replaceAll("-", "!");
						f = f.replaceAll("  ", " ");
						f += " ";
						// System.out.println(f);
						f = f.replaceAll("(\\d+)", START_VAR + "$1" + END_VAR);
						f = f.trim();
						for (String var : vmap.keySet()) {
							if (f.contains(var)) {
								vmap.put(var, true);
							}
						}

						CNFS.add(f);

						//System.out.println(
						//		"Extracting clausule " + (formulaIndex) + " / " + numClausules + ": " + f);

						formulaIndex++;
						if (formulaIndex > numClausules)
							break;
					}
				}
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		/*System.out.println("Formula: ");
		for (int i = 0; i < CNFS.size(); ++i) {
			String s = CNFS.get(i);
			System.out.print("( " + s + " )");
			if (i + 1 != CNFS.size())
				System.out.print(" && ");
		}*/
		
		bf.setVariables(variables);
		bf.setCNFS(CNFS);
		bf.setVmap(vmap);

	}

	public String print() {
		return bf.print();
	}

	public void run(String file) {
		this.parse(file);
	}

}
