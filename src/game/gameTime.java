package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import pac_man_base.pac_man_variable;

public class gameTime {

	public gameTime() {
		// TODO Auto-generated constructor stub
		tm.start();
	}
	
	Timer tm = new Timer(1000, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			pac_man_variable.gameTime++;
		}
	});
	
}
