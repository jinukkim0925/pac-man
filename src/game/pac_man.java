package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import pac_man_base.pac_man_frame;
import pac_man_base.pac_man_variable;

@SuppressWarnings("serial")
public class pac_man extends pac_man_frame {

	public static Point pac_man = new Point();
	int wherePac = 2, mouseSize = 0, drawPacX = 0, drawPacY = 0, draw = 0, sub = 3, where = 3;
	// 0 == up, 1 == right, 2 == bottom, 3 == left;
	Thread th = new Thread(this);
	public static boolean run = true, turn = true, die = false;

	DecimalFormat format = new DecimalFormat("00000");
	public static int score = 0, max_score = 0, frightened = 0;

	public pac_man() {
		// TODO Auto-generated constructor stub
		fs("팩맨");

		size(cp, 840, 840);
		cp.setBackground(Color.black);

		cp.add(ap = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) { // 배경, 먹이 그리기
				// TODO Auto-generated method stub
				super.paintComponent(g);
				for (int i = 0; i < 840; i = i + 30) {
					for (int j = 0; j < 840; j = j + 30) {
						g.setColor(new Color(36, 36, 223));
						// 곧 삭제
						g.drawRect(i, j, 30, 30);
						if (pac_man_variable.wall[i / 30][j / 30] == 1) {
							g.fillRect(i, j, 30, 30);
						}
						g.setColor(new Color(255, 184, 151));
						if (pac_man_variable.feed[i / 30][j / 30] == 1) {
							g.fillOval(i + 10, j + 10, 10, 10);
						}
						if (pac_man_variable.superFeed[i / 30][j / 30] == 1) {
							g.fillOval(i + 5, j + 5, 20, 20);
						}
						g.setColor(new Color(255, 188, 255));
						if (pac_man_variable.gate[i / 30][j / 30] == 1) {
							g.fillRect(i, j, 30, 30);
						}
					}
				}
			}
		});

		ap.setBackground(Color.black);
		size(ap, 840, 840);
		pac_man.x = 14;
		pac_man.y = 20;
//		pac_man.x = 1;
//		pac_man.y = 26;

		// 팩맨그리기
		ap.add(pac = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				g.setColor(Color.yellow);
				// 0 == up, 1 == right, 2 == bottom, 3 == left;
				if (run) {
					switch (wherePac) {
					case 0: {
						drawPacY = -draw;
						drawPacX = 0;
						break;
					}
					case 1: {
						drawPacX = draw;
						drawPacY = 0;
						break;
					}
					case 2: {
						drawPacY = draw;
						drawPacX = 0;
						break;
					}
					case 3: {
						drawPacX = -draw;
						drawPacY = 0;
						break;
					}
					default:

						break;
					}
					if (die == false) {
						g.fillArc(pac_man.x * 30 - drawPacX, pac_man.y * 30 - drawPacY, 30, 30, -wherePac * 90 + 135,
								270 + mouseSize);
					}
				}

			}
		});
		pac.setOpaque(false);
		size(pac, 840, 840);

		// 유령그리기
		blinky b = new blinky();
		pinky p = new pinky();
		lnky l = new lnky();
		clyde c = new clyde();
		pac.add(goastPanel = new JPanel(new BorderLayout()) {
			protected void paintComponent(Graphics g) {
				ImageIcon blinky = new ImageIcon("image/blinky.png");
				if (frightened != 0) {
					blinky = new ImageIcon("image/scare.png");
				}

				ImageIcon pinky = new ImageIcon("image/pinky.png");
				if (frightened != 0) {
					pinky = new ImageIcon("image/scare.png");
				}

				ImageIcon clyde = new ImageIcon("image/clyde.png");
				if (frightened != 0) {
					clyde = new ImageIcon("image/scare.png");
				}

				ImageIcon lnky = new ImageIcon("image/lnky.png");
				if (frightened != 0) {
					lnky = new ImageIcon("image/scare.png");
				}

				Image imblinky = blinky.getImage();
				Image impinky = pinky.getImage();
				Image imclyde = clyde.getImage();
				Image imlnky = lnky.getImage();
				
				if (die == false) {
					g.drawImage(imblinky, b.blinkyDrawX, b.blinkyDrawY, 30, 30, this);
					g.drawImage(impinky, p.pinkyDrawX, p.pinkyDrawY, 30, 30, this);
					g.drawImage(imclyde, c.clydeDrawX, c.clydeDrawY, 30, 30, this);
					g.drawImage(imlnky, l.lnkyDrawX, l.lnkyDrawY, 30, 30, this);
				}
				

			};
		});

		goastPanel.setOpaque(false);
		size(goastPanel, 840, 840);

		// 위에 점수 표시
		np.add(ap = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.white);
				g2.setFont(new Font("Shylock NBP", Font.BOLD, 30));
				g2.drawString(score + "", 30, 50);

			}
		});

		size(np, 840, 100);
		ap.setBackground(Color.black);

		// 아래
		sp.add(ap = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
			}
		});

		size(sp, 840, 50);
		ap.setBackground(Color.black);

		tm1.start();
		th.start();

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int x = pac_man.x;
				int y = pac_man.y;
				switch (e.getKeyCode()) {
				case 37: {
					if (pac_man_variable.wall[--x][y] == 0) {
						sub = 3;
					}
					break;
				}
				case 38: {
					if (pac_man_variable.wall[x][--y] == 0) {
						sub = 0;
					}
					break;
				}
				case 39: {
					if (pac_man_variable.wall[++x][y] == 0) {
						sub = 1;
					}
					break;
				}
				case 40: {
					if (pac_man_variable.wall[x][++y] == 0) {
						sub = 2;
					}
					break;
				}
				default:
					break;
				}
			}
		});

		// 게임 타이머
		new gameTime();
		sh();
	}

	Timer tm1 = new Timer(100, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (mouseSize == 90) {
				mouseSize = 0;
			} else {
				mouseSize = 90;
			}
			if (frightened != 0) {
				frightened--;
			}
			repaint();
		}
	});

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new pac_man();
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (run) {
			try {
				int x = pac_man.x;
				int y = pac_man.y;
				boolean go = true;

				if (x == 0 && sub == 3) {
					x = 28;
					pac_man.x = 28;
				}

				if (x == 27 && sub == 1) {
					x = 0;
					pac_man.x = 0;
				}

				switch (sub) {
				case 0: {
					if (pac_man_variable.wall[x][--y] == 1) {
						go = false;
					}
					break;
				}
				case 1: {
					if (pac_man_variable.wall[++x][y] == 1) {
						go = false;
					}
					break;
				}
				case 2: {
					if (pac_man_variable.wall[x][++y] == 1) {
						go = false;
					}
					break;
				}
				case 3: {
					if (pac_man_variable.wall[--x][y] == 1) {
						go = false;
					}
					break;
				}
				default:
					break;
				}

				if (go) {
					switch (sub) {
					case 0: {
						pac_man.y--;
						wherePac = 0;
						break;
					}
					case 1: {
						pac_man.x++;
						wherePac = 1;
						break;
					}
					case 2: {
						pac_man.y++;
						wherePac = 2;
						break;
					}
					case 3: {
						pac_man.x--;
						wherePac = 3;
						break;
					}
					default:
						break;
					}
					for (draw = 30; draw > 0; draw--) {
						th.sleep(5);
						repaint();
					}
				} else {
					th.sleep(1);
				}

				if (x == pac_man_variable.blinkyPoint.x && y == pac_man_variable.blinkyPoint.y) {
					goastKill();
					th.sleep(2000);
					reset();
				}

				// 쿠키 먹기
				if (pac_man_variable.feed[x][y] == 1) {
					pac_man_variable.feed[x][y] = 0;
					score = score + 10;
				}

				// 큰 쿠키
				if (pac_man_variable.superFeed[x][y] == 1) {
					pac_man_variable.superFeed[x][y] = 0;
					score = score + 50;
					frightened = 60;
				}

				repaint();
				revalidate();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public void goastKill() {
		die = true;
	}

	public void reset() {
		repaint();
		pac_man_variable.blinkyPoint = new Point(13, 11);
		pac_man_variable.clydePoint = new Point(15, 13);
		pac_man_variable.pinkyPoint = new Point(13, 13);
		pac_man_variable.lnkyPoint = new Point(11, 13);
		blinky.blinkyDrawX = pac_man_variable.blinkyPoint.x * 30;
		blinky.blinkyDrawY = pac_man_variable.blinkyPoint.y * 30;
		clyde.clydeDrawX = pac_man_variable.clydePoint.x * 30;
		clyde.clydeDrawY = pac_man_variable.clydePoint.y * 30;
		lnky.lnkyDrawX = pac_man_variable.lnkyPoint.x * 30;
		lnky.lnkyDrawY = pac_man_variable.lnkyPoint.y * 30;
		pinky.pinkyDrawX = pac_man_variable.pinkyPoint.x * 30;
		pinky.pinkyDrawY = pac_man_variable.pinkyPoint.y * 30;

		die = false;
		pac_man_variable.gameTime = 0;
		pac_man.x = 14;
		pac_man.y = 20;
		

	}

	// 0 == up, 1 == right, 2 == bottom, 3 == left;
}
