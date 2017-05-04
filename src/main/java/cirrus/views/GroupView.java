package cirrus.views;

import java.util.List;
import java.util.Set;

import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import cirrus.Sections;
import cirrus.backend.DocumentBackend;
import cirrus.backend.GroupBackend;
import cirrus.models.User;
import cirrus.models.UserGroup;


@SpringView(name = "groups")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Group View")
@FontAwesomeIcon(FontAwesome.GROUP)
public class GroupView extends VerticalLayout implements View {
	final DocumentBackend dBackend;
	final GroupBackend gBackend;
	Panel sidePanel;
	private boolean doubleClicked = false;
	private Button bTemp = null;
	private long clickTimer;
	
	public GroupView(DocumentBackend backend, GroupBackend groupBackend) {
		this.dBackend = backend;
		this.gBackend = groupBackend;
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
				SubWindow sub = new SubWindow(dBackend, gBackend);

				sub.setHeight("340px");
				sub.setWidth("400px");

				UI.getCurrent().addWindow(sub);
			}
		});

		titleBar.addComponent(addGroupBtn);
		titleBar.setComponentAlignment(addGroupBtn, Alignment.TOP_RIGHT);

		addComponent(titleBar);

		// Main layout to hold the panel
		HorizontalLayout mainLayout = new HorizontalLayout();
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		
		mainLayout.addLayoutClickListener(event -> {
			// Collapse side panel
			if (sidePanel.isVisible()) {
				sidePanel.setVisible(false);
			}
		});

		// Panel that displays documents
		Panel panel = createMainPanel(gBackend);
		mainLayout.addComponent(panel);
		mainLayout.setExpandRatio(panel, 1.0f);

		// Side panel for details
		sidePanel = new Panel("Details");
		mainLayout.addComponent(sidePanel);
		sidePanel.setHeight("100%");
		sidePanel.setWidth("250px");
		sidePanel.setVisible(false);

		// Add layout
		addComponent(mainLayout);
		this.setExpandRatio(mainLayout, 1.0f);
	}

	class SubWindow extends Window {
		public SubWindow(DocumentBackend d, GroupBackend g) {
			super("New Group");
			center();
			setClosable(false);
			setResizable(false);

			VerticalLayout subContent = new VerticalLayout();
			subContent.setSpacing(true);
			subContent.setMargin(new MarginInfo(true, true, true, true));
			
			HorizontalLayout newGroupLabel = new HorizontalLayout();
			subContent.addComponent(newGroupLabel);
			newGroupLabel.addComponent(new Label("Enter group name:   "));
			TextField groupName = new TextField();
			newGroupLabel.addComponent(groupName);

			// Twin Column to select users from list
			List<User> data = d.getAllUsers();
			TwinColSelect twinCol = new TwinColSelect(null, data);
			twinCol.setRows(6);
			twinCol.setLeftColumnCaption("Users");
			subContent.addComponent(twinCol);
			
			Button saveGroupButton = new Button("Create");
			saveGroupButton.addClickListener(event -> {
				
				// Create new group and populate with selected users
				UserGroup newGroup = new UserGroup(groupName.getValue(), d.getCurrentUser());
				Set<User> selectedUsers = (Set<User>) twinCol.getValue();
				for (User u : selectedUsers) {
					newGroup.addGroupMember(u);
				}
			
				g.saveGroup(newGroup);
				
				//Close subwindow Refresh page
				getUI().getNavigator().navigateTo("groups/");
				this.close();
			});

			HorizontalLayout buttons = new HorizontalLayout();
			subContent.addComponent(buttons);
			buttons.addComponent(saveGroupButton);
			buttons.addComponent(new Button("Cancel", event -> close()));
			subContent.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
			setContent(subContent);
		}
	}

	private Panel createMainPanel(GroupBackend groupBackend) {
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true); // Fix outside margin
		layout.setSpacing(true); // Fix spacing between buttons
		layout.setSizeFull();

		for (UserGroup g : groupBackend.getAllGroups()) {
			Button button = new Button(g.getGroupLabel());
			button.setData(g);
			button.setIcon(FontAwesome.USER);
			button.addClickListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					UserGroup u = (UserGroup) event.getButton().getData();
					// When successfully double clicked
					if (doubleClicked && bTemp.equals(event.getButton())
							&& System.currentTimeMillis() - clickTimer < 500) {
						// Do something - not implemented yet.

						// Reset double click condition and hide side panels
						doubleClicked = false;
						sidePanel.setVisible(false);
						// When single clicked
					} else {
						// Begin timer to check for double click and store
						// current button
						clickTimer = System.currentTimeMillis();
						bTemp = event.getButton();
						doubleClicked = true;

						// Generate and show side-panel
						sidePanel.setVisible(true);
						sidePanel.setContent(createSidePanel(u));
					}
				}
			});
			layout.addComponent(button);
		}

		panel.setSizeFull();
		panel.setContent(layout);
		panel.getContent().setSizeUndefined();
		return panel;
	}

	FormLayout createSidePanel(UserGroup g) {
		FormLayout content = new FormLayout();
		content.addComponent(new Label("<b>Owner:</b> " + g.getGroupOwner().getFirstName(), ContentMode.HTML));
		
		for (User u : g.getGroupMembers()) {
			content.addComponent(new Label("<b>Member:</b> " + u.getFirstName(), ContentMode.HTML));
		}
		
		content.setSizeFull();
		return content;
	}
	
	@Override
		public void enter(ViewChangeEvent event) {
	}
}