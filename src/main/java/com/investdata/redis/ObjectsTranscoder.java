package com.investdata.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.investdata.dao.po.User;

public class ObjectsTranscoder {
	 public static <T> byte[] serialize(List<T> value) {  
         if (value == null) {  
             throw new NullPointerException("Can't serialize null");  
         }  
         byte[] rv=null;  
         ByteArrayOutputStream bos = null;  
         ObjectOutputStream os = null;  
         try {  
             bos = new ByteArrayOutputStream();  
             os = new ObjectOutputStream(bos);  
             for(T obj : value){  
                 os.writeObject(obj);
             }  
             os.writeObject(null);  
             os.close();  
             bos.close();  
             rv = bos.toByteArray();  
         } catch (IOException e) {  
             throw new IllegalArgumentException("Non-serializable object", e);  
         } finally {  
             close(os);  
             close(bos);  
         }  
         return rv;  
     }  

     public static <T> List<T> deserialize(byte[] in) {  
         List<T> list = new ArrayList<T>();  
         ByteArrayInputStream bis = null;  
         ObjectInputStream is = null;  
         try {  
             if(in != null) {  
                 bis=new ByteArrayInputStream(in);  
                 is=new ObjectInputStream(bis);  
                 while (true) {  
                     T obj =  (T)is.readObject();  
                     if(obj == null){  
                         break;  
                     }else{  
                         list.add(obj);  
                     }  
                 }  
                 is.close();  
                 bis.close();  
             }  
         } catch (IOException e) {  
//             logger.warn("Caught IOException decoding %d bytes of data", in == null ? 0 : in.length, e);  
         } catch (ClassNotFoundException e) {  
//             logger.warn("Caught CNFE decoding %d bytes of data",  
//                     in == null ? 0 : in.length, e);  
         } finally {  
//             CloseUtil.close(is);  
//             CloseUtil.close(bis);  
         }  
         return list;  
     }  
     
     private static void close(Closeable closeable) {
 		if (closeable != null) {
 			try {
 				closeable.close();
 			} catch (Exception e) {
 				// logger.info("Unable to close %s", closeable, e);
 			}
 		}
 	}
}
