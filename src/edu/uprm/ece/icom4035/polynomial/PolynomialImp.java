package edu.uprm.ece.icom4035.polynomial;

import java.util.Iterator;
import edu.uprm.ece.icom4035.list1.List;

public class PolynomialImp implements Polynomial {
	private List<Term> list;
	
	public PolynomialImp() {
		new TermListFactory();
		list= TermListFactory.newListFactory().newInstance();
	}
	public PolynomialImp(String s) {
		new TermListFactory();
		list= TermListFactory.newListFactory().newInstance();
		String[] splitString = s.split("\\+");
		for(String term:splitString) {
			String coef="";
			String exp="0";
			boolean isCoefficient=true;
			
			char[] splitTerm = term.toCharArray();
			for(int i=0;i<term.length();i++){
				if(splitTerm[i]=='-'||(isCoefficient&&splitTerm[i]!='x'))coef+=splitTerm[i];
				else isCoefficient=false;
				if(splitTerm[i]=='x') exp="1";
				else if(splitTerm[i]=='^') {
					exp="";
					i++;
					while(i<term.length()) {
						exp+=splitTerm[i];
						i++;
					}
				}
			}
			double coefficient;
			try{coefficient = Double.parseDouble(coef);}
			catch(Exception e) {coefficient = 1;}
			int exponent = Integer.parseInt(exp);
			if(coefficient!=0) list.add(new TermImp(coefficient,exponent));
		}
		if(list.isEmpty()) list.add(new TermImp(0,0));
	}

	@Override
	public Iterator<Term> iterator() {
		return list.iterator();
	}

	@Override
	public Polynomial add(Polynomial P2) {
		PolynomialImp result=new PolynomialImp();
		List<Term> l3 = result.list;
		Iterator<Term> iter1=iterator();
		Iterator<Term> iter2=P2.iterator();
		while(iter1.hasNext()) l3.add(iter1.next());
		while(iter2.hasNext()) l3.add(iter2.next());
		if(!l3.isEmpty()) {
			result.simplify();
		}
		result.checkZeros();
		
		return result;
	}

	@Override
	public Polynomial subtract(Polynomial P2) {
		return add(P2.multiply(-1));
	}

	@Override
	public Polynomial multiply(Polynomial P2) {
		PolynomialImp result=new PolynomialImp();
		Term term,term2;
		for(int i=0;i<list.size();i++) {
			term = list.get(i);
			for(int j=0;j<((PolynomialImp)P2).list.size();j++) {
				term2=((PolynomialImp)P2).list.get(j);
				result.list.add(new TermImp(term.getCoefficient()*term2.getCoefficient(),term.getExponent()+term2.getExponent()));
			}
		}
		result.simplify();
		result.sort();
		result.checkZeros();
		return result;
		
	}

	@Override
	public Polynomial multiply(double c) {
		PolynomialImp result=new PolynomialImp();
		Iterator<Term> iter = this.iterator();
		while(iter.hasNext()) {
			Term term = iter.next();
			result.list.add(new TermImp(term.getCoefficient()*c,term.getExponent()));
		}
		result.checkZeros();
		return result;
	}

	@Override
	public Polynomial derivative() { // returns the derivative of the polynomial
		PolynomialImp result=new PolynomialImp();
		Iterator<Term> iter = this.iterator();
		while(iter.hasNext()) {
			TermImp term = (TermImp) iter.next();
			if(term.getExponent()>=1) {
				term=term.derive();
				if(term.getCoefficient()!=0)result.list.add(term);
			} 
		}
		result.checkZeros();
		return result;
	}

	@Override
	public Polynomial indefiniteIntegral() { // integrates the polynomial and returns a new one
		PolynomialImp result=new PolynomialImp();
		for(int i=0;i<list.size();i++) {
			TermImp term = ((TermImp)list.get(i)).integrate();
			result.list.add(term);
		}
		result.list.add(new TermImp(1,0)); // to substitute constant C, we add 1 to the polynomial
		return result;
	}

	@Override
	public double definiteIntegral(double a, double b) { // evaluates an integral from value a to b
		PolynomialImp poli = (PolynomialImp) indefiniteIntegral();
		return poli.evaluate(b)-poli.evaluate(a);
	}

	@Override
	public int degree() { // returns the highest degree in the polynomial
		int degree=list.first().getExponent();
		Iterator<Term> iter = iterator();
		while(iter.hasNext()) {
			Term term = iter.next();
			if (term.getExponent()>degree) degree=term.getExponent();
		}
		return degree;
	}

	@Override
	public double evaluate(double x) { // evaluates for x
		double result=0;
		Iterator<Term> iter = iterator();
		while(iter.hasNext()) {
			result+=iter.next().evaluate(x);
		}
		return result;
	}

	@Override
	public boolean equals(Polynomial P) { // verifies two polynomials
		if (P.toString().equals(this.toString())) // by comparing the strings side by side, the equals method is less prone to error
			return true;
		return false;
	}
	public String toString() { // turns the polynomial into a string
		String s="";
		boolean first = true;
		for(Term term: list) {
			if(!first) s+="+";
			else first = false;
			s+=term.toString();
		}
		if(s.endsWith("+")) {
			char[] separated = s.toCharArray();
			s="";
			for(int i=0;i<separated.length-1;i++)s+=separated[i];
		}
		return s;
	}
	
	private void simplify() { // if the polynomial can be simplified, it will be reduced
		TermImp t1,t2;
		for(int i=0;i<list.size();i++) {
			t1 = (TermImp) list.get(i);
			for(int j=i+1;j<list.size();j++) {
				t2 = (TermImp) list.get(j);
				if(t1.getExponent()==t2.getExponent()) {
					t1.setCoefficient(t1.getCoefficient()+t2.getCoefficient());
					list.remove(t2);
				}
			}
		}sort();
		
	}
	private void sort() { // sorts the polynomial from the highest degree
		PolynomialImp sorted = new PolynomialImp();
		int degree=degree();
		Term toAdd;
		while(degree>=0) {
			for(int i=0;i<list.size();i++) {
				toAdd=list.get(i);
				if(toAdd.getExponent()==degree) sorted.list.add(toAdd);
			}
			degree--;
		}
		this.list=sorted.list;
		
	}
	public void checkZeros() { // verifies the zeros in the polynomial
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getCoefficient()==0 && list.size()!=1) list.remove(i);
		}if(list.get(0).getCoefficient()==0 && list.size()==1) ((TermImp)list.get(0)).setExponent(0);
	}
	
}
