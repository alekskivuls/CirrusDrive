package cirrus.templates.groups;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class GroupEditorWindow extends Window
{
	public VerticalLayout subContent;
	public HorizontalLayout groupLabel, userLayout, buttons;
	
	public GroupEditorWindow()
	{
		super("Group Editor");
		this.center();
		this.setClosable(true);
		this.setResizable(true);
		
		subContent = new VerticalLayout();
		subContent.setSpacing(true);
		subContent.setMargin(new MarginInfo(true, true, true, true));
		
		
		groupLabel = new HorizontalLayout();
		groupLabel.setHeightUndefined();
		groupLabel.setSpacing(true);
		groupLabel.setMargin(new MarginInfo(false, true, false, true));
		groupLabel.addComponent(new Label("Enter group name:   "));
		groupLabel.addComponent(new TextField());
		
		userLayout = new HorizontalLayout();
		userLayout.setHeightUndefined();
		userLayout.setSpacing(true);
		userLayout.setMargin(new MarginInfo(false, true, false, true));
		userLayout.addComponent(getUserList());
		
		
		buttons = new HorizontalLayout();
		buttons.setHeightUndefined();
		buttons.setSpacing(true);
		buttons.setMargin(new MarginInfo(false, true, false, true));
		buttons.addComponent(new Button("Save"));
		buttons.addComponent(new Button("Cancel", event -> close()));
		
		
		subContent.addComponent(groupLabel);
		subContent.addComponent(userLayout);
		subContent.addComponent(buttons);
		
		this.setContent(subContent);
	}
	
	private TwinColSelect getUserList()
	{
		List<String> data = IntStream.range(0, 6).mapToObj(i -> "User " + i).collect(Collectors.toList());
		
		TwinColSelect<String> listOfUsers = new TwinColSelect<>(null, data);
		listOfUsers.setLeftColumnCaption("All Users:");
		listOfUsers.setRightColumnCaption("Group Users:");
		listOfUsers.setRows(10);
		listOfUsers.setWidth(100.0f, Unit.PERCENTAGE);
//		listOfUsers.setWidth(150.0f, Unit.PIXELS);
//		listOfUsers.setMultiSelect( true );
//		listOfUsers.addValueChangeListener(event -> Notification.show("Value changed:", String.valueOf(((Label) event).getValue()),
//				Type.TRAY_NOTIFICATION));
        
        return listOfUsers;
	}
}