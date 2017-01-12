package pro.cherkassy.rboyko;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by rboyko on 11.01.17.
 */
public class Main {
    public static void main(String[] args){
        String fileNameUrl=args[0];
        String fileNameStore=args[1];

        DataProvaider urlProvaider=new DataProvaider(fileNameUrl);
        Storage storage=new Storage(fileNameStore);

        ThreadManager threadManager=new ThreadManager(urlProvaider,storage,3);
        System.out.println("Start process ...");
        threadManager.start();
        storage.destroy();
        System.out.println("Done !!!");
    }

    static  class Utils{
        public static String  md5(String str){
            MessageDigest messageDigest=null;
            try {
                messageDigest=MessageDigest.getInstance("MD5");
                messageDigest.update(str.getBytes(),0,str.length());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return new BigInteger(1,messageDigest.digest()).toString(16);
        }
    }
}
