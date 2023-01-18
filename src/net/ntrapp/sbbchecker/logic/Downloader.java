package net.ntrapp.sbbchecker.logic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Downloader {
	
	private File tempFile = new File("./dataset/ist-daten-sbb.csv");
	private BufferedInputStream in;
	
	
	public void download() throws IOException {
		if(!tempFile.exists()) {
			System.out.println("Download gestartet");
			final String FILE_URL = "https://data.sbb.ch/api/explore/v2.1/catalog/datasets/ist-daten-sbb/exports/csv?lang=de&timezone=Europe%2FBerlin&use_labels=true&csv_separator=%3B";
			in = new BufferedInputStream(new URL(FILE_URL).openStream());
			FileOutputStream fileOutputStream = new FileOutputStream("./dataset/ist-daten-sbb.csv");
			byte dataBuffer[] = new byte[1024];
			int bytesRead;
			while((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				fileOutputStream.write(dataBuffer, 0, bytesRead);
			}
			System.out.println("Download beendet");
			fileOutputStream.close();
		} else {
			System.out.println("Datei bereits vorhanden");
		}
	}

}
