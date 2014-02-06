package utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parameter extends Property implements IParameter {
	private boolean enumerable;
	private final static Map<Class<?>,Class<?>> PRIM_CLASS_MATCH = new HashMap<Class<?>,Class<?>>() {
		{
			put(Float.class,float.class);
			put(Double.class,double.class);
			put(Integer.class,int.class);
		}
	};
	
	public Parameter(String name,Class type) {
		super(name,type);
	}
	
	public Parameter(DefParameter def) {
		super(def.name,def.type);
		this.enumerable = def.enumerable;
	}
	
	public static enum DefParameter {
		XPos("X",float.class),
		YPos("Y",float.class),
		Width("Width",float.class),
		Height("Height",float.class),
		Length("Length",int.class)
		;
		
		private String name;
		private Class type;
		private boolean enumerable = false;

		private DefParameter(String name, Class type) {
			this.name = name;
			this.type = type;
		}

		public Class getType() {
			return type;
		}
		
	}
	
	@Override
	public void set(Object value) {
		Class valtype = PRIM_CLASS_MATCH.get(value.getClass());
		super.set(value,valtype);
	}

	@Override
	public Object[] getPossibleValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnumerable() {
		return enumerable;
	}

}
