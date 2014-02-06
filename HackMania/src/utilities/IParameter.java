package utilities;

public interface IParameter extends IProperty {
	Object[] getPossibleValues();
	boolean isEnumerable();
}
