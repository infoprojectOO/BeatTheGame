package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import standards.Axis;
import substrate.Contact;

public class ContactTest {
	private Contact contact;

	@Before
	public void setUp() throws Exception {
		this.contact = new Contact();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMerge() {
		this.contact.merge(new Contact(Axis.X,null));
		assertTrue(contact.onX());
		this.contact.merge(new Contact(null,Axis.Y));
		assertTrue(contact.onX());
		assertTrue(contact.onY());
	}

}
