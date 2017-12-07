public class Literal {
	/**
	 * constructor for creating a literal with the given sign and symbol index
	 */
        public Literal(int symbol, boolean sign){
		System.out.println("add new literal s = " + symbol + " sign = " + sign);
		this.symbol = symbol;
		this.sign = sign;

} 
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
	
	public boolean equals(Literal l) {
		return l.symbol == this.symbol;
	}
	
	/** sign of the literal: true = unnegated, false = negated */
	public boolean sign;

	/** index of proposition symbol */
	public int symbol£»

}