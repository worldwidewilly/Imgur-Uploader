package manager;

import java.io.IOException;
import model.Command;
import org.json.simple.parser.ParseException;
import ui.Window;


public interface CommandManager {

	public void upload(String imagePath, Command command) throws IOException, ParseException;

	public void help(Command command);

	public void about(Command command);

	public void clear(Command command);

	public void error(Exception ex, Command command);

	public void unsupportedCommand(String text, Command command);

	public void showAscii(String imagePath, Command command, Window window) throws Exception;
	
	public void showText(String text, Command command);

	public void addToClipboard(String text, Command command);

}
