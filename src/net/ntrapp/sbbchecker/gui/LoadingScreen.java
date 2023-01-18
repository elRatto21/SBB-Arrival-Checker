package net.ntrapp.sbbchecker.gui;

import javax.swing.*;

import net.ntrapp.sbbchecker.logic.DataManager;

public class LoadingScreen {

	private Menu menu;
	private DataManager dm;
	private static LoadingScreen loading = null;
	private JLabel text = new JLabel();

	private LoadingScreen() {
		menu = Menu.getInstance();
		dm = DataManager.getDataManager();
		text.setBounds(100, 100, 50, 50);
		text.setText(String.format("Gestern waren %s von %s zu spät", dm.getArrivalLateCount(), dm.getArrivalCount()));
		text.setVisible(true);
		menu.getFrame().add(text);

	}

	public static LoadingScreen getLoadingScreen() {
		if (loading == null) {
			loading = new LoadingScreen();
		}
		return loading;
	}

}
