package com.eelco.swing.screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.eelco.algoritme.BruteForce;
import com.eelco.algoritme.Clock;
import com.eelco.algoritme.NearestNeighbour;
import com.eelco.algoritme.packing.BestFit;
import com.eelco.algoritme.packing.FirstFit;
import com.eelco.algoritme.packing.FirstFitDecreasing;
import com.eelco.swing.Alert;
import com.eelco.swing.Alert.AlertType;

public class ComboBox extends JComboBox<Object> implements ActionListener{
	
	/**
	 * @author Eelco - 2015, Lars Baalmans, Jorrit Overweg
	 */
	
	private Frame frame;
	private String[] options;
	public ComboBox(Frame frame,int posY, String... s){
		this.frame=frame;
		this.options = s;
		setModel(new DefaultComboBoxModel<Object>(options));
		setSelectedIndex(0);
		addActionListener(this);
		setBounds(frame.getPanel().getX()+frame.getPanel().getWidth()+15, posY, frame.getWidth()-(frame.getPanel().getWidth()+frame.getStartButton().getWidth()+100), 20);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() instanceof JComboBox){
			if(!frame.hasFileSelected()){
				new Alert(AlertType.WARNING, "Gelieve eerst een XML bestand te uploaden.");
				return;
			}
			String option = this.getSelectedItem().toString();
			switch(option.toLowerCase()){
			case "clock": frame.getPanel().setAlgorithm(new Clock());break;
			case "brute force": frame.getPanel().setAlgorithm(new BruteForce());break;
			case "nearest neighbour": frame.getPanel().setAlgorithm(new NearestNeighbour());break;
			case "first fit": for(int p = 1; p < 5; p++)
				Frame.getInstance().getCurrentLog(p).log("Algorithm: First Fit");
				frame.getBppPanel().setAlgorithm(new FirstFit()); break;
            case "best fit":  for(int p = 1; p < 5; p++)
            	Frame.getInstance().getCurrentLog(p).log("Algorithm: Best Fit");
            	frame.getBppPanel().setAlgorithm(new BestFit()); break;
            case "first fit decreasing": for(int p = 1; p < 5; p++)
            	Frame.getInstance().getCurrentLog(p).log("Algorithm: First Fit Decreasing"); 
            	frame.getBppPanel().setAlgorithm(new FirstFitDecreasing()); break;
			default: new Alert(AlertType.NOTIFICATION, "Gelieve een algoritme te selecteren.");break;
			}
			frame.getBppPanel().resetPages();
			frame.getNextBinBtn().setEnabled(true);
		}
	}
	
}
