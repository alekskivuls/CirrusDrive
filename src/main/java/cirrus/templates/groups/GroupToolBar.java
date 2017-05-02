package cirrus.templates.groups;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

public class GroupToolBar extends HorizontalLayout
{
	public Label header;
	public Button addGroupBtn;
	
	public GroupToolBar()
	{
		this.setWidth(95, Unit.PERCENTAGE);
		
		header = new Label("My Groups");
		header.addStyleName(ValoTheme.LABEL_H1);
		
		this.addComponent(header);
		
		addGroupBtn = new Button(FontAwesome.PLUS);
		addGroupBtn.setId("addGroupBtn");
		addGroupBtn.setDescription("Add a new group");
		addGroupBtn.setSizeUndefined();
		
		addGroupBtn.addClickListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				// create and display a new group
				GroupEditorWindow subWindow = new GroupEditorWindow();
				
				UI.getCurrent().addWindow(subWindow);
			}
		});
		
		this.addComponent(addGroupBtn);
		this.setComponentAlignment(addGroupBtn, Alignment.TOP_RIGHT);
	}
}
