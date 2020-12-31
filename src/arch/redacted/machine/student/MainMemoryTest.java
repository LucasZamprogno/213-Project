package arch.redacted.machine.student;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.*;

public class MainMemoryTest {

	@Test
	public void testIsAcccessAligned() {
		MainMemory mem = new MainMemory(200);
		assertTrue("address 0 should always be aligned", mem.isAccessAligned(0, 4));
		assertTrue("address 0 should always be aligned", mem.isAccessAligned(0, 8));
		assertTrue(mem.isAccessAligned(32, 4)); // Basic case
		assertTrue(mem.isAccessAligned(400, 4)); // Though not possible, should be true
		assertTrue(mem.isAccessAligned(168, 8)); // Slightly more "complicated"
		assertFalse(mem.isAccessAligned(32, 6)); // Standard fail
		assertFalse(mem.isAccessAligned(33, 4)); // Standard fail
	}
	
	@Test
	public void testBytesToInteger0() {
		MainMemory mem = new MainMemory(200);
		int expected = 0;
		// Basic 0 check
		assertEquals(expected, mem.bytesToInteger((byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00));
	}
	
	@Test
	public void testBytesToInteger1() {
		MainMemory mem = new MainMemory(200);
		int expected = 1;
		// Basic positive check
		assertEquals(expected, mem.bytesToInteger((byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01));
	}
	
	@Test
	public void testBytesToInteger2() {
		MainMemory mem = new MainMemory(200);
		int expected = -1;
		// Basic negative check
		assertEquals(expected, mem.bytesToInteger((byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF));
	}
	
	@Test
	public void testBytesToInteger3() {
		MainMemory mem = new MainMemory(200);
		int expected = 2000000000; // 2,000,000,000
		// Positive number, all bits up to sign bit significant
		assertEquals(expected, mem.bytesToInteger((byte)0x77, (byte)0x35, (byte)0x94, (byte)0x00));
	}
	
	@Test
	public void testBytesToInteger4() {
		MainMemory mem = new MainMemory(200);
		int expected = -2000000000; // -2,000,000,000
		// Negative number, all bits up to sign bit significant
		assertEquals(expected, mem.bytesToInteger((byte)0x88, (byte)0xCA, (byte)0x6C, (byte)0x00));
	}
	
	@Test
	public void testIntegerToBytes0() {
		MainMemory mem = new MainMemory(200);
		byte[] expected = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
		// Basic 0 check
		assertTrue(Arrays.equals(expected, mem.integerToBytes(0)));
		//assertEquals(expected, mem.integerToBytes(0));
	}
	
	@Test
	public void testIntegerToBytes1() {
		MainMemory mem = new MainMemory(200);
		byte[] expected = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01};
		// Basic positive check
		assertTrue(Arrays.equals(expected, mem.integerToBytes(1)));
	}
	
	@Test
	public void testIntegerToBytes2() {
		MainMemory mem = new MainMemory(200);
		byte[] expected = {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF};
		// Basic negative check
		assertTrue(Arrays.equals(expected, mem.integerToBytes(-1)));
	}
	
	@Test
	public void testIntegerToBytes3() {
		MainMemory mem = new MainMemory(200);
		byte[] expected = {(byte)0x77, (byte)0x35, (byte)0x94, (byte)0x00};
		// Larger int check (2,000,000,000)
		assertTrue(Arrays.equals(expected, mem.integerToBytes(2000000000)));
	}
	
	@Test
	public void testIntegerToBytes4() {
		MainMemory mem = new MainMemory(200);
		byte[] expected = {(byte)0x88, (byte)0xCA, (byte)0x6C, (byte)0x00};
		// Larger negative check (-2,000,000,000)
		assertTrue(Arrays.equals(expected, mem.integerToBytes(-2000000000)));
	}
	
	@Test
	public void testSetGet0() {
		// Testing up against lowest index
		MainMemory mem = new MainMemory(200);
		byte[] toSet = {(byte)0x77, (byte)0x35, (byte)0x94, (byte)0x00};
		final int ADDRESS = 0;
		try {
			mem.set(ADDRESS, toSet);
		} catch (Exception e) {
			fail("Exception thrown");
		}
		try {
			assertTrue(Arrays.equals(toSet, mem.get(ADDRESS, 4)));
		} catch (Exception e) {
			fail("Exception thrown");
		}
	}
	
	@Test
	public void testSetGet1() {
		// Testing up against highest index
		MainMemory mem = new MainMemory(200);
		byte[] toSet = {(byte)0x77, (byte)0x35, (byte)0x94, (byte)0x00};
		final int ADDRESS = 196;
		try {
			mem.set(ADDRESS, toSet);
		} catch (Exception e) {
			fail("Exception thrown");
		}
		try {
			assertTrue(Arrays.equals(toSet, mem.get(ADDRESS, 4)));
		} catch (Exception e) {
			fail("Exception thrown");
		}
	}
	
	@Test
	public void testSet0() {
		// Testing exception thrown from too low address on set
		MainMemory mem = new MainMemory(200);
		byte[] toSet = {(byte)0x77, (byte)0x35, (byte)0x94, (byte)0x00};
		final int ADDRESS = -1;
		try {
			mem.set(ADDRESS, toSet);
			fail("Set did not throw exception");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testSet1() {
		// Testing exception thrown from too high address on set
		MainMemory mem = new MainMemory(200);
		byte[] toSet = {(byte)0x77, (byte)0x35, (byte)0x94, (byte)0x00};
		final int ADDRESS = 197;
		try {
			mem.set(ADDRESS, toSet);
			fail("Set did not throw exception");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testGet0() {
		// Testing exception thrown from too low address on get
		MainMemory mem = new MainMemory(200);
		final int ADDRESS = -1;
		try {
			byte[] b = mem.get(ADDRESS, 4);
			fail("Get did not throw exception");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testGet1() {
		// Testing exception thrown from too high address on get
		MainMemory mem = new MainMemory(200);
		final int ADDRESS = 197;
		try {
			byte[] b = mem.get(ADDRESS, 4);
			fail("Get did not throw exception");
		} catch (Exception e) {
			
		}
	}
}
