package cirrus.descriptors;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import cirrus.templates.Descriptor;


public class GroupDescriptor extends Descriptor
{
	public GroupDescriptor()
	{
		mLoadOrder = new LinkedList<Component>();
		this.init();
	}
	
	@Override
	protected void init()
	{
		this.mLoadOrder.add(this.initToolbar());
		this.mLoadOrder.add(this.initPanel());
	}
	
	private Label initHeader()
	{
		Label header = new Label("My Groups");
		header.addStyleName(ValoTheme.LABEL_H1);
		return header;
	}
	
	private Button initAddGroupButton()
	{
		Button addGroupBtn = new Button(FontAwesome.PLUS);
		addGroupBtn.setId("addGroupBtn");
		addGroupBtn.setDescription("Add a new group");
		addGroupBtn.setSizeUndefined();
		
		addGroupBtn.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				// create and display a new group
				SubWindow sub = new SubWindow();
				
//				sub.setWidth(75, Unit.PERCENTAGE);
//				sub.setHeight(75, Unit.PERCENTAGE);
				
				UI.getCurrent().addWindow(sub);
			}
		});
		
		return addGroupBtn;
	}
	
	private HorizontalLayout initToolbar()
	{
		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth(95, Unit.PERCENTAGE);
		
		Label header = this.initHeader();
		titleBar.addComponent(header);
		
		Button addGroupBtn = this.initAddGroupButton();
		
		
		titleBar.addComponent(addGroupBtn);
		titleBar.setComponentAlignment(addGroupBtn, Alignment.TOP_RIGHT);
		
		
		return titleBar;
	}
	
	private Panel initPanel()
	{
		Panel panel = new Panel();
		CssLayout layout = new CssLayout();
		layout.setSizeFull();

		panel.setSizeFull();
		panel.setContent(layout);
		panel.getContent().setSizeUndefined();
		return panel;
	}
	
	class SubWindow extends Window
	{
		public SubWindow()
		{
			super("Group Editor");
			this.center();
			this.setClosable(true);
			this.setResizable(true);
			
			VerticalLayout subContent = new VerticalLayout();
			subContent.setSpacing(true);
			subContent.setMargin(new MarginInfo(true, true, true, true));
			
			
			HorizontalLayout groupLabel = new HorizontalLayout();
			groupLabel.setHeightUndefined();
			groupLabel.setSpacing(true);
			groupLabel.setMargin(new MarginInfo(false, true, false, true));
			groupLabel.addComponent(new Label("Enter group name:   "));
			groupLabel.addComponent(new TextField());
			
			HorizontalLayout userLayout = new HorizontalLayout();
			userLayout.setHeightUndefined();
			userLayout.setSpacing(true);
			userLayout.setMargin(new MarginInfo(false, true, false, true));
			userLayout.addComponent(getUserList());
			userLayout.addComponent(getCurrentList());
			
			
			HorizontalLayout buttons = new HorizontalLayout();
			buttons.setHeightUndefined();
			buttons.setSpacing(true);
			buttons.setMargin(new MarginInfo(false, true, false, true));
			buttons.addComponent(new Button("Save"));
			buttons.addComponent(new Button("Cancel", event -> close()));
			
			
			subContent.addComponent(groupLabel);
			subContent.addComponent(userLayout);
			subContent.addComponent(buttons);
//			window.setContent(subContent);
//			this.setContent(window);
			this.setContent(subContent);
		}
		
		private ListSelect getUserList()
		{
			List<String> data = IntStream.range(0, 6).mapToObj(i -> "Option " + i).collect(Collectors.toList());
			
			ListSelect listOfUsers = new ListSelect("Select an option", data);
			listOfUsers.setRows(10);
			listOfUsers.setWidth(150.0f, Unit.PIXELS);
			listOfUsers.setMultiSelect( true );
//			listOfUsers.addValueChangeListener(event -> Notification.show("Value changed:", String.valueOf(((Label) event).getValue()),
//					Type.TRAY_NOTIFICATION));
	        
	        return listOfUsers;
		}
		
		private ListSelect getCurrentList()
		{
			List<String> data = new LinkedList<String>();
			
			ListSelect listOfUsers = new ListSelect("Select an option", data);
			listOfUsers.setRows(10);
			listOfUsers.setWidth(150.0f, Unit.PIXELS);
			listOfUsers.setMultiSelect( true );
	        return listOfUsers;
		}
	}
}