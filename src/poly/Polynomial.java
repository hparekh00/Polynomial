package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		//Created pointers for input polynomials
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		//Create Output pointer for nodes 
		Node first = null;
		Node moving = null;
		//Conditions if any inputs are null
		if(poly1 == null) {
			return poly2;
		}
		if(poly2 == null) {
			return poly1;
		}
		
		while(ptr1 != null && ptr2 != null) {
			Node temp = new Node (ptr1.term.coeff, ptr1.term.degree, null);
			if(ptr1.term.degree == ptr2.term.degree) {
				temp.term.coeff = ptr1.term.coeff + ptr2.term.coeff;
				ptr1 = ptr1.next;
				ptr2 = ptr2.next;	
			}
			else if(ptr1.term.degree < ptr2.term.degree) {
				temp.term.degree = ptr1.term.degree;
				temp.term.coeff = ptr1.term.coeff;
				ptr1 = ptr1.next;
			}
			else if(ptr2.term.degree < ptr1.term.degree) {
				temp.term.degree = ptr2.term.degree;
				temp.term.coeff = ptr2.term.coeff;
				ptr2 = ptr2.next;
			}
			if(first == null) { //Will let first node be connected to ptr first and also establish ptr moving
				first = temp;
				moving = temp;
			}else {
				moving.next = temp;  //Change ptr moving's next direction link to the new node
				moving = temp;       //Change ptr location to the new node
			}
		}
		
		while(ptr1 != null) {
			Node temp = new Node (ptr1.term.coeff, ptr1.term.degree, null);
			ptr1 = ptr1.next;
			moving.next = temp;
			moving = temp;
		}
		while(ptr2 != null) {
			Node temp = new Node (ptr2.term.coeff, ptr2.term.degree, null);
			ptr2 = ptr2.next;
			moving.next = temp;
			moving = temp;
		}
		
		//Will remove zeroes
		moving = first.next;
		Node prev = first;
		while(moving != null) {
			if(moving.term.coeff == 0 && moving.term.degree != 0) {
				prev.next = moving.next;
				moving = moving.next;
			}else {
				moving = moving.next;
				prev = prev.next;
			}
		}
		
		return first;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		Node first = null;
		Node ptr3 = null;
		Node templl = null;
		
		
		while(ptr1 != null) {
			while(ptr2 != null) {
				Node temp = new Node(ptr1.term.coeff, ptr1.term.degree, null);
				temp.term.degree = ptr1.term.degree + ptr2.term.degree;
				temp.term.coeff  = ptr1.term.coeff * ptr2.term.coeff;
				
				if(first == null) {
					first = temp;
					ptr3 = temp;
				}else {
					ptr3.next = temp;
					ptr3 = temp;
				}
				ptr2 = ptr2.next;
			}
			templl = add(templl, first);
			first = null;
			ptr2 = poly2;
			ptr1 = ptr1.next;
		}
		/*
		Node ptr1 = poly1;//Assign ptrs to input polynomials
		Node ptr2 = poly2;
		
		Node first1 = null;
		Node ptr3 = null;
		
		while(ptr1 != null) {
			while(ptr2 != null) {
				Node temp = new Node(ptr1.term.coeff, ptr1.term.degree, null);
				temp.term.degree = ptr1.term.degree + ptr2.term.degree;
				temp.term.coeff  = ptr1.term.coeff * ptr2.term.coeff;
				if(first1 == null) {
					first1 = temp;
					ptr3 = temp;
				}else {
					ptr3.next = temp;
					ptr3 = temp;
				}
				ptr2 = ptr2.next;
			}
			ptr1 = ptr1.next;
			ptr2 = poly2;
		}
		ptr3 = first1;//keep stationary near front of LL
		
		Node front = null;
		Node prev = null;
		
		
		Node first2 = null;
		Node ptr5 = null;
		
		while(ptr3 != null) {
			Node temp = new Node(ptr3.term.coeff, ptr3.term.degree, null);
			
			front = ptr3.next;
			prev = ptr3;
			while(front != null) {
				if(ptr3.term.degree == front.term.degree) {
					temp.term.coeff += front.term.coeff;
					front = front.next;//move front
					prev.next = front;//delete node
				}else {
					front = front.next;
					prev = prev.next;
				}
			}
			
			if(first2 == null) {//now link new temp node to the output LL
				first2 = temp;
				ptr5   = temp;
			}else {
				ptr5.next = temp;
				ptr5 = temp;
			}
			ptr3 = ptr3.next;//move ptr3 to next node
		}
		
		Node prev2 = null;
		Node front2 = null;
		Node ptr = null;
		
		ptr = first2;
		prev2 = ptr;
		front2 = ptr.next;
		
		while(ptr != null) {
			Node goku = new Node(ptr.term.coeff, ptr.term.degree, null);
			while(front2 != null) {
				if(ptr.term.degree > front2.term.degree) {
					goku.term.degree = front2.term.degree;
					goku.term.coeff = front2.term.coeff;
					front2 = front2.next;
					
				}
			}
		}
		*/
		return templl;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		Node ptr = poly;
		float value = 0;
		while(ptr != null) {
			value += (float) ((ptr.term.coeff) * (Math.pow(x,ptr.term.degree)));
			ptr = ptr.next;
		}
		return value;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
