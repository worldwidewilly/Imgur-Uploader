package model;

import java.util.Observable;

public class Command extends Observable {
	
	private String text;
	
	public Command() {
		this.text = "";
	}

	public void setText(String text) {
		assert text != null : "Command.setText() String text is null";
		assert !text.isEmpty() : "Command.setText() String text is empty";

		this.text = text;
		setChanged();
		notifyObservers(this);
	}

	public void addText(String text) {
		assert text != null : "Command.setText() String text is null";
		assert !text.isEmpty() : "Command.setText() String text is empty";

		this.text += text + "\n";
		setChanged();
		notifyObservers(this);
	}

	public String getText() {
		return text;
	}
}
