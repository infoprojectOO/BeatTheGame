package substrate;

import mapcomp.IMapComponent;
import items.Throwable;
import geometry.Geometry;
import standards.Axis;
import view.VirtuaRenderer;

import active.Gamer;
import active.Movable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Collision {
	private static Filmtub physworld;
	private static Array<Throwable> thrown = new Array<Throwable>();

	public Collision() {
		// TODO Auto-generated constructor stub
	}
	
	public static Contact detect(Rectangle rect,Vector2 dl,IMapComponent caller){
		Array<IMapComponent> candidates = physworld.getObjects(rect,caller);
//		candidates.addAll(thrown);
		Array<IMapComponent> shockteam = new Array<IMapComponent>();
		Contact detection = new Contact();
		for(IMapComponent candidate : candidates){
//			if(caller instanceof Gamer) System.out.println(candidate);
			if(candidate.getBox().overlaps(rect)) {
				if(!candidate.allowAccess(caller)) {
					shockteam.add(candidate);
				}
				detection.addHit(candidate);
			}
		} if(shockteam.size!=0) {
			AjustTrajectory(rect,dl,shockteam,caller,detection);
		} 
		return detection;
	}
	
	public static Array<IMapComponent> hit(Throwable missile) {
		Array<IMapComponent> candidates = physworld.getObjects(missile.getBox(),missile);
		Array<IMapComponent> crashed = new Array<IMapComponent>();
		for(IMapComponent imc : candidates) {
			if(imc.getBox().overlaps(missile.getBox())) {
				crashed.add(imc);
			}
		} return crashed;
		
	}
	
	private static void AjustTrajectory(Rectangle rect, Vector2 dl,
			Array<IMapComponent> shockteam, IMapComponent collider, Contact contact) {
		Vector2 inipos = collider.getBox().getPosition(new Vector2());
		Rectangle ajustx = new Rectangle(rect);
		ajustx.setX(inipos.x);
		Rectangle ajusty = new Rectangle(rect);
		ajusty.setY(inipos.y);
		for(IMapComponent collided : shockteam) {
			Rectangle zone = collided.getBox();
			if(zone.overlaps(ajusty)) {
				contact.onX(true,ajusty.x-inipos.x,collided.getEntity());
				repelX(zone, ajusty, collider.getBox());
				rect.setX(ajusty.x);
			} if(zone.overlaps(ajustx)) {
				contact.onY(true,ajustx.y-inipos.y,collided.getEntity());
				repelY(zone, ajustx, collider.getBox());
				rect.setY(ajustx.y);
			}
		} 
	}

//	private static float[] scaleup(float[] vertices) {
//		float[] floats = new float[vertices.length];
//		for(int i=0;i<vertices.length/2;i++) {
//			floats[2*i] = vertices[2*i]*VirtuaRenderer.scaleX;
//			floats[2*i+1] = vertices[2*i+1]*VirtuaRenderer.scaleY;
//		} return floats;
//
//	}

	private static void repelX(Rectangle host, Rectangle invader, Rectangle origin) {
		if(invader.x-origin.x>0) {
			invader.x = host.x-invader.width;
		} else {
			invader.x = host.x+host.width;
		}
	}
	
	private static void repelY(Rectangle host, Rectangle invader, Rectangle origin) {
		if(invader.y-origin.y>0) {
			invader.y = host.y-invader.height;
		} else {
			invader.y = host.y+host.height;
		}
	}

	private static Contact AjustTrajectory(Rectangle rect, Vector2 vel, Polygon polygon, IMapComponent collider) {
		Rectangle ajustx = new Rectangle(rect);
		ajustx.setX(collider.getCoord().x);
		Rectangle ajusty = new Rectangle(rect);
		ajusty.setY(collider.getCoord().y);
		Intersector.MinimumTranslationVector minvectx = new Intersector.MinimumTranslationVector();
		Intersector.MinimumTranslationVector minvecty = new Intersector.MinimumTranslationVector();
		Intersector.overlapConvexPolygons(polygon, new Polygon(Geometry.getRectVertices(ajustx)),minvectx);
		Intersector.overlapConvexPolygons(polygon, new Polygon(Geometry.getRectVertices(ajusty)),minvecty);
//		System.out.println(minvectx.depth);
//		System.out.println(minvecty.depth);
		if(minvecty.depth==0) {
			rect.set(ajusty);
			return new Contact(null,Axis.Y);
		} else if(minvectx.depth==0) {
			rect.set(ajustx);
			return new Contact(Axis.X,null);
		} else {
			rect.set(collider.getBox());
			return new Contact(Axis.X,Axis.Y);
		}
		
	}

	public static void setWorld(Filmtub world) {
		Collision.physworld = world;
	}

	public static void addThrowable(Throwable bullet) {
		Collision.thrown.add(bullet);
		physworld.add(bullet);
	}

	public static void destroy(Throwable bullet) {
		Collision.thrown.removeValue(bullet, false);
		physworld.remove(bullet);
	}
	
	public static void destroy(IMapComponent imc) {
		physworld.remove(imc);
	}
	
	public static void destroy(Movable animated) {
		physworld.remove(animated);
		
	}

}
