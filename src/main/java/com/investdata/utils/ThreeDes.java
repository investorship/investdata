package com.investdata.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*字符串 DESede(3DES) 加密
 * ECB模式/使用PKCS7方式填充不足位,目前给的密钥是192位
 * 3DES（即Triple DES）是DES向AES过渡的加密算法（1999年，NIST将3-DES指定为过渡的
 * 加密标准），是DES的一个更安全的变形。它以DES为基本模块，通过组合分组方法设计出分组加
 * 密算法，其具体实现如下：设Ek()和Dk()代表DES算法的加密和解密过程，K代表DES算法使用的
 * 密钥，P代表明文，C代表密表，这样，
 * 3DES加密过程为：C=Ek3(Dk2(Ek1(P)))
 * 3DES解密过程为：P=Dk1((EK2(Dk3(C)))
 * */
public class ThreeDes {

	public static final byte[] Key = "A^f~1'w&".getBytes();
    private static final String Algorithm = "DES";  //定义 加密算法,可用 DES,DESede,Blowfish
 
    // 加密字符串
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try { // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
 
    // 解密字符串
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try { // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
 
    public static void main(String[] args) throws Exception { // 添加新安全算法,如果用JCE就要把它添加进去
//        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        /*final byte[] keyBytes = Key;    //8字节的密钥
        String szSrc = "isdjfisjf8192=1";
        System.out.println("加密前的字符串:" + szSrc);
        byte[] encoded = encryptMode(keyBytes, szSrc.getBytes());
        System.out.println("加密后的字符串:" + new String(encoded));
        System.out.println("加密后的字符串:" + Coder.encryptBASE64(encoded));
        
        System.err.println("muti 8=" + Coder.encryptBASE64((encoded)).length());
        System.err.println("------------- " + new String(decryptMode(keyBytes, Coder.decryptBASE64(Coder.encryptBASE64(encoded)))));
        
        
        byte[] srcBytes = decryptMode(keyBytes, encoded);
        System.out.println("解密后的字符串:" + (new String(srcBytes)));
    	
    	System.err.println(URLDecoder.decode("l5M%2FPz9eq3lFEOKgE6%2BW8oxvpdj4nanlKah9RFwVP77NOS9aej9emNi0xsPyQ4RP2PEvCHi%2FbJpPLSs%3D","UTF-8"));*/
    	
    	String str = "8123sfs你说的服2";
    	System.err.println(new String(Coder.getMD5Code(str)).length());
    	
    }
}
