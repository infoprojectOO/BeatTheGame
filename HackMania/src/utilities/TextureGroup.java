package utilities;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class TextureGroup {
	private HashMap<String,Array<TextureRegion>> map = new HashMap<String,Array<TextureRegion>>();

	public TextureGroup() {
		
	}
	
	public void addRegion(TextureRegion region,String name) {
		Array<TextureRegion> arr = map.get(name);
		if (arr == null) {
			arr = new Array<TextureRegion>();
			map.put(name, arr);
		} arr.add(region);
	}
	
//	public Array<TextureRegion[]> getGroups() {
//		Array<TextureRegion[]> res = new Array<TextureRegion[]>();
//		for(Array<TextureRegion> arr : map.values()) {
//			res.add(arr.toArray());
//		} return res;
//	}
	
	public HashMap<String,Array<TextureRegion>> getGroups() {
		return map;
	}
	

}
