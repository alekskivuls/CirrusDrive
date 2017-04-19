package cirrus.templates.documents;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PreferencesSubwindow extends Window {
	public PreferencesSubwindow() {
		super("Preferences"); // Set window caption

		this.center();
		this.setResizable(true);
		this.setWidth(50, Unit.PERCENTAGE);
		this.setHeight(50, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		content.addComponent(new CheckBox("Show Line Numbers"));

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(new Button("Save"));
		buttons.addComponent(new Button("Close preferences", event -> close()));

		// Disable the close button
		this.setClosable(false);
		this.setResizable(false);

		content.addComponent(buttons);
		this.setContent(content);
	}
}