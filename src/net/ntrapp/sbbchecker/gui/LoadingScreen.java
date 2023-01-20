package net.ntrapp.sbbchecker.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.*;

import net.ntrapp.sbbchecker.logic.DataManager;
import net.ntrapp.sbbchecker.logic.Downloader;
import net.ntrapp.sbbchecker.logic.Parser;

public class LoadingScreen {

	private static Menu menu;
	private static DataManager dm;
	private static LoadingScreen loading = null;
	
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyy");
	// Yesterdays date formatted as dd.MM.yyyy
	private static final String DATE = LocalDate.now().minus(1, ChronoUnit.DAYS).format(DATE_FORMAT);
	private static final File DATASET_PATH = new File(String.format("./dataset/%s.csv", DATE));
	
	private static JLabel downloadText = new JLabel();
	private static JProgressBar downloadProgress = new JProgressBar();
	private static JLabel parsingText = new JLabel();
	private static JProgressBar parsingProgress = new JProgressBar();
	private static JLabel placeholder = new JLabel();
	private static JLabel result = new JLabel();
	private static Container c;

	private LoadingScreen() throws MalformedURLException, IOException, InterruptedException {
		menu = Menu.getInstance();
		c = menu.getFrame().getContentPane();
		
		dm = DataManager.getDataManager();
		downloadProgress.setBounds(195, 250, 200, 50);
		downloadProgress.setMaximum(20000);
		downloadProgress.setVisible(false);
		c.add(downloadProgress);
			
		downloadText.setBounds(195, 175, 200, 100);
		downloadText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		downloadText.setForeground(Color.WHITE);
		downloadText.setText("Downloading ...");
		downloadText.setVisible(false);
		c.add(downloadText);
		
		parsingProgress.setBounds(195, 250, 200, 50);
		parsingProgress.setMaximum(65000);
		parsingProgress.setVisible(false);
		c.add(parsingProgress);
		
		parsingText.setBounds(225, 175, 200, 100);
		parsingText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		parsingText.setForeground(Color.WHITE);
		parsingText.setText("Parsing ...");
		parsingText.setVisible(false);
		c.add(parsingText);
		
		result.setBounds(225, 175, 200, 100);
		result.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		result.setForeground(Color.WHITE);
		result.setVisible(false);
		c.add(result);
		
		c.add(placeholder);
		
		if(!DATASET_PATH.exists()) {
			downloadProgress.setVisible(true);
			downloadText.setVisible(true);
			startDownload();
		} else {
			downloadFinishedStartParser();
		}
	}

	public static LoadingScreen getLoadingScreen() throws MalformedURLException, IOException, InterruptedException {
		if (loading == null) {
			loading = new LoadingScreen();
		}
		return loading;
	}
	
	public static void updateDownloadProgress(int progress) {
		downloadProgress.setValue(progress);
	}
	
	public static void updateParsingProgress(int progress) {
		parsingProgress.setValue(progress);
	}
	
	public void startDownload() throws MalformedURLException, IOException, InterruptedException {
		new Thread() {
			public void run() {
				try {
					new Downloader();
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void downloadFinishedStartParser() throws FileNotFoundException, InterruptedException {
		Thread.sleep(1000);
		downloadProgress.setVisible(false);
		downloadText.setVisible(false);
		parsingProgress.setVisible(true);
		parsingText.setVisible(true);
		new Thread() {
			public void run() {
				try {
					new Parser();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public static void parsingFinished() {
		parsingProgress.setVisible(false);
		parsingText.setVisible(false);
		result.setText(String.format("%s of %s arrived late", dm.getArrivalLateCount(), dm.getArrivalCount()));
		result.setVisible(true);
		System.out.println("Parsing finished\nOutput: '" + String.format("%s of %s arrived late", dm.getArrivalLateCount(), dm.getArrivalCount()) + "'");
	}
}
