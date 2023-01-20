package net.ntrapp.sbbchecker.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.*;

public class Menu {

	private static Menu menu_instance = null;

	private JFrame frame;
	private JLabel heading; // [0] == Heading
	private JButton submit;
	private JLabel placeholder = new JLabel();
	private Container c;

	private Menu() {
		setupFrame();
		setupStart();
		setupHeading();
		c.add(placeholder);
	}

	public static Menu getInstance() {
		if (menu_instance == null) {
			menu_instance = new Menu();
		}
		return menu_instance;
	}

	private void setupFrame() {
		frame = new JFrame("Vallah wie oft SBB spät?");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = frame.getContentPane();
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setResizable(false);
		c.setBackground(Color.DARK_GRAY);
	}

	private void setupHeading() {
		heading = new JLabel();
		heading.setBounds(40, 100, 600, 100);
		heading.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		heading.setForeground(Color.WHITE);
		heading.setText("Wie oft war die SBB gestern zu spät?");
		heading.setVisible(true);
		c.add(heading);
	}

	private void setupStart() {
		submit = new JButton();
		submit.setBounds(190, 300, 200, 75);
		submit.setText("START");
		submit.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
		submit.setBackground(Color.LIGHT_GRAY);
		submit.setVisible(true);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					try {
						submitPressed();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		c.add(submit);
	}

	private void submitPressed() throws MalformedURLException, IOException, InterruptedException {
		disable();
		LoadingScreen.getLoadingScreen();
	}

	public JFrame getFrame() {
		return this.frame;
	}

	private void disable() {
		submit.setVisible(false);
		heading.setVisible(false);
	}

}
