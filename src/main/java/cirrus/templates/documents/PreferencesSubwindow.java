package cirrus.templates.documents;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PreferencesSubwindow extends Window {
	public PreferencesSubwindow() {
		super("Preferences"); // Set window caption

		this.center();
		this.setResizable(true);
		this.setWidth(50, Unit.PERCENTAGE);
		this.setHeight(50, Unit.PERCENTAGE);

		VerticalLayout content = new VerticalLayout();
		CheckBox checkbox = new CheckBox("Show Line Numbers");
		content.addComponent(checkbox);

		HorizontalLayout buttons = new HorizontalLayout();
		Button save = new Button("Save");
		buttons.addComponent(save);
		buttons.addComponent(new Button("Close preferences", event -> close()));

		// Disable the close button
		this.setClosable(false);
		this.setResizable(false);

		content.addComponent(buttons);
		this.setContent(content);
		
		save.addClickListener(event -> {
			if (checkbox.getValue()){
				showLineNumbers();
				checkbox.setValue(true);
			}
			else
				hideLineNumbers();
			});
	}
	
	public void showLineNumbers() {

		// Display line numbers on left side using JS
		// TODO: Figure out how to move style code to CSS file
		StringBuilder sb = new StringBuilder("var panel = document.querySelector('div.v-panel-content');");
		sb.append("var textPanel = document.querySelector('.v-panel-content'); var textArea = document.querySelector('textarea');")
			.append("var p = document.createElement('div');p.classList.add('lineNumbers');p.style.position = 'absolute';")
			.append("p.style.width = '30px'; p.style.height = '100%'; textArea.style.paddingLeft = '30px';")
			.append("textArea.style.fontFamily = 'courier'; p.style.fontFamily = 'courier'; textPanel.insertBefore(p, textArea);")
			.append("for (var i = 1; i <= 50; i++) {var num = document.createElement('div');num.innerHTML = i;p.appendChild(num);num.style.margin = '0';")
			.append("num.style.height ='18px';textArea.style.paddingTop = '3px';}")
			.append("textArea.setAttribute('spellcheck','off');")
			.append("p.style.margin = '0'; p.style.border ='solid .5px #ddd';");
								
		String script = sb.toString();	
		JavaScript.getCurrent().execute(script);
	}
	
	public void hideLineNumbers() {
		StringBuilder sb = new StringBuilder("var panel = document.querySelector('div.v-panel-content');");	
		sb.append("var lineNumbers = panel.querySelector('.lineNumbers'); panel.removeChild(lineNumbers); panel.children[0].style.setProperty('padding-left','0');");
		
		String script = sb.toString();
		JavaScript.getCurrent().execute(script);

		
	}
}