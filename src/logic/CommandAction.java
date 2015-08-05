package logic;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import manager.CommandManager;
import model.Command;
import org.json.simple.parser.ParseException;
import ui.Window;

public class CommandAction implements CommandManager {

	private final String ABOUT =
			"***\tThis is a programm made by\t***\n***\tWorldWideWilly@sh.it\t\t***\n***\tVersion: 0.1\t\t\t***";
	private final String HELP =
			"***\tAvailable commands\t***\nupload {image path}\nabout\nclear\nhelp\nexit\n***\t***";
	public static final String SEPARATOR = "$";
	private final String SPACE = " ";
	private final String lineBreak = "\n";
	
	@Override
	public synchronized void upload(String imagePath, Command command) throws IOException,
			ParseException {
		command.setText("uploading image ***\n");
		String link = Uploader.upload(imagePath);
		command.setText("image upload complete.\n");
		command.addText("link: " + link + SPACE + SEPARATOR);
		addToClipboard(link, command);
	}

	@Override
	public synchronized void help(Command command) {
		command.setText(HELP + SPACE + SEPARATOR + lineBreak);
	}

	@Override
	public synchronized void about(Command command) {
		command.setText(ABOUT + SPACE + SEPARATOR + lineBreak);
	}

	@Override
	public synchronized void error(Exception ex, Command command) {
		command.setText(ex.toString() + SPACE + SEPARATOR);
	}

	@Override
	public synchronized void unsupportedCommand(String text, Command command) {
		command.addText(text + "\nThis command is unsupported." + SPACE + SEPARATOR);
	}

	@Override
	public synchronized void showAscii(String imagePath, Command command, Window window)
			throws Exception {
		command.setText("");
		AsciiConverter.convertToAscii(imagePath, command);
		command.addText(SPACE + SEPARATOR);
	}

	@Override
	public synchronized void clear(Command command) {
		command.setText("");
	}

	@Override
	public synchronized void showText(String text, Command command) {
		command.addText(text + SPACE + SEPARATOR);
	}

	@Override
	public synchronized void addToClipboard(String text, Command command) {
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents((Transferable) new StringSelection(text), null);
		command.addText("Link added to clipboard." + SPACE + SEPARATOR);
	}

}
