package com.eelco.swing.screen;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.eelco.core.Artikel;
import com.eelco.core.Klant;
import com.eelco.core.Order;

public class OrderFrame extends JFrame{
	
	/**
	 * @author Eelco
	 */
	private final int WIDTH = 350;
	private final int HEIGHT = 700;
	private static OrderFrame instance = null;
	private OrderFrame(Frame frame){
		Order order = Order.getCurrentOrder();
		Klant klant = order.getKlant();
		setTitle("Pakbon");
		setResizable(false);
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		add(label(WIDTH/2-100, 25, 200, 50, "Klant Informatie"));
		add(getInformation(WIDTH/2-105, 80, 200, 100, klant.getNaam()+" "+klant.getAchternaam(), klant.getAdres(), klant.getPostcode()+" "+klant.getWoonplaats()));
		add(label(WIDTH/2-100, 75+100+30, 200, 50, "Order Informatie"));
		String[] artikelen = new String[order.getArtikelen().length];
		for(int i = 0; i < order.getArtikelen().length; i++){
			Artikel a = order.getArtikelen()[i];
			artikelen[i] = a.getDesc()+" ▶  Bin "+a.getBin()+"";
		}
		add(getInformation(WIDTH/2-105, 75+100+30+5+50, 200, 300, artikelen));
		setVisible(true);
	}
	
	public static OrderFrame getFrame(Frame frame){
		if(instance == null) instance = new OrderFrame(frame);
		return instance;
	}
	
	private JLabel label(int x, int y, int w, int h, String txt){
		JLabel lbl = new JLabel(txt, SwingConstants.CENTER);
		int fontSize = 20;
		lbl.setOpaque(true);
		lbl.setBackground(Color.WHITE);
		lbl.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true));
		lbl.setBounds(x-(fontSize-14), y, w, h);
		lbl.setFont(new Font(lbl.getFont().getFontName(), Font.BOLD, fontSize));
		return lbl;
	}
	
	/**
	 * Get text area
	 * @param x int
	 * @param y int
	 * @param w int
	 * @param h int
	 * @param txt String[]
	 * @return Text area
	 */
	private JTextArea getInformation(int x, int y, int w, int h, String... txt){
		JTextArea area = new JTextArea();
		area.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		area.setBounds(x, y, w, h);
		area.setEditable(false);
		for(String s : txt){
			area.append("  "+s+"\n");
		}
		return area;
	}

}
