package pac_man_base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class pac_man_frame extends JFrame implements Runnable{
	public JPanel jp1, ap, np, sp, wp, cp, ep,pac, blinkyPanel, pinkyPanel, lnkyPanel, clydePanel;
	
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
