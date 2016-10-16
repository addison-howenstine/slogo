package slogo_controller;

public class MathOperations {
	public MathOperations() {
	}
	
	protected double sum(double expr1, double expr2){
		return expr1 + expr2;
	}
	
	protected double difference(double expr1, double expr2){
		return expr1 - expr2;
	}
	
	protected double product(double expr1, double expr2){
		return expr1 * expr2;
	}
	
	protected double quotient(double expr1, double expr2){
		return expr1 / expr2;
	}
	
	protected double remainder(double expr1, double expr2){
		return expr1 % expr2;
	}
	
	protected double minus(double expr){
		return (-1) * expr;
	}
	
	protected double random(double max){
		return Math.random() * max;
	}
	
	protected double sine(double degrees){
		return Math.sin( Math.toRadians(degrees) );
	}
	
	protected double cosine(double degrees){
		return Math.cos( Math.toRadians(degrees) );
	}
	
	protected double tangent(double degrees){
		return Math.tan( Math.toRadians(degrees) );
	}
	
	protected double arcTangent(double degrees){
		return Math.atan( Math.toRadians(degrees) );
	}
	
	protected double naturalLog(double expr){
		return Math.log(expr);
	}
	
	protected double power(double base, double exponent){
		return Math.pow(base, exponent);
	}
	
	protected double pi(){
		return Math.PI;
	}
}