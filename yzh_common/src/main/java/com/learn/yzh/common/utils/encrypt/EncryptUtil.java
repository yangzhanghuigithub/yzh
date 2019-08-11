package com.learn.yzh.common.utils.encrypt;


import com.learn.yzh.common.utils.StringUtils;

import java.util.regex.Pattern;


public class EncryptUtil {
	private static String[] A5 = {"A","B","C","D","E","F","G","H","I","J"};
	private static String[] A4 = {"K","L","M","N","O","P","Q","R","S","T"};
	private static String[] A0 = {"U","V","W","X","Y","Z","A","B","C","D"};
	private static String[] A3 = {"E","F","G","H","I","J","K","L","M","N"};
	private static String[] A1 = {"O","P","Q","R","S","T","U","V","W","X"};
	private static String[] A2 = {"Y","Z","A","B","C","D","E","F","G","H"};
	private static String[] A9 = {"I","J","K","L","M","N","O","P","Q","R"};
	private static String[] A6 = {"S","T","U","V","W","X","Y","Z","A","B"};
	private static String[] A7 = {"C","D","E","F","G","H","I","J","K","L"};
	private static String[] A8 = {"M","N","O","P","Q","R","S","T","U","V"};
	
	
	public static String encryp(String enterpriseId, String number) {
		if (number == null || (number.length() < 12 || number.length() > 16) || !StringUtils.isNumber(number)) {
			return number;
		}
		String e = enterpriseId ;
		int seed = Integer.parseInt(e.substring(e.length() - 1, e.length()));
		int sessionId = Integer.parseInt(e.substring(0, 4));
		String begin = number.substring(0, number.length() - 8);
		String middle = number.substring(number.length() - 8, number.length() - 4);
		String tail = number.substring(number.length() - 4, number.length());
		
		String sec = encryp(seed, sessionId, middle, tail);
		return begin + sec + tail;
	}
	
	public static String decryp(String enterpriseId, String number) {
		if (number == null || (number.length() < 12 || number.length() > 16) || StringUtils.isNumber(number)) {
			return number;
		}
		
		String begin = number.substring(0, number.length() - 8);
		String sec = number.substring(number.length() - 8, number.length() - 4);
		String tail = number.substring(number.length() - 4, number.length());
		if (!StringUtils.isNumber(begin) || !StringUtils.isNumber(tail) || !Pattern.matches("^[A-Z]{4}$", sec)) {
			return number;
		}
		String e = enterpriseId + "";
		int seed = Integer.parseInt(e.substring(e.length() - 1, e.length()));
		int sessionId = Integer.parseInt(e.substring(0, 4));
		
		String middle = decryp(seed, sessionId, sec, tail);
		
		return begin + middle + tail;
	}
	
	public static String encryp(int seed, int sessionId, String number, String tailNumber){
	    String[] encrypArray = getEncryptArray(Integer.parseInt(tailNumber.substring(0,1)));
		int numberLength = number.length();
		int src;
		try{
			src = Integer.parseInt(number);
		}catch(Exception e){
			return "error";
		}
		byte a[] = new byte[numberLength];
		for(int i=0;i<numberLength;i++){
			a[i]=(byte)(number.charAt(i)-'0');
		}
		
		byte b[] = new byte[numberLength];
	    for(int i=0;i<numberLength;i++){
	    	if(a[i] > seed){
	    		 b[i]=(byte) (seed+10-a[i]);
	    	}else{
	    		b[i]=(byte) (seed-a[i]);
	    	}
	    }
	    byte t=b[0];
	    for(int i=0;i<numberLength;i++)
	    {
	    	if(i==numberLength-1){
	    		b[i]=t;
	    	}else{
	    		b[i]=b[i+1];
	    	}
	    }
		int tail;
		try{
			tail = Integer.parseInt(tailNumber);
		}catch(Exception e){
			return "error";
		}
	    tail += sessionId;
	    int p = (int) Math.pow(10, tailNumber.length());
	    if(tail > p){
	    	tail -= p;
	    }
		
		int tailNumberLength = tailNumber.length();
		tailNumber = String.valueOf(tail);
		for(int i=String.valueOf(tail).length(); i<tailNumberLength ;i++){
			tailNumber = "0" + tailNumber;
		}
		byte ta[] = new byte[tailNumberLength];
		for(int i=0;i<tailNumberLength;i++){
			ta[i]=(byte)(tailNumber.charAt(i)-'0');
		}
	    for(int i=0;i<tailNumberLength;i++){
	    	b[i] = (byte) ((b[i] + ta[i])%10);
	    }
	    return "" + encrypArray[b[0]] + encrypArray[b[1]] + encrypArray[b[2]] +encrypArray[b[3]];
	}
	
	public static String decryp(int seed, int sessionId, String fake, String tailNumber){
		String number = "";
		String[] encrypArray = getEncryptArray(Integer.parseInt(tailNumber.substring(0,1)));
		char[] charArray = fake.toCharArray();
		for (int x = 0; x < charArray.length; x++) {
			for (int y = 0; y < encrypArray.length; y++) {
				if (encrypArray[y].equals(charArray[x] + "")) {
					number += y + "";
					break;
				}
			}
		}
		int numberLength = number.length();
		int src;
		try{
			src = Integer.parseInt(number);
		}catch(Exception e){
			return "error";
		}
		byte a[] = new byte[numberLength];
		for(int i=0;i<numberLength;i++){
			a[i]=(byte)(number.charAt(i)-'0');
		}
		int tail;
		try{
			tail = Integer.parseInt(tailNumber);
		}catch(Exception e){
			return "error";
		}
	    tail += sessionId;
	    int p = (int) Math.pow(10, tailNumber.length());
	    if(tail > p){
	    	tail -= p;
	    }
		
		int tailNumberLength = tailNumber.length();
		tailNumber = String.valueOf(tail);
		for(int i=String.valueOf(tail).length(); i<tailNumberLength ;i++){
			tailNumber = "0" + tailNumber;
		}
		byte ta[] = new byte[tailNumberLength];
		for(int i=0;i<numberLength;i++){
			ta[i]=(byte)(tailNumber.charAt(i)-'0');
		}
	    for(int i=numberLength-1;i>=0;i--)
	    {
	    	a[i]=(byte) ((a[i]+10-ta[i])%10);
	    }
	    byte t=a[numberLength-1];
	    for(int i=numberLength-1;i>=0;i--)
	    {
	    	if(i==0){
	    		a[i]=t;
	    	}else{
	    		a[i]=a[i-1];
	    	}
	    }
		byte b[] = new byte[numberLength];
	    for(int i=0;i<numberLength;i++){
	    	if(a[i] <= seed){
	    		b[i]=(byte) (seed-a[i]);
	    	}else{
	    		b[i]=(byte) (seed+10-a[i]);
	    	}
	    }	
	    
	    //System.out.println("解密后:" + b[0] + b[1] + b[2] +b[3]);
	    return "" + b[0] + b[1] + b[2] +b[3];
	}
	 
	private static String[] getEncryptArray(int n) {
		switch(n) {
		case 0: return A0;
		case 1: return A1;
		case 2: return A2;
		case 3: return A3;
		case 4: return A4;
		case 5: return A5;
		case 6: return A6;
		case 7: return A7;
		case 8: return A8;
		case 9: return A9;
		}
		return null;
	}
	
	public static void main(String[] argv) {
		String s = "717200000067";
		System.out.println(s);
		s = encryp("0107", s);
		System.out.println(s);
		s = decryp("0107", s);
		System.out.println(s);
		
	}
}
