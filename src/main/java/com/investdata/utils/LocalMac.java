package com.investdata.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class LocalMac {
	
	public static String getLocalMac() throws Exception {
		InetAddress ia = InetAddress.getLocalHost();
		//��ȡ��������ȡ��ַ
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
//		System.out.println("mac���鳤�ȣ�"+mac.length);
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			//�ֽ�ת��Ϊ����
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
//			System.out.println("ÿ8λ:"+str);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		return sb.toString().toUpperCase();
	}
	
	/**
	 * @param args
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 */
	public static void main(String[] args) throws Exception {
		System.err.println(getLocalMac());
	}
}
