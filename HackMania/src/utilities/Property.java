package utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapcomp.MapComponent;

import substrate.Platform;
import utilities.Parameter.DefParameter;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Property implements IProperty {
	public static Map<String,Method> methods = gatherMethods();
	private final String name;
	private List<IParameter> attributes;
	private Object value;
	private Method validation;
	private Class type;
	private Method valmeth;

	public Property(DefProperty def, Object value) {
		this.name = def.name;
		this.type = def.type;
		this.attributes = new ArrayList<IParameter>();
		for(DefParameter dp : def.attributes) {
			this.attributes.add(new Parameter(dp));
		}
		this.valmeth = def.valmeth;
		this.set(value);
	}

	public Property(String name, Class type) {
		this.name = name;
		this.type = type;
	}

	public static enum DefProperty {
		POSITION("Position",Vector2.class,"set",DefParameter.XPos,DefParameter.YPos),
		BOX("Size",Rectangle.class,"set",DefParameter.XPos,DefParameter.YPos, 
				DefParameter.Width,DefParameter.Height),
		THEME("Theme"),
		LENGTH("Length",float.class,null);
		;
		
		public final String name;
		private List<DefParameter> attributes;
		private Method valmeth;
		private Class type;

		private DefProperty(String name) {
			this.name = name;
		}
		
		private DefProperty(String name, Class type, String method, DefParameter...parameters ) {
			this.name = name;
			this.type = type;
			this.attributes = new ArrayList<DefParameter>();
			if(method!=null){
				Class[] paramtypes = new Class[parameters.length];
				int i = 0;
				for(DefParameter p : parameters) {
					attributes.add(p);
					paramtypes[i] = p.getType();
					i++;
				} try {
					this.valmeth = this.type.getMethod(method, paramtypes);
				} catch (NoSuchMethodException e) {
					for(Class c : paramtypes){
						System.err.println(c);
					}
					System.err.println("no method found for "+method+" in "+name);
				}
			}
		}
		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void validate(Object receiver, boolean open) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if(open && valmeth!=null) {
			Object[] params = new Object[attributes.size()];
			for(int i=0; i<attributes.size();i++) {
				params[i] = attributes.get(i).getValue();
			} valmeth.invoke(value, params);
		} 
		validation.invoke(receiver, value);
	}

	@Override
	public void set(Object value) {
		if(value.getClass()==type) {
			this.value = value;
		} else {
			throw new ClassCastException();
		}
	}
	
	@Override
	public List<IParameter> getAttributes() {
		return attributes;
	}

	@Override
	public void setValidation(Method method) {
		this.validation = method;		
	}

	@Override
	public Object getValue() {
		return value;
	}

	protected void set(Object value, Class valtype) {
		if(valtype==type) {
			this.value = value;
		} else {
			throw new ClassCastException();
		}
		
	}
	
	private static Map<String, Method> gatherMethods() {
		Map<String,Method> methods = new HashMap<String, Method>();
		try {
			methods.put("setCoord", MapComponent.class.getMethod("setCoord", Vector2.class));
			methods.put("setLength", Platform.class.getMethod("setLength", int.class));
			methods.put("setBox", MapComponent.class.getMethod("setBox", Rectangle.class));
		} catch (NoSuchMethodException e) {
			System.err.println("Method "+ e.getLocalizedMessage() + " not found at initial gathering");
		}
		return methods;
	}

}
