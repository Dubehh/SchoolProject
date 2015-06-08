package com.eelco;

import com.eelco.mechanics.Database;
import com.eelco.swing.screen.Frame;

public class Main {
	
	public static void main(String[] args){
		new Main();
	}
	
	public Main(){
		Frame.getInstance();
		Database.getInstance();
	}
}
