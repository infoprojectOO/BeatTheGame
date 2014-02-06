package mainmodel;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class SetupImage {
	
	private static void packFolder(String packname, String name) {
		Settings settings = new Settings();
		settings.pot = false;
		settings.paddingX = 0;
		settings.paddingY = 0;
		settings.minHeight = 256;
		settings.maxHeight = 1024;
		settings.minWidth = 256;
		settings.maxWidth = 1024;
		TexturePacker2.process(settings,"/Users/AnthonyS/Documents/University/Informatique/Projets/Projet Info 4 (abyss games)/HackMania/assets/"+packname, "/Users/AnthonyS/Documents/University/Informatique/Projets/Projet Info 4 (abyss games)/HackMania/assets/"+packname+"/textures/", name+".pack");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		packFolder("gadgets","gadgets");
		
		
	}

}
