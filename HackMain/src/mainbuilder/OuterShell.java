package mainbuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.FaultAction;

import substrate.Platform;
import utilities.IProperty;
import utilities.Property;

import mapcomp.IMapComponent;

public class OuterShell {
	private IMapComponent core;
	private Map<String,IProperty> properties = new HashMap<String, IProperty>();
	private boolean open = false;

	public OuterShell() {
		
	}
	
	public void load(IMapComponent prototype) throws Exception {
		properties.clear();
		this.core = prototype;
		for(IProperty p : prototype.getProperties()){
			properties.put(p.getName(), p);
		}
	}
	
	public void set(String property, IProperty value) {
		this.properties.get(property).set(value);
	}
	
	public Object get(String property) {
		if(this.properties.containsKey(property)) {
			return this.properties.get(property).getValue();
		} else {
			throw new NullPointerException("No such property");
		}
	}
	
	public Collection<IProperty> getProperties() {
		return properties.values();
	}
	
	public boolean admitsPresets() {
		return core instanceof Platform;
	}

	public IMapComponent retrieveProduct() {
		for(IProperty p : properties.values()) {
			try {
				p.validate(core,open);
			} catch (IllegalArgumentException | IllegalAccessException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return core;
	}
	
	public void change(String property, Object value) {
		this.properties.get(property).set(value);
	}
	
	
	
	
	
	

}


