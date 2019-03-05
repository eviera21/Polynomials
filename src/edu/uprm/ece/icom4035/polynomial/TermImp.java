package edu.uprm.ece.icom4035.polynomial;

public class TermImp implements Term {
	private double coefficient;
	private int exponent;
	
	public TermImp() {
		coefficient=0;
		exponent=0;
	}
	public TermImp(double c, int e) {
		coefficient=c;
		exponent=e;
	}
	public TermImp(Term t) {
		coefficient=t.getCoefficient();
		exponent=t.getExponent();
	}
	@Override
	public double getCoefficient() {
		return coefficient;
	}

	@Override
	public int getExponent() {
		return exponent;
	}

	@Override
	public double evaluate(double x) {
		return coefficient*Math.pow(x, exponent);
	}
	
	public void setCoefficient(double c) {
		coefficient = c;
	}
	
	public void setExponent(int e) {
		exponent = e;
	}
	
	public boolean equals(TermImp t) {
		return t.getCoefficient()==coefficient&&t.getExponent()==exponent;
	}
	public TermImp derive() {
		TermImp term = new TermImp(coefficient,exponent);
		term.setCoefficient(coefficient*exponent);
		if(exponent>0)term.setExponent(exponent-1);
		else setCoefficient(0);
		return term;
	}
	public TermImp integrate() {
		TermImp term = new TermImp(this);
		term.setCoefficient(coefficient/(exponent+1));
		term.setExponent(exponent+1);
		return term;
	}
	public String toString() {
		String s="";
		if(coefficient>=0) {
			if(coefficient-(int)coefficient==0)s+=(int)coefficient+".00";
		}
		if(exponent==0) return s;
		else if(exponent==1) return s+="x";
		else return s+="x^"+exponent;
	}
	

}
