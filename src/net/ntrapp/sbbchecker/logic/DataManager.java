package net.ntrapp.sbbchecker.logic;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * This class manages the necessary data including:
 * File variable for the dataset
 * arrivalCount variable to count the sum of arrivals
 * arrivalLateCount variable to count the sum of late arrivals
 * 
 * @author trappn
 * @version 1.0
 * @since 1.0
 */
public class DataManager {

	private int arrivalCount;
	private int arrivalLateCount;

	private static DataManager datamanager_instance = null;
	
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyy");
	private static final String DATE = LocalDate.now().minus(1, ChronoUnit.DAYS).format(DATE_FORMAT);

	private DataManager() {
		this.arrivalCount = 0;
		this.arrivalLateCount = 0;
	}

	public static DataManager getDataManager() {
		if (datamanager_instance == null) {
			datamanager_instance = new DataManager();
		}
		return datamanager_instance;
	}

	/**
	 * This method returns a File variable for the dataset
	 * 
	 * If the dataset file is not existent, this method calls the downloadDataset()
	 * method from Downloader
	 * 
	 * @return File
	 * @since 1.0
	 */
	public File getFile() {
		return new File(String.format("./dataset/%s.csv", DATE));
	}

	/**
	 * This method sets the arrivalCount variable to the given value
	 * 
	 * @param arrivalCount the new value
	 * @since 1.0
	 */
	public void setArrivalCount(int arrivalCount) {
		this.arrivalCount = arrivalCount;
	}

	/**
	 * This method increases the arrivalCount by 1
	 * 
	 * @since 1.0
	 */
	public void increaseArrivalCount() {
		this.arrivalCount++;
	}

	/**
	 * This method returns the value of arrivalCount
	 * 
	 * @return arrivalCount
	 * @since 1.0
	 */
	public int getArrivalCount() {
		return this.arrivalCount;
	}

	/**
	 * This method sets the arrivalLateCount variable to the given value
	 * 
	 * @param arrivalLateCount the new value
	 * @version 1.0
	 * @since 1.0
	 */
	public void setArrivalLateCount(int arrivalLateCount) {
		this.arrivalLateCount = arrivalLateCount;
	}

	/**
	 * This method increases the arrivalLateCount by 1
	 * 
	 * @version 1.0
	 * @since 1.0
	 */
	public void increaseArrivalLateCount() {
		this.arrivalLateCount++;
	}

	/**
	 * This method returns the value of arrivalLateCount
	 * 
	 * @return arrivalLateCount
	 * @version 1.0
	 * @since 1.0
	 */
	public int getArrivalLateCount() {
		return this.arrivalLateCount;
	}

}
