package game;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import pac_man_base.pac_man_frame;
import pac_man_base.pac_man_variable;

public class pinky extends pac_man_frame {

	static Point p = pac_man_variable.pinkyPoint, subP = pac_man_variable.pinkyPoint;
	Thread th = new Thread(this);
	public static int pinkyDrawX = 0, pinkyDrawY = 0;

	public pinky() {
		// TODO Auto-generated constructor stub
		th.start();
		pinkyDrawX = pac_man_variable.pinkyPoint.x * 30;
		pinkyDrawY = pac_man_variable.pinkyPoint.y * 30;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (pac_man_variable.run) {
			try {
				if (pac_man_variable.gameTime >= 3  && pac_man.die == false) {

					int gg = 4, cnt = 0;
					int x = pac_man.pac_man.x;
					int y = pac_man.pac_man.y;
					int chx = 0;
					int chy = 0;
					boolean ch = true;

					check: while (ch) {
						if (chx >= 28 || chy >= 28 || chx <= 0 || chy <= 0) {
							switch (cnt) {
							case 0:
								chx = x + gg;
								break;
							case 1:
								chx = x - gg;
								break;
							case 2:
								chy = y + gg;
								break;
							case 3:
								chy = y - gg;
								break;
							default:
								cnt = 0;
								if (gg < 0) {
									gg--;
								} else {
									gg++;
								}
								if (gg > 10) {
									gg = -4;
								}
								break;
							}
						}else if (pac_man_variable.wall[chx][chy] == 1) {
							switch (cnt) {
							case 0:
								chx = x + gg;
								break;
							case 1:
								chx = x - gg;
								break;
							case 2:
								chy = y + gg;
								break;
							case 3:
								chy = y - gg;
								break;
							default:
								cnt = 0;
								if (gg < 0) {
									gg--;
								} else {
									gg++;
								}
								if (gg > 10) {
									gg = -4;
								}
								break;
							}
						} else {
							break check;
						}
						cnt++;
					}
					x = chx;
					y = chy;
//					System.out.println(new Point(x, y) + "/" + pac_man.pac_man);
					p = trackingMode(new Point(x, y), pac_man_variable.pinkyPoint);
					if (p.x == subP.x && p.y == subP.y) {
						p = randomPoint(pac_man_variable.pinkyPoint);
					}
					if (pac_man.frightened != 0) {
						p = randomPoint(pac_man_variable.pinkyPoint);
					}
					
					if (pac_man_variable.pinkydie) {
						p = trackingMode(new Point(13,14), pac_man_variable.pinkyPoint);
						if (p.x == subP.x && p.y == subP.y) {
							pac_man_variable.pinkydie = false;
							pac_man_variable.resurrectiongoast[1] = 1;
							pac_man_variable.diegoast[1] = 0;
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
							pinkyDrawX = subP.x * 30;
							pinkyDrawY = subP.y * 30 - i;
							break;
						}
						case 1: {
							pinkyDrawX = subP.x * 30 + i;
							pinkyDrawY = subP.y * 30;
							break;
						}
						case 2: {
							pinkyDrawX = subP.x * 30;
							pinkyDrawY = subP.y * 30 + i;
							break;
						}
						case 3: {
							pinkyDrawX = subP.x * 30 - i;
							pinkyDrawY = subP.y * 30;
							break;
						}
						default:
							break;
						}
						th.sleep(pac_man_variable.goastSpeed);
					}

					pac_man_variable.pinkyPoint = subP;

				} else {
					th.sleep(1);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
