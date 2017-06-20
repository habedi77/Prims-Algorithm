/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dMathProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dMathProject.primProcessing.*;
import javafx.scene.paint.Color;

/**
 *
 * @author Habedi
 */
public class Main extends Application
{

	private static Canvas canvas;
	private static GraphicsContext gc;
	private static CProcess process;

	@Override
	public void start(Stage stage) throws Exception
	{

		Parent root = FXMLLoader.load(
				getClass().getResource("FXMLDocument.fxml"));
//		Parent root = load.getController();
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.show();

		SplitPane sp = (SplitPane) root;
		AnchorPane ap = (AnchorPane) sp.getItems().get(1);
		canvas = (Canvas) ap.getChildren().get(0);
		gc = canvas.getGraphicsContext2D();
//		System.out.println((ap.getChildren()));
		process = new CProcess(350, 350);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		System.out.println("intilazing");
		launch(args);
	}

	public static void generate(GraphMode gm, EdgesMode em, int n)
	{

		process.startWork(gm, em, n);

		drawOnCanvas(process.getVects(), process.getAdj_matrix(),
				process.getAnswer_tree_adj_matrix(),
				(process.isModifier_available()) ? (process.getEdgesModifier()) : (null),
				(gm != GraphMode.COMPLETE || em != EdgesMode.SIMPLE_DISTANCE));
//	drawOnCanvas(process.getVects(), process.getAdj_matrix(),
//				process.getAnswer_tree_adj_matrix(),
//				(null),
//				(gm != GraphMode.COMPLETE));

	}

	private static void drawOnCanvas(Vector[] vs, boolean[][] base,
			boolean[][] answer,
			double[][] modifiers,
			boolean drawBase)
	{
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		int n = vs.length;
		boolean mods = false;
		if (modifiers != null)
			mods = true;
		gc.setGlobalAlpha(.05);
		gc.setStroke(Color.DARKSLATEGRAY);
		if (drawBase)
			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < n; j++)
				{
					if (base[i][j] == false)
						continue;
					if (mods && modifiers[i][j] > 2)
						gc.setStroke(Color.CRIMSON.darker());
					else if (mods && modifiers[i][j] < 1.2)
						gc.setStroke(Color.PALEGREEN.darker());
					else
						gc.setStroke(Color.DARKSLATEGRAY);

					gc.setLineWidth((mods == true) ? (2 + modifiers[i][j]) : (2));
					gc.strokeLine(vs[i].x, vs[i].y, vs[j].x, vs[j].y);
				}
			}

		gc.setGlobalAlpha(1);
		gc.setStroke(Color.ORANGERED);
		gc.setLineWidth(1.3);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (answer[i][j] == true)
					gc.strokeLine(vs[i].x, vs[i].y, vs[j].x, vs[j].y);

		gc.setFill(Color.LIGHTSEAGREEN);
		for (int i = 0; i < n; i++)
			gc.fillOval(vs[i].x - 3, vs[i].y - 3, 6, 6);
	}

}
