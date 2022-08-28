package testfi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler;
import javax.swing.table.DefaultTableModel;

//interface for cafe manu list
public class MainFrame extends JFrame {
	
	//object serialization
	private static final long seriaVersionUID = 1L;
	
	//Set variable name
	String tmp, menu, sign2;
	DefaultTableModel model;
	int price, realsum;
	
	JPanel Mainpanel, Orderpanel, Labelpanel, btnpanel, Optionpanel, Menupanel;
	JLabel l_order, l_sum, l_realsum;
	JTable T_tmp;
	JButton btn_order, btn_cancel, btn_quit;
	JButton[] btn_menu;
	JRadioButton[] tmp_Radio;
	ButtonGroup tmp_group, menu_group;
	File file;
	
	String header[] = {"Menu", "품목", "Price"};
	String contents[][] = {};
	
	String[] label_list = {"Order", "Total"};
	String[] menu_name = {"Latte", "Milk", "Mocha", "ice cream", "tea"};
	String[] names = {"Hot", "Ice"};
	int[] price_list = {3000,2000,3500,4000,1500};
	
	
	public MainFrame() {
		this.setSize(600,400);
		realsum = 0;
		
		Mainpanel = new JPanel();
		Mainpanel.setPreferredSize(new Dimension(600,400));
		Mainpanel.setLayout(new GridLayout(1,2,8,8));
		
		Orderpanel = new JPanel();
		Orderpanel.setPreferredSize(new Dimension(280, 390));
		Orderpanel.setLayout(new BoxLayout(Orderpanel, BoxLayout.Y_AXIS));
		
		Labelpanel = new JPanel();
		for(int i = 0; i <label_list.length; i++) {
			l_order = new JLabel(label_list[i]);
			l_order.setPreferredSize(new Dimension(270,15));
			l_order.setOpaque(true);
			l_order.setBackground(Color.white);
			Labelpanel.add(l_order);
			Labelpanel.add(Box.createVerticalStrut(8));
		}
		
		l_realsum = new JLabel("0");
		l_realsum.setPreferredSize(new Dimension(270,15));
		l_realsum.setOpaque(true);
		l_realsum.setBackground(Color.white);
		Labelpanel.add(l_realsum);
		Labelpanel.add(Box.createVerticalStrut(8));
		
		Orderpanel.add(Labelpanel);
		
		this.model = new DefaultTableModel(contents, header);
		this.T_tmp = new JTable(this.model);
		T_tmp.setPreferredSize(new Dimension(200,200));
		Orderpanel.add(T_tmp.getTableHeader());
		Orderpanel.add(Box.createVerticalStrut(8));
		
		btnpanel = new JPanel();
		btnpanel.setLayout(new GridLayout(0,3,10,0));
		
		btn_order = new JButton("Order");
		btn_order.addActionListener(new ActionHandler1());
		btnpanel.add(btn_order);
		
		btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new ActionHandler1());
		btnpanel.add(btn_cancel);
		
		btn_quit = new JButton("Exit");
		btn_quit.addActionListener(new ActionHandler1());
		btnpanel.add(btn_quit);
		
		Orderpanel.add(btnpanel);
		
		Menupanel = new JPanel();
		Menupanel.setLayout(new GridLayout(0,2,8,8));
		
		Optionpanel = new JPanel();
		Optionpanel.setPreferredSize(new Dimension(120,100));
		Optionpanel.setLayout(new GridLayout(2,0));
		
		tmp_Radio = new JRadioButton[2];
		tmp_group = new ButtonGroup();
		for(int i = 0; i<tmp_Radio.length; i++) {
			tmp_Radio[i] = new JRadioButton(names[i]);
			tmp_group.add(tmp_Radio[i]);
			tmp_Radio[i].addItemListener(new ItemHandler());
			Optionpanel.add(tmp_Radio[i]);
		}
		
		Menupanel.add(Optionpanel);
		
		btn_menu = new JButton[5];
		
		for(int i=0; i<menu_name.length; i++) {
			btn_menu[i] = new JButton(menu_name[i]);
			btn_menu[i].setPreferredSize(new Dimension(120,100));
			String menu = menu_name[i];
			btn_menu[i].addActionListener(new ActionHandler2());
			Menupanel.add(btn_menu[i]);
		}
	
		
		Mainpanel.add(Orderpanel);
		Mainpanel.add(Menupanel);
		this.add(Mainpanel);
	}
	
	
	public void order() {
		File file = new File("data/myorderlist");
		try {
			FileWriter fw = new FileWriter(file,true);
			fw.write("Total" + model.getRowCount()+"menus"+ "\n");//Line change
			for (int i = 0; i<model.getRowCount();i++) {
				for(int j = 0; j<model.getColumnCount();j++) {
					fw.write((String)model.getValueAt(i,j) + " ");
				}
				fw.write("/ ");
			}
			fw.write("\n\n");
			fw.flush();
			JOptionPane.showMessageDialog(null, "Order Success.");
			
			this.setVisible(false);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Order Fail.");
			e.printStackTrace();
			
		}
		
	}
	
	public void cancel() {
		this.model.removeRow(this.T_tmp.getSelectedRow());
		realsum = realsum - price;
		l_realsum.setText(Integer.toString(realsum));
	}
	
	public class ActionHandler1 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String sign1 = e.getActionCommand();
			
			if(sign1.equals("Order")) {
				order();
			}
			
			else if (sign1.equals("Cancel")) {
				cancel();
			}
			else if (sign1.equals("Exit")) {
				System.exit(0);
			}
			
		}
	}
	// TODO ice hot medium
	// TODO button size down
	// TODO 취소 error fix
	// TODO hot button Default value

	public class ActionHandler2 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			sign2 = new String(e.getActionCommand());
			
			String input[] = new String[3];
			
			if (sign2.equals("Latte")) {
				realsum = realsum + 3000;
			}
			else if (sign2.equals("Milk")) {
				realsum = realsum + 2000;
			}
			else if (sign2.equals("Mocha")) {
				realsum = realsum + 3500;
			}
			else if (sign2.equals("ice cream")) {
				realsum = realsum + 4000;
			}
			else if (sign2.equals("tea")) {
				realsum = realsum + 1500;
			}
			
			input[0] = tmp;
			input[1] = sign2;
			input[2] = Integer.toString(price);
			model.addRow(input);
			
			l_realsum.setText(Integer.toString(realsum));
			}
		}

	public class ItemHandler implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange()==ItemEvent.DESELECTED) {
				return;
			}
			if(tmp_Radio[0].isSelected()) {
				tmp = new String("Hot");
			}
			else if (tmp_Radio[1].isSelected()) {
				tmp = new String("Ice");
			   }
			}
		}
		
	}
