package help;

import items.Bullet;
import items.Circuitry;
import items.Electricity;
import items.ItemShell;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

import mapcomp.Block;
import mapcomp.IMapComponent;
import mapcomp.Scenery;
import modules.MapIO;

import substrate.Platform;
import utilities.ArrayUtils;
import utilities.IParameter;
import utilities.Priorator;
import utilities.Property;
import utilities.Property.DefProperty;
import geometry.Geometry;

import active.Gamer;
import active.Movable;
import active.Opponent;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.files.FileHandleStream;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class QuickTestUtils {
	private static PriorityQueue<IMapComponent> priorqueue =  new PriorityQueue<IMapComponent>(10, new Priorator());
	private static SortedSet<IMapComponent> sortedset = new ConcurrentSkipListSet<IMapComponent>(new Priorator());

	public QuickTestUtils() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		property();
		filesave();
//		Class c = Vector2.class;
//		float[] params = {1f,2f};
//		Vector2 v = new Vector2(0,0);
//		try {
//			Method m = c.getMethod("set", float.class,float.class);
//			m.invoke(v, params);
//		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} System.out.println(v);
		
//		fill(priorqueue);
//		IMapComponent grass = new Scenery(Scenery.Type.GRASS,new Vector2(0,0),1,1);
//		IMapComponent tree = new Scenery(Scenery.Type.TREE, new Vector2(0, 0), 1,1);
//		IMapComponent block = new Block(new Vector2(1,1));
//		IMapComponent gamer = new Gamer(new Vector2(1, 1));
//		while(priorqueue.size()!=0) {
//			System.out.println(priorqueue.poll());
//		} 
//		Priorator p = new Priorator();
//		System.out.println(p.compare(tree, gamer));
//		System.out.println(p.compare(gamer, block));
//		System.out.println(p.compare(block, tree));
//		System.out.println(p.compare(tree, grass));
		
	}

	private static void filesave() {
		String name = MapIO.getDefaultPath();
		Path path = Paths.get(name, "test");
		FileHandle filestream = new FileHandle(path.toString());
		OutputStream out = filestream.write(false);
		ObjectOutputStream outobj = null;
		try {
			outobj = new ObjectOutputStream(out);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			outobj.writeObject(new Array<Integer>());
			outobj.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	private static void property() {
		Property p = new Property(DefProperty.POSITION,new Vector2(1,2));
		try {
			p.setValidation(Block.class.getMethod("setCoord",Vector2.class));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<IParameter> list = p.getAttributes();
		list.get(0).set(5f);
		list.get(1).set(1.5f);
		Block b = new Block(new Vector2(2f, 1f));
		System.out.println(b.getCoord());
		try {
			p.validate(b,true);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(b.getCoord());
		
	}

	private static void fill(Collection<IMapComponent> c) {
		c.add(new Platform(new Vector2(12,4),7,1));
		c.add(new Opponent(new Vector2(8f,2f)));
		c.add(new Block(new Vector2(5f, 1f)));
		c.add(new Block(new Vector2(5f, 2f)));
		c.add(new Block(new Vector2(5f, 3f)));
		c.add(new Scenery(Scenery.Type.TREE,new Vector2(7f,1f),3f,4f));
		c.add(new Scenery(Scenery.Type.GRASS,new Vector2(5,4),0.5f,0.5f));
		c.add(new Scenery(Scenery.Type.ROCK,new Vector2(0,1),1,1));
		c.add(new ItemShell(new Vector2(5,4), new Electricity.Holder(10)));
		c.add(new ItemShell(new Vector2(10,5), new Circuitry.Holder(10)));		
		c.add(new Bullet());
		
	}

}
