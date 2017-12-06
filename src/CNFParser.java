import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Cnf {

	private ArrayList<Integer> symbols;
	public ArrayList<Integer> getSymbols() {
		return symbols;
	}

	private ArrayList<Clause> clauses;

	public Cnf() {
		this.clauses = new ArrayList<Clause>();
		this.symbols = new ArrayList<Integer>();
	}

	public Cnf(InputStream fstream) {
		this.clauses = new ArrayList<Clause>();
		this.symbols = new ArrayList<Integer>();
		this.parse(fstream);
	}
	
	public Cnf(int numSymbols) {
		this.clauses = new ArrayList<Clause>();
		this.symbols = new ArrayList<Integer>();
		for (int i = 1; i <= numSymbols; ++i) {
			symbols.add(i);
		}
	}

	