package mapcomp;

import java.io.Serializable;
import java.util.List;

import items.Throwable;

import active.Awakener;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import standards.Access;
import standards.Axis;
import standards.Prior;
import utilities.IProperty;

public interface IMapComponent extends IPrototype<IMapComponent>, Serializable {
	
	float getBreadth(Axis axis);
	int prior(IMapComponent rival);
	double getYRatio();
	double getXRatio();
	Vector2 getCoord();
	void setCoord(Vector2 coord);
	Rectangle getBox();
	String getID();
	void hit(Throwable bullet);
	Prior getPrior();
	void setPrior(Prior p);
	String getRootFolder();
	void awaken(Awakener awakener);
	Access getAccess();
	boolean allowAccess(IMapComponent visitor);
	Entity getEntity();
	List<IProperty> getProperties() throws Exception;
	void setBox(Rectangle rect);

}
