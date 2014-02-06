package geometry;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Geometry {
	
	public static float[] getRectVertices(Rectangle r) {
		return new float[] {r.x,r.y,r.x+r.width,r.y,r.x+r.width,r.y+r.height,r.x,r.y+r.height};
	}
	
	public static void joinBasis(Polygon porg, Rectangle rect) {
		float[] vertices = porg.getVertices();
		if(vertices.length==0) {
			porg.setVertices(getRectVertices(rect));
			return;
		}
		List<Float> flist = new ArrayList<Float>();
		boolean added = false;
		for(int i=0;i<vertices.length/2;i++) {
			flist.add(vertices[i*2]);
			flist.add(vertices[i*2+1]);
			if(rect.contains(vertices[i*2], vertices[i*2+1])&&!added) {
				added = true;
				addContour(porg,rect,flist);
				flist.add(vertices[i*2]);
				flist.add(vertices[i*2+1]);
			} 
		} float[] newcontour = new float[flist.size()];
		int j=0;
		for(float f : flist) {
			newcontour[j] = f;
			j++;
		} porg.setVertices(newcontour);
	}

	private static void addContour(Polygon polygon, Rectangle rect, List<Float> floats) {
		float[] vertices = getRectVertices(rect);
		int s = 0;
		for(int i=0;i<vertices.length/2;i++) {
			if(polygon.contains(vertices[i*2], vertices[i*2+1])) {
				s = i;
			}
		} for(int j=0;j<vertices.length/2;j++) {
			floats.add(vertices[s*2]);
			floats.add(vertices[s*2+1]);
			s++;
			if(s>3) {
				s = 0;
			}
		}	floats.add(vertices[s*2]);
		floats.add(vertices[s*2+1]);
	}
	
//	public static float[] joinRough(Polygon porg, Rectangle rect) {
//		float[] vertices = porg.getVertices();
//		List<Float> flist = new ArrayList<Float>();
//		for(int i=0;i<vertices.length/2;i++) {
//			if(rect.contains(vertices[i*2], vertices[i]*2+1)) {
//				
//			}
//		}
//		
//	}

}
