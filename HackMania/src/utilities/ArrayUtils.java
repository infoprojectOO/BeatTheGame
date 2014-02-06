package utilities;

public class ArrayUtils {
	
	public static float[] addAll(float[] fs1, float[] fs2) {
		float[] floats = new float[fs1.length+fs2.length];
		int i = 0;
		for(float f : fs1) {
			floats[i]=f;
			i++;
		} for(float f : fs2) {
			floats[i]=f;
			i++;
		} return floats;
	}

}
