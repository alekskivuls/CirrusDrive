
package cirrus.views;

import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import cirrus.Sections;
import cirrus.backend.Backend;
import cirrus.descriptors.GroupDescriptor;

/**
 * When the user logs in and there is no view to navigate to, this view will be
 * shown.
 */

@SpringView(name = "groups")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Group View")
@FontAwesomeIcon(FontAwesome.GROUP)

public class GroupView extends VerticalLayout implements View
{
	GroupDescriptor mGroupView;
	final Backend mBackend;

	public GroupView( Backend backend )
	{
		this.mBackend = backend;
		this.setSizeFull();
		this.setMargin(true);
		
		mGroupView = new GroupDescriptor();
		
		
		for( Component component : mGroupView.getLoadOrder() )
		{
			this.addComponent( component );
			if( component instanceof Button )
			{
				Button b = (Button) component;
				if( b.getId().equals("addGroupBtn") )
				{
					this.setComponentAlignment(b, Alignment.TOP_RIGHT); // titlebar
				}
			}
			else if( component instanceof Label )
			{
				this.setExpandRatio(component, 1.0f); // titlebar
			}
			else if( component instanceof Panel )
			{
				this.setExpandRatio(component, 1.0f);
			}
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
