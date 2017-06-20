/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dMathProject.primProcessing;

import dMathProject.EdgesMode;
import dMathProject.GraphMode;

/**
 *
 * @author Habedi
 */
final public class CProcess
{

	private int noOfV;
	private int noOfE;
	private double[][] edgesModifier;
	private boolean modifier_available;
	private boolean[][] adj_matrix;
	private boolean[][] answer_tree_adj_matrix;
	private Vector[] vects;

	private double dx, dy;

	public CProcess()
	{
		dx = dy = 290;
	}

	public CProcess(double x, double y)
	{
		dx = x - 10;
		dy = y - 10;
	}

	public void startWork(GraphMode gm, EdgesMode em, int n)
	{
		//INIT variables
		noOfV = n;

		adj_matrix = new boolean[n][n];
		answer_tree_adj_matrix = new boolean[n][n];
		edgesModifier = new double[n][n];
		vects = new Vector[n];
		System.gc();

		fillVecs();

		if (em == EdgesMode.DISTANCE_W_MODIFIER)
		{
			modifier_available = true;
			fillModifierGraph();
		}
		else
			modifier_available = false;

		if (gm == GraphMode.COMPLETE)
			makeFullGraph();
		else
			makeConnectedGraphV01();

		startSearch();
//		printGraph(adj_matrix, "start graph");
//		printGraph(answer_tree_adj_matrix, "answer");

	}

	public int getNoOfE()
	{
		return noOfE;
	}

	public double[][] getEdgesModifier()
	{
		return edgesModifier;
	}

	public boolean isModifier_available()
	{
		return modifier_available;
	}

	public boolean[][] getAdj_matrix()
	{
		return adj_matrix;
	}

	public boolean[][] getAnswer_tree_adj_matrix()
	{
		return answer_tree_adj_matrix;
	}

	public Vector[] getVects()
	{
		return vects;
	}

	public int getNoOfV()
	{
		return noOfV;
	}

	private double getMassOfEdge(int v1, int v2)
	{
		if (modifier_available == false)
			return 1;
		else
			return edgesModifier[v1][v2];

	}

	private double getEdgesDistance(int v1, int v2)
	{
		return vects[v1].getDistTo(vects[v2]);
	}

	private void fillVecs()
	{
		double x, y;
		for (int i = 0; i < noOfV; i++)
		{
			x = Math.random() * dx + 10;
			y = Math.random() * dy + 10;
//			System.out.println(x+":"+y);
			vects[i] = new Vector(x, y);

		}
	}

	/**
	 * Creates a Central node
	 */
	private void makeConnectedGraphV01()
	{
		double x, y;
		for (int i = 0; i < noOfV; i++)
		{
			for (int j = 0; j < noOfV; j++)
			{
				if (i == j)
				{
					continue;
				}
				else if (Math.random() > .5)
				{
					adj_matrix[j][i] = true;
					adj_matrix[i][j] = true;
					noOfE++;
				}
				else
				{
					adj_matrix[j][i] = false;
					adj_matrix[i][j] = false;
				}

			}

		}
		int n = (int) (Math.random() * noOfV);
		for (int i = 0; i < noOfV; i++)
		{
			adj_matrix[i][n] = true;
			adj_matrix[n][i] = true;
		}
		adj_matrix[n][n] = false;
	}

	/**
	 * for 2D based graph, full
	 */
	private void makeFullGraph()
	{
		double x, y;
		for (int i = 0; i < noOfV; i++)
		{
			for (int j = 0; j < noOfV; j++)
			{
				if (i == j)
				{
					continue;
				}
				adj_matrix[j][i] = true;
				adj_matrix[i][j] = true;
				noOfE++;

			}
		}

	}

	private void startSearch()
	{
		boolean[] counted_in_vertices = new boolean[noOfV];
		boolean done = false;

		double min_mass, tmp;
		counted_in_vertices[0] = true;
		int mm_v1_i, mm_v2_i;//Min mass Vertex index
		while (!done)
		{
			min_mass = Integer.MAX_VALUE;
			mm_v1_i = mm_v2_i = -1;
			for (int i = 0; i < noOfV; i++)
			{
				if (counted_in_vertices[i] == true)
				{

					for (int j = 0; j < noOfV; j++)
					{
						if (counted_in_vertices[j] == false)
						{
							if (adj_matrix[i][j] == true)
							{
								tmp = getEdgesDistance(i, j);
								tmp *= getMassOfEdge(i, j);
//								System.out.printf(" %d to %d is %.2f\n",i,j,tmp);
								if (tmp < min_mass)
								{
									mm_v1_i = i;
									mm_v2_i = j;
									min_mass = tmp;
								}
							}

						}
					}
				}
			}
			answer_tree_adj_matrix[mm_v1_i][mm_v2_i] = true;
			answer_tree_adj_matrix[mm_v2_i][mm_v1_i] = true;
//			System.err.printf("added %d:%d with mass of%.2f\n", mm_v1_i, mm_v2_i, min_mass);
			counted_in_vertices[mm_v2_i] = true;

			done = true;
			for (boolean b : counted_in_vertices)
			{
				if (b == false)
				{
					done = false;
					break;
				}
			}

		}

	}

	private void fillModifierGraph()
	{
		for (int i = 0; i < noOfV; i++)
			for (int j = 0; j < noOfV; j++)
			{
				edgesModifier[i][j]
						= edgesModifier[j][i]
						= 2.8 - Math.log10(Math.random() * 100 + 1);
			}
	}

	public void printGraph(boolean[][] g, String name)
	{
		System.out.printf("Grpah:%s:\n", name);
		for (int i = 0; i < noOfV; i++)
		{
			for (int j = 0; j < noOfV; j++)
			{
				System.out.printf("%d ", (g[i][j] == true) ? 1 : 0);
			}
			System.out.printf("\n");
		}
	}

}
