package mapcomp;

import java.util.HashMap;
import java.util.Map;

import standards.Arrow;
import standards.LivingState;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Appearance {
	private TextureRegion def;
	private Map<LivingState,TextureRegion[]> images = new HashMap<LivingState, TextureRegion[]>();
	private Map<LivingState,Animation[]> animations = new HashMap<LivingState,Animation[]>();

	public Appearance() {
		
	}
	
	public TextureRegion getDefault() {
		return def;
	}
	
	public TextureRegion getTextureState(LivingState state, Arrow arrow, boolean anim, float delta) {
		if(anim) {
			return animations.get(state)[arrow.getOrder()].getKeyFrame(delta, true);									
		} else {
			return images.get(state)[arrow.getOrder()];
		}
	}
	
	public void setDefault(TextureRegion tr) {
		this.def = tr;
	}
	
	public void setAnimation(LivingState state, Animation[] anim) {
		animations.put(state, anim);
	}
	
	public void setTexture(LivingState state, TextureRegion[] text) {
		images.put(state, text);
	}

}
