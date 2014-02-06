package modules;

import hack.model.HackMania;
import items.Bullet;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import utilities.ImageFetcher;

import mapcomp.Block;
import mapcomp.IMapComponent;

import active.Gamer;
import active.Movable;
import active.Opponent;

public class GraphicImage {
	private static Toolkit kit = Toolkit.getDefaultToolkit();
	
	public static Image getImage(String name) {
		BufferedImage img = null;
		try {
			Path path = Paths.get(System.getProperty("user.dir"));
			path = path.getParent().resolve("HackMania/assets/"+name);
			File file = new File(path.toUri());
			img = ImageIO.read(path.toFile());
		} catch (IOException e) {System.err.println("No image found named " + name);}
		return img;	
	}
//
//	public static Image[] getTextures(String name) {
//		Image right = new TextureRegion(new Texture(Gdx.files.internal(name)));
//		Image left = new TextureRegion(right);
//		left.flip(true, false);
//		return new TextureRegion[] {left,right};
//	}
	
	
	public static Image[] getTextures(String pack, String name) {
		Image right = getImage(pack+"/"+name+".png");
//		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
//		tx.translate(-right.getWidth(null), 0);
//		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//		Image left = op.filter(right, null);
		return new Image[] {right,right};		
	}


	public static Image[] getTextures(String name) {
		BufferedImage img = null;
		try {
			Path path = Paths.get(System.getProperty("user.dir"));
			path = path.getParent().resolve("HackMania/assets/"+name);
			File file = new File(path.toUri());
			img = ImageIO.read(path.toFile());
		} catch (IOException e) {System.err.println("No image found named " + name);}
//		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
//		tx.translate(-right.getWidth(null), 0);
//		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//		Image left = op.filter(right, null);
		return new Image[] {img,img};	
	}
	
	public static Image getIconImage(String name, Path path) {
		Image icon = kit.getImage(path.resolve(name).toString());
		return icon;		
	}

}
