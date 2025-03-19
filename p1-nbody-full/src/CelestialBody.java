

/**
 * Celestial Body class for NBody
 * Modified from original Planet class
 * used at Princeton and Berkeley
 * @author ola
 * @author Aidan Batista
 */

 import java.lang.Math;

public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;

	}

	/**
	 *
	 * @return
	 */
	public double getX() {
		return myXPos;
	}

	/**
	 *
	 * @return
	 */
	public double getY() {
		return myYPos;
	}

	/**
	 * Accessor for the x-velocity
	 * @return the value of this object's x-velocity
	 */
	public double getXVel() {
		return myXVel;
	}
	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */
	public double getYVel() {
		return myYVel;
	}

	/**
	 *
	 * @return
	 */
	public double getMass() {
		return myMass;
	}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		double dx = myXPos - b.getX();
		double dy = myYPos - b.getY();
		double r2 = dx*dx + dy*dy;
		return Math.sqrt(r2);
	}

	public double calcForceExertedBy(CelestialBody b) {
		double m1 = myMass;
		double m2 = b.getMass();
		double G = 6.67 * 1e-11;
		double r2 = calcDistance(b)*calcDistance(b);
		return G * ((m1*m2)/(r2));
	}

	public double calcForceExertedByX(CelestialBody b) {
		double F = calcForceExertedBy(b);
		double dx = b.getX() - myXPos;
		double r = calcDistance(b);
		return F * dx / r;
	}
	public double calcForceExertedByY(CelestialBody b) {
		double F = calcForceExertedBy(b);
		double dy = b.getY() - myYPos;
		double r = calcDistance(b);
		return F * (dy / r);
	}

	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double sum = 0.0;
		for (CelestialBody b : bodies) {
			if (! b.equals(this)) {
				sum += calcForceExertedByX(b);
			}
		}
		return sum;
	}

	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double sum = 0.0;
		for (CelestialBody b : bodies) {
			if (! b.equals(this)) {
				sum += calcForceExertedByY(b);
			}
		}
		return sum;
	}

	public void update(double deltaT, 
			           double xforce, double yforce) {
		double ax = xforce / myMass;
		double ay = yforce / myMass;
		double nvx = myXVel + deltaT*ax;
		double nvy = myYVel + deltaT*ay;
		double nx = myXPos + deltaT*nvx;
		double ny = myYPos + deltaT*nvy;
		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;
	}

	/**
	 * Draws this planet's image at its current position
	 */
	public void draw() {
		StdDraw.picture(myXPos,myYPos,"images/"+myFileName);
	}
}
