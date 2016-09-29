package arch.sm213.machine.student;

import machine.AbstractMainMemory;


/**
 * Main Memory of Simple CPU.
 *
 * Provides an abstraction of main memory (DRAM).
 */

public class MainMemory extends AbstractMainMemory {
  private byte [] mem;
  
  /**
   * Allocate memory.
   * @param byteCapacity size of memory in bytes.
   */
  public MainMemory (int byteCapacity) {
    mem = new byte [byteCapacity];
  }
  
  /**
   * Determine whether an address is aligned to specified length.
   * @param address memory address.
   * @param length byte length.
   * @return true iff address is aligned to length.
   */
  @Override protected boolean isAccessAligned (int address, int length) {
    return address % length == 0;
  }
  
  /**
   * Convert an sequence of four bytes into a Big Endian integer.
   * @param byteAtAddrPlus0 value of byte with lowest memory address (base address).
   * @param byteAtAddrPlus1 value of byte at base address plus 1.
   * @param byteAtAddrPlus2 value of byte at base address plus 2.
   * @param byteAtAddrPlus3 value of byte at base address plus 3 (highest memory address).
   * @return Big Endian integer formed by these four bytes.
   */
  @Override public int bytesToInteger (byte byteAtAddrPlus0, byte byteAtAddrPlus1, byte byteAtAddrPlus2, byte byteAtAddrPlus3) {
    int sum = 0;
    final int MASK = 0;
    // TODO make byte mask constant
    sum += MASK | ((0xFF & byteAtAddrPlus0) << 3*8);
    sum += MASK | ((0xFF & byteAtAddrPlus1) << 2*8);
    sum += MASK | ((0xFF & byteAtAddrPlus2) << 1*8);
    sum += MASK | (0xFF & byteAtAddrPlus3);
    return sum;
  }
  
  /**
   * Convert a Big Endian integer into an array of 4 bytes organized by memory address.
   * @param  i an Big Endian integer.
   * @return an array of byte where [0] is value of low-address byte of the number etc.
   */
  @Override public byte[] integerToBytes (int i) {
	final int BYTE_MASK = 0xFF;
	byte[] bytes = new byte[4];
	for (int c = 0; c < bytes.length; c++) {
		bytes[3 - c] = (byte) (BYTE_MASK & (i >> c*8));
	}
    return bytes;
  }
  
  /**
   * Fetch a sequence of bytes from memory.
   * @param address address of the first byte to fetch.
   * @param length  number of bytes to fetch.
   * @throws InvalidAddressException  if any address in the range address to address+length-1 is invalid.
   * @return an array of byte where [0] is memory value at address, [1] is memory value at address+1 etc.
   */
  @Override protected byte[] get (int address, int length) throws InvalidAddressException {
    byte[] bytes = new byte[length];
    if (address + length > this.length()) {
    	throw new InvalidAddressException();
    } else {
    	for (int offset = 0; offset < length; offset++) {
        	bytes[offset] = this.mem[address + offset];
        }
    }  
    return bytes;
  }
  
  /**
   * Store a sequence of bytes into memory.
   * @param  address                  address of the first byte in memory to recieve the specified value.
   * @param  value                    an array of byte values to store in memory at the specified address.
   * @throws InvalidAddressException  if any address in the range address to address+value.length-1 is invalid.
   */
  @Override protected void set (int address, byte[] value) throws InvalidAddressException {
    int len = value.length;
    if (address < 0 || address + len - 1 > this.length()) {
    	throw new InvalidAddressException();
    } else {
    	for (int i = 0; i < len; i++) {
    		mem[address + i] = value[i];
    	}
    }
  }
  
  /**
   * Determine the size of memory.
   * @return the number of bytes allocated to this memory.
   */
  @Override public int length () {
    return mem.length;
  }
}
