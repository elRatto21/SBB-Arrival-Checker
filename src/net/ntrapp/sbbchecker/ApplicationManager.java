package net.ntrapp.sbbchecker;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.ntrapp.sbbchecker.gui.Menu;
import net.ntrapp.sbbchecker.logic.Downloader;
import net.ntrapp.sbbchecker.logic.Parser;

public class ApplicationManager {

	public static void main(String[] args) throws IOException {
		//Menu menu = Menu.getInstance();
		Downloader d = new Downloader();
		d.download();
	}

}
