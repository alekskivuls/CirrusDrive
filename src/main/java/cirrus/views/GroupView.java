
package cirrus.views;

import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cirrus.Sections;
import cirrus.backend.Backend;
import cirrus.templates.documents.BuildMenuBar;
import cirrus.templates.documents.DocumentPanel;
import cirrus.templates.documents.DocumentTabs;
import cirrus.templates.documents.FileMenuBar;
import cirrus.templates.documents.PreferencesSubwindow;
import cirrus.templates.groups.GroupEditorWindow;
import cirrus.templates.groups.GroupToolBar;

/**
 * When the user logs in and there is no view to navigate to, this view will be
 * shown.
 */

@SpringView(name = "groups")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Group View")
@FontAwesomeIcon(FontAwesome.GROUP)

public class GroupView extends VerticalLayout implements View
{
	private TwinColSelect listOfUsers;
	private GroupToolBar groupToolBar;
	private Panel panel;
	
	final Backend mBackend;

	public GroupView( Backend backend )
	{
		this.mBackend = backend;
		this.setSizeFull();
		this.setMargin(true);
		
		groupToolBar = new GroupToolBar();
		this.addComponent( groupToolBar );
		
		panel = new Panel();
		CssLayout layout = new CssLayout();
		layout.setSizeFull();
		panel.setSizeFull();
		panel.setContent(layout);
		panel.getContent().setSizeUndefined();
		this.addComponent( panel );
		this.setExpandRatio(panel, 1.0f);
	}

	@Override
	public void enter(ViewChangeEvent event) {
//		listOfUsers.addValueChangeListener(event -> Notification.show("Value changed:", String.valueOf(event.getValue()),
//                Type.TRAY_NOTIFICATION));
	}
}
