package instructions;

public class MathOperations {

	public MathOperations() {
		// TODO Auto-generated constructor stub
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
	
	protected double sin(double degrees){
		return Math.sin( Math.toRadians(degrees) );
	}
	
	protected double cos(double degrees){
		return Math.cos( Math.toRadians(degrees) );
	}
	
	protected double tan(double degrees){
		return Math.tan( Math.toRadians(degrees) );
	}
	
	protected double atan(double degrees){
		return Math.atan( Math.toRadians(degrees) );
	}
	
	protected double log(double expr){
		return Math.log(expr);
	}
	
	protected double pow(double base, double exponent){
		return Math.pow(base, exponent);
	}
	
	protected double pi(){
		return Math.PI;
	}

}
