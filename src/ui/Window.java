package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.dnd.DropTarget;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import logic.CommandAction;
import logic.Controller;
import model.Command;

public class Window extends JFrame implements Observer {

	// Window values
	private final int WINDOW_WIDTH = 750;
	private final int WINDOW_HEIGHT = 400;
	private final String TITLE = "IUNA";

	// Window components
	private JTextArea outputLog;
	private JScrollPane outputLogScrollPane;
	private final Font font = new Font("Serif", Font.ITALIC, 20);

	public Window(Controller controller) {
		super.setTitle(this.TITLE);
		super.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Add text area
		this.outputLog = new JTextArea();
		this.outputLog.setForeground(Color.GREEN);
		this.outputLog.setBackground(Color.BLACK);
		this.outputLog.setFont(this.font);
		// Add key listener of the controller
		this.outputLog.addKeyListener(controller);
		// Add drop listener of the controller
		new DropTarget(outputLog, controller);
		this.outputLog.setDragEnabled(true);
		// Add scroll bar
		this.outputLogScrollPane = new JScrollPane(outputLog);
		this.outputLogScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.outputLogScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		super.getContentPane().add(outputLogScrollPane);
	}

	public String[] getInput() {
		String[] withoutNewlines = this.outputLog.getText().split("\n");
		List<String> withoutSpaces = new ArrayList<String>();
		for (String withSpace : withoutNewlines)
			for (String withoutSpace : withSpace.split(" "))
				withoutSpaces.add(withoutSpace);
		for (int i = withoutSpaces.size() - 1; i >= 0; i--)
			if (withoutSpaces.get(i).equals(CommandAction.SEPARATOR)) {
				String[] input =
						(String[]) withoutSpaces.subList(i + 1, withoutSpaces.size()).toArray(
								new String[withoutSpaces.size() - (i + 1)]);
				return input;
			}
		return (String[]) withoutSpaces.toArray(new String[withoutSpaces.size()]);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Command) this.outputLog.setText(((Command) arg).getText());
	}

}
