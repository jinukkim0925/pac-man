package game;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import pac_man_base.pac_man_frame;
import pac_man_base.pac_man_variable;

public class clyde extends pac_man_frame {

	static Point p = pac_man_variable.clydePoint, subP = pac_man_variable.clydePoint;
	Thread th = new Thread(this);
	public static int clydeDrawX = 0, clydeDrawY = 0;

	public clyde() {
		// TODO Auto-generated constructor stub
		th.start();
		clydeDrawX = pac_man_variable.clydePoint.x * 30;
		clydeDrawY = pac_man_variable.clydePoint.y * 30;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (pac_man_variable.run) {
			try {
				if (pac_man_variable.gameTime >= 7  && pac_man.die == false) {

					if (findDistance(pac_man.pac_man, pac_man_variable.clydePoint) <= 8) {
						p = disperseMode(pac_man.pac_man, pac_man_variable.clydePoint);
					} else {
						p = trackingMode(pac_man.pac_man, pac_man_variable.clydePoint);
					}
					
					if (p.x == subP.x && p.y == subP.y) {
						p = randomPoint(pac_man_variable.clydePoint);
					}
					if (pac_man.frightened != 0) {
						p = randomPoint(pac_man_variable.clydePoint);
					}
					if (pac_man_variable.clydedie) {
						p = trackingMode(new Point(13,14), pac_man_variable.clydePoint);
						if (p.x == subP.x && p.y == subP.y) {
							pac_man_variable.clydedie = false;
							pac_man_variable.resurrectiongoast[3] = 1;
							pac_man_variable.diegoast[3] = 0;
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
					subP = p;
					for (int i = 30; i > 0; i--) {
						switch (where) {
						case 0: {
							clydeDrawX = subP.x * 30;
							clydeDrawY = subP.y * 30 - i;
							break;
						}
						case 1: {
							clydeDrawX = subP.x * 30 + i;
							clydeDrawY = subP.y * 30;
							break;
						}
						case 2: {
							clydeDrawX = subP.x * 30;
							clydeDrawY = subP.y * 30 + i;
							break;
						}
						case 3: {
							clydeDrawX = subP.x * 30 - i;
							clydeDrawY = subP.y * 30;
							break;
						}
						default:
							break;
						}
						th.sleep(pac_man_variable.goastSpeed);
					}

					pac_man_variable.clydePoint = subP;

				} else {
					th.sleep(1);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
