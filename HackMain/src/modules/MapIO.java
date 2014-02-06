package modules;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;

import substrate.Filmtub;

public class MapIO {
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	
	public static Filmtub open(File file) {
		Filmtub map = null;
		try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			map = (Filmtub) in.readObject();
			System.out.println("hello");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static void save(Filmtub map, File file) {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		} catch (IOException e) {
			e.printStackTrace();
		} try {
			System.out.println("hello");
			out.writeObject(map);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public static String getDefaultPath() {
		return System.getProperty("user.dir");
	}
	
	
	

}
