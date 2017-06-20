/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dMathProject.primProcessing;

/**
 *
 * @author Habedi
 */
public class Vector
{

	public double x, y;

	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector(Vector v)
	{
		this.x = v.x;
		this.y = v.y;
	}

	public Vector()
	{

	}

	public double getLen()
	{
		return Math.hypot(x, y);
	}

	public double getDistTo(Vector v)
	{
		return Math.hypot(x - v.x, y - v.y);
	}

}
