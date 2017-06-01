package cirrus.templates.documents;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class DocumentTabs extends TabSheet {
	Label console;

	public DocumentTabs() {
		setId("DocumentTabs");
		setHeight(200, Unit.PIXELS);
		addStyleName(ValoTheme.TABSHEET_FRAMED);
		addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

		String[] tabNames = new String[] { "Console" };
		for (int i = 0; i < tabNames.length; ++i) {
			// Change to TextArea and send Console Output here.
			// Try this:
			// TextArea docBody = new TextArea();
			// docBody.setWordwrap(false);
			// docBody.setSizeFull();

			console = new Label("", ContentMode.PREFORMATTED);
			console.setWidth(100.0f, Unit.PERCENTAGE);

			final VerticalLayout tab = new VerticalLayout(console);
			tab.setMargin(true);
			//tab.setSizeFull();

			addTab(tab, tabNames[i]);
		}

	}

	public void appendConsole(String text) {
		console.setValue(console.getValue() + text);
	}

}
