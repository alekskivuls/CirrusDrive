package cirrus.templates;

import java.util.List;

import com.vaadin.ui.Component;

public abstract class Descriptor
{
	public List<Component> getLoadOrder()
	{
		return mLoadOrder;
	}
	
	protected abstract void init();
	
	protected List<Component> mLoadOrder;
}