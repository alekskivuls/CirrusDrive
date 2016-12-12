
package cirrus.views;

import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.ValoTheme;

import cirrus.Sections;
import cirrus.backend.Backend;
import cirrus.models.Document;

/**
 * When the user logs in and there is no view to navigate to, this view will be
 * shown.
 */
@Theme("HomeTheme")
@SpringView(name = "")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Home", order = 0)
@FontAwesomeIcon(FontAwesome.HOME)
public class HomeView extends VerticalLayout implements View {
	final Backend mBackend;

	public HomeView(Backend backend) {
		this.mBackend = backend;
		setSizeFull();
		setMargin(true);

		Label header = new Label("Welcome to Cirrus Drive!");
		header.addStyleName(ValoTheme.LABEL_H1);
		addComponent(header);

		Panel panel = createPanel();
		addComponent(panel);
		this.setExpandRatio(panel, 1.0f);
	}

	Panel createPanel() {
		Panel panel = new Panel();
		CssLayout layout = new CssLayout();
		layout.setSizeFull();

		for (Document doc : mBackend.getUsersDocs()) {
			Button button = new Button(doc.getDocName());
			button.setData(doc);
			button.setIcon(FontAwesome.FOLDER);
			button.addStyleName(BaseTheme.BUTTON_LINK);
			button.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					Document doc = (Document) event.getButton().getData();
					getUI().getNavigator().navigateTo("document/" + doc.getDocId());
				}
			});
			layout.addComponent(button);
		}

		panel.setSizeFull();
		panel.setContent(layout);
		panel.getContent().setSizeUndefined();
		return panel;
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
