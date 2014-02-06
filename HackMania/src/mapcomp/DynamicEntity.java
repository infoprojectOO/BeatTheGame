package mapcomp;

public class DynamicEntity extends Entity {
	private static final long serialVersionUID = 2220631494519354626L;
	private EntityType type;
//	private int mass;

	public DynamicEntity(float brake, float rebounce, DynamicEntity.EntityType entitytype) {
		super(brake, rebounce);
		this.type = entitytype;
	}
	
	public static enum EntityType {
		LIGHTWEIGHT(7,15,20),
		HEAVYWEIGHT(6,10,8);
		
		public final float impulse;
		public final float spdinc;
		public final float spddec;

		private EntityType(float impulse, float spdinc, float spddec) {
			this.impulse = impulse;
			this.spdinc = spdinc;
			this.spddec = spddec;
		}
		
	}
	
	public float getImpulse() {
		return type.impulse;
	}
	
	public float getIncrease() {
		return type.spdinc;
	}
	
	public float getDecrease() {
		return type.spddec;
	}

}
