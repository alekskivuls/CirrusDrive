
package cirrus.views;

import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.ValoTheme;

import cirrus.Sections;
import cirrus.backend.Backend;
import cirrus.models.Document;
import cirrus.models.User;
import cirrus.models.UserGroup;

/**
 * When the user logs in and there is no view to navigate to, this view will be
 * shown.
 */

@SpringView(name = "groups")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Group View")
@FontAwesomeIcon(FontAwesome.GROUP)

public class GroupView extends VerticalLayout implements View {
	final Backend mBackend;
	
	class SubWindow extends Window {
		public SubWindow() {
			super("Group Editor");
			center();
			setClosable(false);
			setResizable(false);
			
			VerticalLayout subContent = new VerticalLayout();
			subContent.setSpacing(true);
			subContent.setMargin(new MarginInfo(true, true, true, true));
			HorizontalLayout newGroupLabel = new HorizontalLayout();
			newGroupLabel.setMargin(new MarginInfo(false, true, true, false));
			HorizontalLayout buttons = new HorizontalLayout();
			
			subContent.addComponent(newGroupLabel);
			subContent.addComponent(buttons);
			
			
			newGroupLabel.addComponent(new Label("Enter group name:   "));
			newGroupLabel.addComponent(new TextField());
			buttons.addComponent(new Button("OK"));
			buttons.addComponent(new Button("Cancel", event -> close()));
			setContent(subContent);
		}
	}

	public GroupView(Backend backend) {
		this.mBackend = backend;
		setSizeFull();
		setMargin(true);

		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth(95, Unit.PERCENTAGE);
		
		Label header = new Label("My Groups");
		header.addStyleName(ValoTheme.LABEL_H1);
		titleBar.addComponent(header);
		titleBar.setExpandRatio(header, 1.0f);
		
		Button addGroupBtn = new Button(FontAwesome.PLUS);
		addGroupBtn.setDescription("Add a new group");
		addGroupBtn.setSizeUndefined();
		
		addGroupBtn.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				// create and display a new group
				SubWindow sub = new SubWindow();
				
				sub.setHeight("200px");
				sub.setWidth("400px");
				
				UI.getCurrent().addWindow(sub);
			}
		});
		
		
		titleBar.addComponent(addGroupBtn);
		titleBar.setComponentAlignment(addGroupBtn, Alignment.TOP_RIGHT);
		
		addComponent(titleBar);

		Panel panel = createPanel();
		addComponent(panel);
		this.setExpandRatio(panel, 1.0f);
	}
	
	// group list
	Panel createPanel() {
		Panel panel = new Panel();
		CssLayout layout = new CssLayout();
		layout.setSizeFull();

		panel.setSizeFull();
		panel.setContent(layout);
		panel.getContent().setSizeUndefined();
		return panel;
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
