# BreadBoard.java
package tryout;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;

class Attribute {
	int link;
	int value;
	Color color;
	
	Attribute(int l, Color clr) {
		link = l;
		color = clr;
		value = -1;
	}
	
	Attribute(int l, int v) {
		link = l;
		value = v;
	}
}

class Cordinate {
	int s, r, c;
	Cordinate(int s, int r, int c) {
		this.s = s;
		this.r = r;
		this.c = c;
	}
}

class Pins {
	Cordinate src;
	Attribute ob;
	Pins next;
	
	Pins(Cordinate src) {
		this.src = src;
	}
	
	void add(Pins ob) {
		Pins tmp = this;
		while(tmp.next!=null) {
			tmp = tmp.next;
		}
		tmp.next = ob;
	}
}

class color {
	int r, g, b;
}

//<applet code="BreadBoard.class" width = 1750 height = 400> </applet>
public class BreadBoard extends Applet implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel lblbt[], lblb[];
	char row[];
	JLabel tmplbl[];
	JLabel lbl;
	static Vector<color> vcolor = new Vector<color>();
	static JButton pin[][][];
	static Attribute ofpin[][][];
	static Vector<JButton> icgate = new Vector<JButton>();
	@SuppressWarnings("rawtypes")
	static Vector gate = new Vector();
	static Vector<Cordinate> inpt = new Vector<Cordinate>();
	static Vector<Cordinate> otpt = new Vector<Cordinate>();
	static Vector<Cordinate> vcc = new Vector<Cordinate>();
	static Vector<Cordinate> gnd = new Vector<Cordinate>();
	static Vector<Pins> wire = new Vector<Pins>();
	static Applet board;
	JPanel brd, pnl1, pnl2, pnl3, pnl0;
	static JPanel pnlpn;
	static ImageIcon icon;

	ImageIcon ic;
	Dimension dmpn;
	Rectangle btn;
	boolean flag, counter;
	static boolean canDisplay;

	public void init() {
		counter = true;
		board = this;
		setSize(1750, 300);
		icon = new ImageIcon(ClassLoader.getSystemResource("breadboard_pin.png"));
		ic = new ImageIcon("");
		
		tmplbl = new JLabel[12];
		row = new char[]{' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', ' '};
		for(int i=0;i<12;i++) {
			tmplbl[i] = new JLabel("   " + row[i]);
			tmplbl[i].setFont(new Font("SansSerif", Font.BOLD, 9));
			tmplbl[i].setHorizontalTextPosition(JLabel.CENTER);
		}
		lblb = new JLabel[64];
		pin = new JButton[2][5][64];
		ofpin = new Attribute[2][5][64];
		lblbt = new JLabel[64];
		
		lbl = new JLabel();
		brd = new JPanel();
		pnl0 = new JPanel();
		pnl1 = new JPanel();
		pnl2 = new JPanel();
		pnl3 = new JPanel();
		pnlpn = new JPanel();

		pnl0.setLayout(new GridLayout(1, 64));
		pnl0.setBackground(new Color(255, 250, 255));
		
		pnl1.setLayout(new GridLayout(5, 64));
		pnl1.setBackground(new Color(255, 250, 240));
		
		pnl2.setLayout(new GridLayout(5, 64));
		pnl2.setBackground(new Color(255, 250, 240));
		
		pnl3.setLayout(new GridLayout(1, 64));
		pnl3.setBackground(new Color(255, 250, 255));
		
		pnlpn.setLayout(null);

		pnl0.add(tmplbl[0]);
		pnl3.add(tmplbl[11]);
		for(int i=0;i<5;i++) {
			pnl1.add(tmplbl[i+1]);
			pnl2.add(tmplbl[i+6]);
			for(int j=0;j<64;j++) {
				pin[0][i][j] = new JButton();
				pin[0][i][j].setIcon(icon);
				pin[0][i][j].setBorderPainted(false);
				pin[0][i][j].setContentAreaFilled(false);
				pin[0][i][j].addActionListener(this);
				pnl1.add(pin[0][i][j]);
				
				pin[1][i][j] = new JButton();
				pin[1][i][j].setIcon(icon);
				pin[1][i][j].setBorderPainted(false);
				pin[1][i][j].setContentAreaFilled(false);
				pin[1][i][j].addActionListener(this);
				pnl2.add(pin[1][i][j]);
				
				ofpin[0][i][j] = new Attribute(-1, -1);
				ofpin[1][i][j] = new Attribute(-1, -1);
				
				if(i==0) {
					if(j<10) {
						lblb[j] = new JLabel("   "+j);
						lblbt[j] = new JLabel("   "+j);
					} else {
						lblb[j] = new JLabel("  " +j);
						lblbt[j] = new JLabel("  " +j);
					}
					pnl0.add(lblb[j]);
					pnl3.add(lblbt[j]);
				}
			}
		}
		setLayout(new FlowLayout());

		pnl0.setPreferredSize(new Dimension(1500, 5));
		pnl3.setPreferredSize(new Dimension(1500, 5));
		brd.setLayout(new GridLayout(5, 1));
		brd.setPreferredSize(new Dimension(1500, 250));
		brd.add(pnl0);
		brd.add(pnl1);
		brd.add(pnlpn);
		brd.add(pnl2);
		brd.add(pnl3);
		
		add(brd);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton prsd = (JButton)arg0.getSource();
		Cordinate src = getButton(prsd); 
		Object[] options;
		int ch;
		
		if(prsd.getIcon()==null) {
			options = new Object[]{"Remove connection", "Change Connection", "Return"};
		    ch = JOptionPane.showOptionDialog(null, "Would you like to ...?", "PIN CONNECTION", 
		    		JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
		    switch(ch) {
		    case 0:
		    	//Remove
		    	break;
		    	
		    case 1:
		    	//Remove
		    	linkPin(src);
		    }
		} else if(prsd.getIcon().toString().endsWith("vcc.png")) {
			options = new Object[]{"Yes", "No"};
		    ch = JOptionPane.showOptionDialog(null, "Would you like to Remove Vcc?", "PIN CONNECTION", 
		    		JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
		    
		    if(ch==0) {
		    	BreadBoard.removeFrom(src, vcc);
		    	BreadBoard.removeValue(src);
		    	BreadBoard.ofpin[src.s][src.r][src.c] = new Attribute(-1, -1);
		    	BreadBoard.getPin(src).setIcon(icon);
		    }
		} else if(prsd.getIcon().toString().endsWith("gnd.png")) {
			options = new Object[]{"Yes", "No"};
		    ch = JOptionPane.showOptionDialog(null, "Would you like to Remove Ground?", "PIN CONNECTION", 
		    		JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
		    
		    if(ch==0) {
		    	BreadBoard.removeFrom(src, gnd);
		    	BreadBoard.removeValue(src);
		    	BreadBoard.ofpin[src.s][src.r][src.c] = new Attribute(-1, -1);
		    	BreadBoard.getPin(src).setIcon(icon);
		    }
		} else if(prsd.getIcon().toString().endsWith("otpt.png")) {
			options = new Object[]{"Yes", "No"};
		    ch = JOptionPane.showOptionDialog(null, "Would you like to Remove Output?", "PIN CONNECTION", 
		    		JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
		    
		    if(ch==0) {
		    	BreadBoard.removeFrom(src, otpt);
		    	BreadBoard.removeValue(src);
		    	BreadBoard.ofpin[src.s][src.r][src.c] = new Attribute(-1, -1);
		    	BreadBoard.getPin(src).setIcon(icon);
		    }
		}  else if(prsd.getIcon().toString().endsWith("inpt.png")) {
			options = new Object[]{"Yes", "No"};
		    ch = JOptionPane.showOptionDialog(null, "Would you like to Remove Input?", "PIN CONNECTION", 
		    		JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
		    
		    if(ch==0) {
		    	BreadBoard.removeFrom(src, inpt);
		    	BreadBoard.removeValue(src);
		    	BreadBoard.ofpin[src.s][src.r][src.c] = new Attribute(-1, -1);
		    	BreadBoard.getPin(src).setIcon(icon);
		    }
		} else if(prsd.getIcon().toString().endsWith("pin.png")) {
			linkPin(src);
		}
	}
	
	static void addICGate(Cordinate src, String ic) {
		ImageIcon imgIcn = new ImageIcon(ClassLoader.getSystemResource("breadboard_ic.png"));
		JButton icgate = new JButton(ic, imgIcn);
		icgate.setForeground(new Color(255, 255, 255));
		icgate.setHorizontalTextPosition(JButton.CENTER);
		icgate.setVerticalTextPosition(JButton.CENTER);
		icgate.setBounds(27+23*src.c, 0, 161, 50);
		pnlpn.add(icgate);
		icgate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for(Cordinate tmp : vcc) {
					addValue(tmp, 1);
				}
				
				for(Cordinate tmp : inpt) {
					BreadBoard.ofpin[tmp.s][tmp.r][tmp.c].value = 0;
				}
				
				for(Cordinate tmp : otpt) {
					BreadBoard.ofpin[tmp.s][tmp.r][tmp.c].value = getValue(tmp);
				}
				
				execute();
				if(canDisplay) {
					Display ob = new Display(inpt.size(), otpt.size());
					ob.init();
				}
			}
	});
		BreadBoard.icgate.add(icgate);
		icgate.requestFocus();
	}

	static void linkPin(Cordinate src) {
		try {
			BreadBoard.board.setEnabled(false);
			Connect ob = new Connect(src);
			ob.init();
			
		} catch (Exception e) {
			System.out.println("Not Found!");
		}
	}
	
	Cordinate getButton(JButton prsd) {
		Cordinate src = new Cordinate(0, 0, 0);
		for(int i=0;i<5;i++) {
			for(int j=0;j<64;j++) {
				if(prsd.equals(pin[0][i][j])) {
					src.s = 0;
					src.r = i;
					src.c = j;
				} else if(prsd.equals(pin[1][i][j])) {
					src.s = 1;
					src.r = i;
					src.c = j;
				}
			}
		}
		
		return src;
	}
	
	static boolean isIn(Cordinate src, Vector<Cordinate> v) {
		boolean flag = false;
		
		for(Cordinate tmp : v) {
			if(src.s==tmp.s && src.r==tmp.r && src.c==tmp.c) {
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	static void removeFrom(Cordinate src, Vector<Cordinate> v) {
		for(Cordinate tmp : v) {
			if(src.s==tmp.s && src.r==tmp.r && src.c==tmp.c) {
				v.remove(tmp);
				break;
			}
		}
	}
	
	static Color getColor() {
		Color clr;
		color clrt = new color();
		Random rand = new Random();
		int rd, gr, bl;
		do {
			rd = rand.nextInt(255);
		}while(isIn(rd, 0));
		do {
			gr = rand.nextInt(255);
		}while(isIn(gr, 0));
		do {
			bl = rand.nextInt(255);
		}while(isIn(bl, 0));
		
		clrt.r = rd;
		clrt.g = gr;
		clrt.b = bl;
		
		vcolor.add(clrt);
		clr = new Color(rd, gr, bl);
		return clr;
	}
	
	static JButton getPin(Cordinate src) {
		return BreadBoard.pin[src.s][src.r][src.c];
	}
	
	static Attribute getOf(Cordinate src) {
		Attribute ob = null;
		
		for(int i=0;i<5;i++) {
			ob = BreadBoard.ofpin[src.s][i][src.c];
			if(ob!=null) {
				break;
			}
		}
		return ob;
	}

	static boolean isOutput(Cordinate src) {
		boolean flag = false;
		JButton btn;
		int i, k=0;
		
		for(i=0;i<5;i++) {
			btn = BreadBoard.pin[src.s][i][src.c];
			if(btn.getIcon()!=icon) {
				flag = true;
				break;
			}
		}
		
		if(flag) {
			do {
				btn = BreadBoard.pin[src.s][i][src.c-k-1];
				k++;
			}while(btn.getIcon()!=icon && src.c-k!=0);
		}
		
		return flag;
	}
	
	static boolean checkValue(Cordinate src, int value) {
		int i, tmp=-1;
		
		for(i=0;i<5;i++) {
			if(src.r!=i) {
				tmp = BreadBoard.ofpin[src.s][i][src.c].value;
				if(tmp!=-1) {
					break;
				}
			}
		}

		if(value == 2) {
			if(i==5) {
				return false;
			}
			BreadBoard.ofpin[src.s][src.r][src.c] = BreadBoard.ofpin[src.s][i][src.c];
			return true;
		} else if(tmp==1 || tmp==0 || tmp==-2) {
			return false;
		} else {
			addValue(src, value);
			return true;
		}
	}
	
	static void addValue(Cordinate src, int value) {
		Attribute tmp;
		
		for(int i=0;i<5;i++) {
			tmp = BreadBoard.ofpin[src.s][i][src.c];
			if(src.r!=i && tmp.link!=-1) {
				tmp.value = value;
				break;
			}
		}
	}
	
	static int getValue(Cordinate src) {
		Attribute tmp;
		System.out.print(src.s + "," + src.r + "," + src.c + "\t");
		for(int i=0;i<5;i++) {
			tmp = BreadBoard.ofpin[src.s][i][src.c];
			if(src.r!=i && tmp.link!=-1 && tmp.value!=2) {
				System.out.print(src.s + "," + i + "," + src.c + "\n");
				return tmp.value;
			}
		}
		System.out.print("Fail\n");
		return 0;
	}
	
	static void removeValue(Cordinate src) {
		int tmp;
		
		for(int i=0;i<5;i++) {
			tmp = BreadBoard.ofpin[src.s][i][src.c].link;
			if(src.r!=i && tmp!=-1) {
				BreadBoard.ofpin[src.s][i][src.c].value = -1;
				break;
			}
		}
	}
	
	static boolean isIn(int c, int ch) {
		boolean flag = false;
		int cmp=0;
		
		for(color clr : vcolor) {
			switch(ch) {
			case 1:
				cmp = clr.r;
				break;
				
			case 2:
				cmp = clr.g;
				break;
				
			case 3:
				cmp = clr.b;
				break;
			}
			if(c==cmp) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	static void execute() {
		String s;
		canDisplay = true;
		for(Object ob : gate) {
			s = ob.toString().substring(7);
			if(s.startsWith("AND")) {
				AND ic = (AND) ob;
				ic.start();
			} else if(s.startsWith("OR")) {
				OR ic = (OR) ob;
				ic.start();
			} else if(s.startsWith("NOT")) {
				NOT ic = (NOT) ob;
				ic.start();
			} else if(s.startsWith("NAND")) {
				NAND ic = (NAND) ob;
				ic.start();
			} else if(s.startsWith("NOR")) {
				NOR ic = (NOR) ob;
				ic.start();
			} else if(s.startsWith("XOR")) {
				XOR ic = (XOR) ob;
				ic.start();
			}
		}
	}
	
}


Connect.java
package tryout;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class Connect extends JFrame implements ActionListener {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	JFrame frame;
	JLabel ptr[];
	JLabel spc[];
	JButton btn[];
	JPanel pnl[];
	
	private int Frame_Width;
	private int Frame_Height;
	private int Frame_X;
	private int Frame_Y;
	boolean flag;
	Cordinate src;
	
	Connect(Cordinate src) {
		this.src = src;
		flag = true;
	}

	public void init() {
		frame = new JFrame(); 
		Frame_Width = 480; Frame_Height = 450;
		Frame_X = 635; Frame_Y = 204;
		frame.setLayout(new FlowLayout());
		String s[] = {"Add IC", "Add Input", "Add Output", "Add Vcc", "Add Ground", "Connect another Pin"};
		
		spc = new JLabel[6];
		for(int i=0;i<6;i++) {
			spc[i] = new JLabel("   ");
			spc[i].setFont(new Font("SansSerif", 0, 30));
		}
		
		ptr = new JLabel[6];
		for(int i=0;i<6;i++) {
			ptr[i] = new JLabel("~~>");
			ptr[i].setFont(new Font("SansSerif", 0, 30));
		}
		
		btn = new JButton[6];
		for(int i=0;i<6;i++) {
			btn[i] = new JButton(s[i]);
			btn[i].setPreferredSize(new Dimension(300, 50));
			btn[i].setFont(new Font("SansSerif", 0, 20));
			btn[i].addActionListener(this);
		}
		
		pnl = new JPanel[6];
		for(int i=0;i<6;i++) {
			pnl[i] = new JPanel();
			pnl[i].setLayout(new FlowLayout());
			pnl[i].add(ptr[i]);
			pnl[i].add(btn[i]);
			pnl[i].add(spc[i]);
			frame.add(pnl[i]);
		}
		
		jFrame(frame);
	}
	
	//Create a generalized Frame:
	private void jFrame(final JFrame frame) {
		
		frame.setVisible(true);
		frame.setTitle("Pin Configuration");
		frame.setSize(Frame_Width, Frame_Height);
		frame.setLocation(Frame_X, Frame_Y);
		frame.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent arg0) {
			}
			public void windowClosed(WindowEvent arg0) {
			}
			public void windowClosing(WindowEvent arg0) {
				if(flag) {
					BreadBoard.board.setEnabled(true);
				}
			}
			public void windowDeactivated(WindowEvent arg0) {
			}
			public void windowDeiconified(WindowEvent arg0) {
			}
			public void windowIconified(WindowEvent arg0) {
			}
			public void windowOpened(WindowEvent arg0) {
			}
			
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton prsd = (JButton)arg0.getSource();
		
		for(int i=0;i<6;i++) {
			if(prsd.equals(btn[i])) {
				call(i);
				break;
			}
		}
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	Cordinate isFree() {
		Cordinate flag = null, tmp;
		int s, r, c;
		for(int pin=0;pin<14;pin++) {
			s = (pin<7)?1:0;
			r = (pin<7)?0:4;
			c = src.c + ((pin<7)?pin:(13-pin));
			tmp = new Cordinate(s, r, c);
			
			if(!BreadBoard.getPin(tmp).getIcon().toString().endsWith("pin.png")) {
				flag = tmp;
				break;
			}
		}
		
		return flag;
	}
	
	void call(int o) {
		JButton tmp;
		Icon icon;
		Cordinate pin;
		char ch;
		int v;
		
		try {
			switch(o) {
			case 0:
				if(src.s!=0 || src.r!=4) {
					display("An IC can only be added on the E Row.");
				} else if(o==0 && src.c>57) {
					display("An IC has 7 pin. Select a column less than 58.");
				} else {
					pin = isFree();
					if (pin!=null) {
						ch = (char)(65+pin.r+5*pin.s);
						display("Cannot place IC. Connection found at " + ch + "-" + pin.c + ".");
					} else {
						ICOption ob = new ICOption(src);
						flag = false;
						ob.init();
					}
				}
				break;
				
			case 1:
				v = 0;
				if(BreadBoard.checkValue(src, v)) {
					icon = new ImageIcon(ClassLoader.getSystemResource("breadboard_inpt.png"));
					BreadBoard.inpt.add(src);
					tmp = BreadBoard.getPin(src);
					tmp.setIcon(icon);
					BreadBoard.ofpin[src.s][src.r][src.c] = new Attribute(-2, v);
				} else {
					display("Error! Another Connection already Exists!");
				}
				
				break;
				
			case 2:
				v = 2;
				BreadBoard.checkValue(src, v);
				icon = new ImageIcon(ClassLoader.getSystemResource("breadboard_otpt.png"));
				BreadBoard.otpt.add(src);
				tmp = BreadBoard.getPin(src);
				tmp.setIcon(icon);
				BreadBoard.ofpin[src.s][src.r][src.c] = new Attribute(-2, v);
								
				break;
				
			case 3:
				v = 1;
				if(BreadBoard.checkValue(src, v)) {
					icon = new ImageIcon(ClassLoader.getSystemResource("breadboard_vcc.png"));
					BreadBoard.vcc.add(src);
					tmp = BreadBoard.getPin(src);
					tmp.setIcon(icon);
					BreadBoard.ofpin[src.s][src.r][src.c] = new Attribute(-2, v);
					
				} else {
					display("Error! Another Connection already Exists!");
				}
				break;
				
			case 4:
				v = -2;
				if(BreadBoard.checkValue(src, v)) {
					icon = new ImageIcon(ClassLoader.getSystemResource("breadboard_gnd.png"));
					BreadBoard.gnd.add(src);
					tmp = BreadBoard.getPin(src);
					tmp.setIcon(icon);
					BreadBoard.ofpin[src.s][src.r][src.c] = new Attribute(-2, v);
				} else {
					display("Error! Another Connection already Exists!");
				}
				break;
				
			case 5:
				flag = false;
				PinOption ob = new PinOption(src);
				ob.init();
			}
			
		} catch (Exception e) {
			System.out.println(o);
		}
		
	}

	public void display(String s) {
		JOptionPane.showMessageDialog(frame, s, "BREADBOARD", JOptionPane.INFORMATION_MESSAGE, null);
	}
	
}
