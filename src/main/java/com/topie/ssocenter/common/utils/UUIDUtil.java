package com.topie.ssocenter.common.utils;

import java.util.UUID;

public class UUIDUtil {
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}
	public static String[] chars = { "a", "b", "c", "d", "e", "f", 
	    "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", 
	    "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", 
	    "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", 
	    "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", 
	    "W", "X", "Y", "Z" };

	  private static String getUUID(int length, UUID uuid)
	  {
	    int groupLength = 32 / length;
	    StringBuilder sb = new StringBuilder();
	    String id = uuid.toString().replace("-", "");
	    for (int i = 0; i < length; i++) {
	      String str = id.substring(i * groupLength, i * groupLength + 
	        groupLength);
	      int x = Integer.parseInt(str, 16);
	      sb.append(chars[(x % 62)]);
	    }
	    return sb.toString();
	  }

	  public static String getUUID8()
	  {
	    return getUUID(8, UUID.randomUUID());
	  }

	  public static String getUUID8(byte[] bytes)
	  {
	    return getUUID(8, UUID.nameUUIDFromBytes(bytes));
	  }

	  public static String getUUID8(String fromString)
	  {
	    return getUUID(8, UUID.fromString(fromString));
	  }

	  public static String getUUID16()
	  {
	    return getUUID(16, UUID.randomUUID());
	  }

	  public static String getUUID16(String fromString)
	  {
	    return getUUID(16, UUID.fromString(fromString));
	  }

	  public static String getUUID16(byte[] bytes)
	  {
	    return getUUID(16, UUID.nameUUIDFromBytes(bytes));
	  }

	  public static String getUUID32()
	  {
	    return UUID.randomUUID().toString().replace("-", "");
	  }
	public static void main(String[] args) {
		String uuid = UUIDUtil.getUUID();
		System.out.println(uuid);
	}
}
