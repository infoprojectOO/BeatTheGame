package utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public interface IProperty {
	String getName();
	List<IParameter> getAttributes();
	void set(Object value);
	void setValidation(Method method);
	void validate(Object receiver, boolean open) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	Object getValue();
	
}
