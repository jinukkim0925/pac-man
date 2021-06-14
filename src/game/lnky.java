package game;

import java.awt.Point;

import pac_man_base.pac_man_frame;
import pac_man_base.pac_man_variable;

public class lnky extends pac_man_frame {

	static Point p = pac_man_variable.lnkyPoint, subP = pac_man_variable.lnkyPoint;
	Thread th = new Thread(this);
	public static int lnkyDrawX = 0, lnkyDrawY = 0;

	public lnky() {
		// TODO Auto-generated constructor stub
		th.start();
		lnkyDrawX = pac_man_variable.lnkyPoint.x * 30;
		lnkyDrawY = pac_man_variable.lnkyPoint.y * 30;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (pac_man_variable.run) {
			try {
				if (pac_man_variable.gameTime >= 5  && pac_man.die == false) {
					int x = pac_man.pac_man.x - pac_man_variable.blinkyPoint.x;
					int y = pac_man.pac_man.y - pac_man_variable.blinkyPoint.y;
					
					if (x < 0) {
						x = pac_man.pac_man.x - x;
					} else {
						x = pac_man.pac_man.x + x;
					}
					if (y < 0) {
						y = pac_man.pac_man.y - y;
					} else {
						y = pac_man.pac_man.y + y;
					}
					boolean ch = true;
					int cnt = 0;
					if (x >= 28 || y >= 28 || x <= 0 || y <= 0) {
						x = pac_man.pac_man.x;
						y = pac_man.pac_man.y;
//						x = pac_man_variable.blinkyPoint.x;
//						y = pac_man_variable.blinkyPoint.y;
					}
					check: while (ch) {
						if (pac_man_variable.wall[x][y] == 1) {
							switch (cnt) {
							case 0:
								x++;
								break;

							case 1:
								x--;
								y++;
								break;

							case 2:
								y--;
								x--;
								break;

							case 3:
								x++;
								y--;
								break;

							default:
								x = pac_man.pac_man.x;
								y = pac_man.pac_man.y;
								break check;
							}
							cnt++;
						} else {

							break check;
						}
					}
					p = trackingMode(new Point(x, y), pac_man_variable.lnkyPoint);
					if (p.x == subP.x && p.y == subP.y) {
						p = randomPoint(pac_man_variable.lnkyPoint);
					}
					if (pac_man.frightened != 0) {
						p = randomPoint(pac_man_variable.lnkyPoint);
					}
					
					if (pac_man_variable.lnkydie) {
						p = trackingMode(new Point(13,14), pac_man_variable.lnkyPoint);
						if (p.x == subP.x && p.y == subP.y) {
							pac_man_variable.lnkydie = false;
							pac_man_variable.resurrectiongoast[2] = 1;
							pac_man_variable.diegoast[2] = 0;
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
							lnkyDrawX = subP.x * 30;
							lnkyDrawY = subP.y * 30 - i;
							break;
						}
						case 1: {
							lnkyDrawX = subP.x * 30 + i;
							lnkyDrawY = subP.y * 30;
							break;
						}
						case 2: {
							lnkyDrawX = subP.x * 30;
							lnkyDrawY = subP.y * 30 + i;
							break;
						}
						case 3: {
							lnkyDrawX = subP.x * 30 - i;
							lnkyDrawY = subP.y * 30;
							break;
						}
						default:
							break;
						}
						th.sleep(pac_man_variable.goastSpeed);
					}

					pac_man_variable.lnkyPoint = subP;

				} else {
					th.sleep(1);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
