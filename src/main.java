/**
 * Created by ravibhankharia on 11/27/16.
 */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

public class main {
        public static void main(String[] args) throws Exception {
            int i = 0x0;
            for (; i < 0xF; i++){
                System.out.println(i);
                System.out.print(decrypt("000" + Integer.toHexString(i)));
            }
            i++;
            for (; i < 0xFF; i++){
                System.out.println(i);
                System.out.print(decrypt("00" + Integer.toHexString(i)));
            }
            i++;
            for (; i < 0xFFF; i++){
                System.out.println(i);
                System.out.print(decrypt("0" + Integer.toHexString(i)));
            }
            i++;
            for (; i < 0xFFFF; i++){
                System.out.println(i);
                System.out.print(decrypt(Integer.toHexString(i)));
            }



        }

        public static String decrypt(String key) throws Exception{
            String keyHex = "639404CBD1A1BD2322B206C39140" + key;
            final String ciphertextHex = "5A052F928464CC3E437187ADCFC7E8F1CF9DEAC7059B5264E4E940D8C35AA60E2277D4832843043F593F40E4084609C886681BCF5B570D353BFF24C0E1F4A65E";

            SecretKey skey = new SecretKeySpec(DatatypeConverter
                    .parseHexBinary(keyHex), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skey);

            byte[] result = cipher.doFinal(DatatypeConverter.parseHexBinary(ciphertextHex));

            if (!isValid(result)){
                return "";
            }
            return new String(result) + "\n";


        }

        public static boolean isValid(byte[] arr){
            int threshold = 0;
            for (int j = 0; j < arr.length; j ++){
                if (arr[j] > 0x7E && arr[j] < 0x20){
                    threshold++;
                }
            }
            if (threshold > 1){
                return false;
            }else{
                return true;
            }
        }
}