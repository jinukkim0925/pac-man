package game;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import pac_man_base.pac_man_frame;
import pac_man_base.pac_man_variable;

public class blinky extends pac_man_frame {

	static Point p = pac_man_variable.blinkyPoint, subP = pac_man_variable.blinkyPoint;
	Thread th = new Thread(this);
	public static int blinkyDrawX = 0, blinkyDrawY = 0;

	public blinky() {
		// TODO Auto-generated constructor stub
		th.start();
		blinkyDrawX = pac_man_variable.blinkyPoint.x * 30;
		blinkyDrawY = pac_man_variable.blinkyPoint.y * 30;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (pac_man_variable.run) {
			try {
				if (pac_man_variable.gameTime >= 1 && pac_man.die == false) {
					p = trackingMode(pac_man.pac_man, pac_man_variable.blinkyPoint);
//					p = disperseMode(pac_man.pac_man, pac_man_variable.blinkyPoint);
					if (p.x == subP.x && p.y == subP.y) {
						p = randomPoint(pac_man_variable.blinkyPoint);
					}
					if (pac_man.frightened != 0) {
						p = randomPoint(pac_man_variable.blinkyPoint);
					}
					if (pac_man_variable.blinkydie) {
						p = trackingMode(new Point(13,14), pac_man_variable.blinkyPoint);
						if (p.x == subP.x && p.y == subP.y) {
							pac_man_variable.blinkydie = false;
							pac_man_variable.resurrectiongoast[0] = 1;
							pac_man_variable.diegoast[0] = 0;
						}
					}
					int where = 0;
					if (p.x > subP.x) {
						where = 3;
					}
					if (p.x < subP.x) {
						where = 1;
					}
					if (p.y > subP.y) {
						where = 0;
					}
					if (p.y < subP.y) {
						where = 2;
					}
//					System.out.println(where);
					subP = p;
					for (int i = 30; i > 0; i--) {
						switch (where) {
						case 0: {
							blinkyDrawX = subP.x * 30;
							blinkyDrawY = subP.y * 30 - i;
							break;
						}
						case 1: {
							blinkyDrawX = subP.x * 30 + i;
							blinkyDrawY = subP.y * 30;
							break;
						}
						case 2: {
							blinkyDrawX = subP.x * 30;
							blinkyDrawY = subP.y * 30 + i;
							break;
						}
						case 3: {
							blinkyDrawX = subP.x * 30 - i;
							blinkyDrawY = subP.y * 30;
							break;
						}
						default:
							break;
						}
						th.sleep(pac_man_variable.goastSpeed);
					}

					pac_man_variable.blinkyPoint = subP;
					
				}else {
					th.sleep(1);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
