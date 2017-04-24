package cirrus.templates.documents;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;

public class BuildMenuBar extends MenuBar {
	MenuItem build, run, stop;

	public BuildMenuBar() {
		build = addItem("Build", FontAwesome.CHECK_CIRCLE, null);
		run = addItem("Run", FontAwesome.PLAY_CIRCLE_O, null);
		stop = addItem("Stop", FontAwesome.STOP_CIRCLE_O, null);
	}

	public void setBuildCmd(Command command) {
		build.setCommand(command);
	}

	public void setRunCmd(Command command) {
		run.setCommand(command);
	}

	public void setStopCmd(Command command) {
		stop.setCommand(command);
	}
}
