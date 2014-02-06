package help;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import mapcomp.Block;

import com.badlogic.gdx.math.Vector2;

import standards.Arrow;

import active.Gamer;

public class QuickTestGeneral {

	public static void main(String[] args) {
		methodTest();
	}
	
	public static void methodTest() {
		Method m = null;
		try {
			m = Gamer.class.getMethod("setArrow");
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		} catch (SecurityException e) {
			
			e.printStackTrace();
		}
		Gamer g = new Gamer(new Vector2(0,0));
		try {
			m.invoke(g,Arrow.DOWN);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} System.out.println(g.getArrow());
	}

}
