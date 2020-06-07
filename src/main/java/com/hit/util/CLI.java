package com.hit.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class CLI implements Runnable {

	private Scanner scan;
	private PrintWriter writer;
	private String input, oldVal;
	private PropertyChangeSupport changes;

	public CLI(InputStream in, OutputStream out) {
		super();
		oldVal = "stop";
		scan = new Scanner(in);
		writer = new PrintWriter(out);
		changes = new PropertyChangeSupport(this);
	}

	@Override
	public void run() {
		write("Please enter your command\n");
		input = scan.next();
		input = input.toLowerCase();
		while (true) {

			switch (input) {
			case "start":
				if (oldVal.equalsIgnoreCase("start"))
					write("The server is already running\n");
				else
					write("Starting server......\n");
				setInput("start");
				break;
			case "stop":
				if (oldVal.equalsIgnoreCase("stop"))
					write("The server has already been stopped\n");
				else
					write("Shutdown server\n");
				setInput("stop");
				break;
			default:
				write("Only start/stop command\n");
				break;
			}
			write("Please enter your command\n");
			input = scan.next();
		}
	}

	public void setInput(String newValue) {
		changes.firePropertyChange("input", oldVal, newValue);
		oldVal = this.input;

	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		changes.addPropertyChangeListener(pcl);

	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		changes.removePropertyChangeListener(pcl);
	}

	public void write(String string) {
		writer.write(string);
		writer.flush();
	}

}
