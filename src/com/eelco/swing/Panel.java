package com.eelco.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import com.eelco.algoritme.Clock;
import com.eelco.algoritme.TSPAlgorithmBase;
import com.eelco.mechanics.Coordinaat;
import com.eelco.swing.screen.Frame;

public class Panel extends JPanel{
	
	/**
	 * @author Eelco - 2015, Darryn van Barneveld
	 */
	
	private Frame frame;
	private TSPAlgorithmBase algo = null;
	private List<Coordinaat> drawList;
	private boolean canDrawPath = false; /*Do not change. (Object boolean for drawing the path)*/
	private final int DOT_SIZE = 10; /* Size of coordinaat dots */
	private final int WIDTH = 350; /* Size of panel */
	private final int HEIGHT = 350; /* Size of panel */
	private final int GRID_SIZE = 5; /* Size of your grid */
	private final int REC_SIZE = WIDTH/GRID_SIZE-1; /* Do not change. (size of rectangles from grid) */
	private final double START_X = 4;
	private final double START_Y = 0;
	
	public Panel(Frame frame){
		super.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.frame = frame;
		setBounds(frame.getStartButton().getX()+frame.getStartButton().getWidth()+25, 15, WIDTH, HEIGHT);
	}
	
	/**
	 * @return Instance of Frame (Main frame)
	 */
	public Frame getFrame(){
		return frame;
	}
	
	@Override
	public void paintComponent(Graphics graph){
		super.paintComponent(graph);
		Graphics2D g = (Graphics2D) graph;
		g.setColor(Color.BLACK);
		/* Draw a grid */
		for(int y = 0; y < GRID_SIZE; y++){
			for(int x = 0; x < GRID_SIZE; x++){
				g.drawRect(x*REC_SIZE, y*REC_SIZE, REC_SIZE, REC_SIZE);
			}
		}
		if(canDrawPath){
			/* Below is for drawing the path */
			for(Coordinaat c : drawList){ /* Loop through list of coordinates*/
				g.setColor(Color.RED);
				g.fillOval(getFrameX(c), getFrameY(c), DOT_SIZE, DOT_SIZE); /* Create an dot in the center*/
				try{
					g.setColor(Color.BLUE);
					Coordinaat next = drawList.get(drawList.indexOf(c)+1); /* Check if there is a next upcoming coordinaat */
					g.drawLine(getFrameX(c)+(DOT_SIZE/2), getFrameY(c)+(DOT_SIZE/2), getFrameX(next)+(DOT_SIZE/2), getFrameY(next)+(DOT_SIZE/2)); /* Draw a line to the next coordinaat center*/
				}catch(IndexOutOfBoundsException _e){
					super.repaint(); /* No more coordinates found, repaint to finish*/
					break;
				}
			}
		}
	}
	
	/**
	 * Calculates dynamic placement of X coordinaat by given specs
	 * @param c Coordinaat object
	 * @return Calculated X coordinaat
	 */
	private int getFrameX(Coordinaat c){ return (int)c.getX()*(WIDTH/GRID_SIZE)+(WIDTH/GRID_SIZE/2)-GRID_SIZE; }
	
	/**
	 * Calculates dynamic placement of Y coordinaat by given specs
	 * @param c Coordinaat object
	 * @return Calculated Y coordinaat
	 */
	private int getFrameY(Coordinaat c){ return (int)(GRID_SIZE-c.getY()-1)*(HEIGHT/GRID_SIZE)+(HEIGHT/GRID_SIZE/2)-GRID_SIZE; }
	
	/**
	 * Choose whether you want to draw or not.
	 * @param candraw True or false, depends on your choice.
	 */
	public void setDrawPath(boolean candraw){
		canDrawPath = candraw;
		if(algo==null) canDrawPath = false;
		this.repaint();
	}
	
	/**
	 * Set algorithm to draw path
	 * @param algo Algorithm
	 */
	public void setAlgorithm(TSPAlgorithmBase algo){
		this.algo = algo;
		setDrawPath(true);
		drawList = algo.getFinalResult(START_X, START_Y);
		frame.getLogger().log("Algoritme: "+algo.getClass().getSimpleName()+", wordt getekend.");
		frame.getTSPInfoLabel().setText("Distance: "+algo.getTotalDistance(START_X, START_Y));
		this.repaint();
	}
	
	/**
	 * Gets current algorithm
	 * @return Algorithm (TSP)
	 */
	public TSPAlgorithmBase getCurrentAlgorithm(){
		if(algo ==null) algo = new Clock();
		return algo;
	}
	
	public double getStartX(){ return START_X; }
	public double getStartY(){ return START_Y; }
}
