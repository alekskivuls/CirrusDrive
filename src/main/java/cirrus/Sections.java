package cirrus;

import org.springframework.stereotype.Component;
import org.vaadin.spring.sidebar.annotation.SideBarSection;
import org.vaadin.spring.sidebar.annotation.SideBarSections;

/**
 * Component that is only used to declare the sections of the side bar.
 */
@Component
@SideBarSections({
        @SideBarSection(id = Sections.VIEWS, caption = "Views"),
        @SideBarSection(id = Sections.OPERATIONS, caption = "Operations")
})
public class Sections {

	public static final String VIEWS = "views";
	public static final String OPERATIONS = "operations";
}
