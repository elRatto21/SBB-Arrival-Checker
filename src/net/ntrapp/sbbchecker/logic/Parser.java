package net.ntrapp.sbbchecker.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

	private File dataset;
	private Scanner sc;
	private DataManager dm;

	public Parser() throws FileNotFoundException {
		this.dm = DataManager.getDataManager();
		this.dataset = dm.getFile();
		try {
			this.sc = new Scanner(dataset);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File " + dataset.getAbsolutePath() + " not found");
		}
		parse();
	}

	public void parse() {
		System.out.println(sc.nextLine());
	}

}
