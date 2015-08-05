package logic;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import manager.CommandManager;
import manager.SupportedCommands;
import model.Command;
import ui.Window;

public class Controller implements KeyListener, DropTargetListener {

	// Add view
	private Window window;
	// Add model
	private Command command;
	private CommandManager manager;
	// Key values
	private final int KEY_ENTER = 10;
	// Image path
	private String imagePath = "";

	public Controller() {
		this.window = new Window(this);
		this.command = new Command();

		this.command.addObserver(window);

		this.window.setVisible(true);
		this.manager = new CommandAction();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		try {
			if (e.getKeyCode() == KEY_ENTER) {
				final String[] input = window.getInput();
				if (input.length >= 1) {
					if (input[0].toUpperCase().equals(SupportedCommands.HELP.toString()))
						manager.help(command);
					else if (input[0].toUpperCase().equals(SupportedCommands.ABOUT.toString()))
						manager.about(command);
					else if (input[0].toUpperCase().equals(SupportedCommands.CLEAR.toString()))
						manager.clear(command);
					else if (input[0].toUpperCase().equals(SupportedCommands.UPLOAD.toString()))
						manager.upload(input[1], command);
					else if(input[0].toUpperCase().equals(SupportedCommands.YES.toString())) {
						if (!this.imagePath.isEmpty())
							manager.upload(this.imagePath, command);
						else
							manager.showText("No image selected.", command);
					} else if (input[0].toUpperCase().equals(SupportedCommands.NO.toString()))
						manager.clear(command);
					else if (input[0].toUpperCase().equals(SupportedCommands.EXIT.toString()))
						System.exit(0);
					else
						manager.unsupportedCommand(input[0], command);
				} else {
					manager.unsupportedCommand("$", command);
				}
				this.imagePath = "";
			}
		} catch (Exception ex) {
			manager.error(ex, command);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public synchronized void drop(DropTargetDropEvent arg0) {
		arg0.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
		Transferable t = arg0.getTransferable();
		if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			try {
				Object td = t.getTransferData(DataFlavor.javaFileListFlavor);
				if (td instanceof List) {
					for (Object value : ((List) td)) {
						if (value instanceof File) {
							File file = (File) value;
							this.imagePath = file.getAbsolutePath();
							manager.showAscii(file.getAbsolutePath(), this.command, this.window);
							manager.showText("Do you want to upload this image? [yes / no]",
									command);
						}
					}
				}
			} catch (UnsupportedFlavorException | IOException ex) {
				manager.error(ex, command);
			} catch (Exception ex) {
				manager.error(ex, command);
			}
		}
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
	}

}
