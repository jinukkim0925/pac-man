package pac_man_base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class pac_man_frame extends JFrame implements Runnable {
	public JPanel jp1, ap, np, sp, wp, cp, ep, pac, goastPanel, pinkyPanel, lnkyPanel, clydePanel;

	public void fs(String s) {
		setTitle(s);
		setDefaultCloseOperation(2);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});

		add(cp = new JPanel(new BorderLayout()), BorderLayout.CENTER);
		add(np = new JPanel(new BorderLayout()), BorderLayout.NORTH);
		add(sp = new JPanel(new BorderLayout()), BorderLayout.SOUTH);
		add(ep = new JPanel(new BorderLayout()), BorderLayout.EAST);
		add(wp = new JPanel(new BorderLayout()), BorderLayout.WEST);
	}

	public static Point randomPoint(Point goast) {
		Point findPoint = new Point();
		
		int x = goast.x;
		int y = goast.y;
		int subX = x;
		int subY = y;
		
		LinkedList<Point> listPoint = new LinkedList<>();
		
		if (pac_man_variable.wall[x+1][y] == 0) {
			listPoint.add(new Point(x+1,y));
		}
		if (pac_man_variable.wall[x-1][y] == 0) {
			listPoint.add(new Point(x-1,y));
		}
		if (pac_man_variable.wall[x][y+1] == 0) {
			listPoint.add(new Point(x,y+1));
		}
		if (pac_man_variable.wall[x][y-1] == 0) {
			listPoint.add(new Point(x,y-1));
		}
		
		Random r = new Random();
		
		findPoint = listPoint.get(r.nextInt(listPoint.size()));
		
		return findPoint;
	}
	
	public static Point disperseMode(Point pac, Point goast) {
		Point findPoint = new Point();

		int x = 14 - pac.x;
		int y = 14 - pac.y;

		if (x < 0) {
			x = 14 - x;
		} else {
			x = 14 + x;
		}

		if (y < 0) {
			y = 14 + y;
		} else {
			y = 14 - y;
		}

		int subX = x;
		int subY = y;

		int gg = 1, cnt = 0;
		boolean run = true;

		check: while (run) {
			if (subX >= 28 || subY >= 28 || subX <= 0 || subY <= 0) {
				switch (cnt) {
				case 0:
					subX = x + gg;
					break;
				case 1:
					subX = x - gg;
					break;

				case 2:
					subY = y + gg;
					break;

				case 3:
					subY = y - gg;
					break;

				default:
					cnt = 0;
					if (gg < 0) {
						gg--;
					} else {
						gg++;
					}
					if (gg > 10) {
						gg = -1;
					}
					break;
				}
			} else if (pac_man_variable.wall[subX][subY] == 1) {
				switch (cnt) {
				case 0:
					subX = x + gg;
					break;
				case 1:
					subX = x - gg;
					break;

				case 2:
					subY = y + gg;
					break;

				case 3:
					subY = y - gg;
					break;

				default:
					cnt = 0;
					if (gg < 0) {
						gg--;
					} else {
						gg++;
					}
					if (gg > 10) {
						gg = -1;
					}
					break;
				}

			} else {
				break check;
			}
			cnt++;
		}
//		System.out.println(subX + "," + subY);
//		System.out.println(goast);
		findPoint = trackingMode(new Point(subX, subY), goast);
		
		return findPoint;
	}
	
	public static int findDistance(Point pac, Point goast) {
		int distance = 0;
		
		int x = goast.x - pac.x;
		int y = goast.y - pac.y;
		
		x = x * x;
		y = y * y;
		
		int path = x + y;
		
		distance = (int) Math.sqrt(path);
		return distance;
	}
	
	// 최단경로
	public static Point trackingMode(Point pac, Point goast) {
		Point findPoint = new Point();
		boolean run = true;
		
		HashMap<Integer, Point> map = new HashMap<>();
		HashMap<Integer, Point> submap = new HashMap<>();
		HashMap<Integer, Point> wallmap = new HashMap<>();
		HashMap<Point, Point> findmap = new HashMap<>();

		int c = 0;
		for (int i = 0; i < pac_man_variable.wall.length; i++) {
			for (int j = 0; j < pac_man_variable.wall[i].length; j++) {
				if (pac_man_variable.wall[i][j] == 1) {
					wallmap.put(c, new Point(i, j));
					c++;
				}
			}
		}

		map.put(0, goast);
		findmap.put(goast, goast);

		int cnt = 0;
		submap.put(cnt, goast);
		cnt++;
		find: while (run) {

			int size = map.size();

			for (int i = 0; i < size; i++) {

				Point p = map.get(i);
				map.remove(i);

				if (search(wallmap, p.x + 1, p.y) && search(submap, p.x + 1, p.y)) {
					submap.put(cnt, new Point(p.x + 1, p.y));
					findmap.put(new Point(p.x + 1, p.y), p);
					cnt++;
				}
				if (search(wallmap, p.x - 1, p.y) && search(submap, p.x - 1, p.y)) {
					submap.put(cnt, new Point(p.x - 1, p.y));
					findmap.put(new Point(p.x - 1, p.y), p);
					cnt++;
				}
				if (search(wallmap, p.x, p.y + 1) && search(submap, p.x, p.y + 1)) {
					submap.put(cnt, new Point(p.x, p.y + 1));
					findmap.put(new Point(p.x, p.y + 1), p);
					cnt++;
				}
				if (search(wallmap, p.x, p.y - 1) && search(submap, p.x, p.y - 1)) {
					submap.put(cnt, new Point(p.x, p.y - 1));
					findmap.put(new Point(p.x, p.y - 1), p);
					cnt++;
				}

			}

			size = submap.size();
			for (int i = 0; i < size; i++) {
				Point p = submap.get(i);
				if (p.x == pac.x && p.y == pac.y) {
					run = false;
					break find;
				}
				map.put(i, p);
			}

		}

		int size = findmap.size();
		Point p = new Point(pac);
		for (int i = 0; i < size; i++) {
//			System.out.println(p + " / " + goast);
			p = findmap.get(p);
			if (p.x == goast.x && p.y == goast.y) {
				if (findPoint.x == 0 && findPoint.y == 0) {
					return goast;
					
				}else {
					return findPoint;
				}
			}
			findPoint = p;
		}
		return findPoint;
	}

	public static boolean search(HashMap<Integer, Point> map, int x, int y) {
		if (map.containsValue(new Point(x, y))) {
			return false;
		}
		return true;
	}

	public void sh() {
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public void size(JComponent c, int x, int y) {
		c.setPreferredSize(new Dimension(x, y));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
