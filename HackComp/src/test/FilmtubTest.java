package test;

import static org.junit.Assert.*;

import mapcomp.Block;
import mapcomp.IMapComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import substrate.Filmtub;

public class FilmtubTest {
	private Filmtub test;
	private Array<IMapComponent> blocks = new Array<IMapComponent>();

	@Before
	public void setUp() throws Exception {
		this.test = new Filmtub(10,10);
		blocks.add(new Block(new Vector2(4f,4f)));
		blocks.add(new Block(new Vector2(5f,4f)));
		blocks.add(new Block(new Vector2(5f,5f)));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFilmtub() {
		fail("Not yet implemented");
	}

	@Test
	public void testWithin() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRoots() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEmpty() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testPut() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemove() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetObjects() {
		test.put(blocks.get(0), 4, 4);
		test.put(blocks.get(1), 5, 4);
		test.put(blocks.get(2), 5, 5);
		IMapComponent caller = new Block(new Vector2(5.5f,0.5f));
		Array<IMapComponent> objects = test.getObjects(caller.getBox(), caller);
		assertTrue(objects.size==3);		
	}

}
