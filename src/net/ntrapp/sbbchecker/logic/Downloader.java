package net.ntrapp.sbbchecker.logic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.SwingUtilities;

import net.ntrapp.sbbchecker.gui.LoadingScreen;

/**
 * This class manages the download of the dataset
 * 
 * @author Niklas Trapp
 * @version 1.0.5
 * @since 1.0
 */
public class Downloader {

	private BufferedInputStream in;
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyy");
	// Yesterdays date formatted as dd.MM.yyyy
	private static final String DATE = LocalDate.now().minus(1, ChronoUnit.DAYS).format(DATE_FORMAT);
	private File tempFile = new File(String.format("./dataset/%s.csv", DATE));
	private File tempDir = new File("./dataset/");

	// TODO: Implement method to find the real size, currently to lazy so just
	// assuming the dataset is never bigger than 20MB
	private static final int DOWNLOAD_SIZE = 20000;

	public Downloader() throws MalformedURLException, IOException {
		// Checks if the dataset dir exists, if not creates it
		if (!tempDir.exists()) {
			new File("./dataset/").mkdirs();
		}
		// Checks if the dataset from yesterday already exists
		if (!tempFile.exists()) {
			// Prints out that the download starts
			System.out.println(String.format("Downloading Dataset from %s", DATE));
			// URL of the dataset (Same URL every day)
			final String FILE_URL = "https://data.sbb.ch/api/explore/v2.1/catalog/datasets/ist-daten-sbb/exports/csv?lang=de&timezone=Europe%2FBerlin&use_labels=true&csv_separator=%3B";
			// New InputStream
			in = new BufferedInputStream(new URL(FILE_URL).openStream());
			// New OutputStream saving the file as yesterdays date
			FileOutputStream fileOutputStream = new FileOutputStream(String.format("./dataset/%s.csv", DATE));

			byte dataBuffer[] = new byte[1024];
			int bytesRead;
			int tempCount = 0; 
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				tempCount++;
				final int byteCount = tempCount;
				// Updates the ProgressBar
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (byteCount % 1000 == 0) {
							LoadingScreen.updateProgress(byteCount);
						}
					}
				});
				// "Downloading"
				fileOutputStream.write(dataBuffer, 0, bytesRead);
			}
			// Download finished, updating ProgressBar to 100%
			System.out.println("Download finished");
			LoadingScreen.updateProgress(DOWNLOAD_SIZE);
			fileOutputStream.close();
		} else {
			System.out.println("Dataset already exists");
		}
	}

}
