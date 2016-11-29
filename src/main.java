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
            final String IV = "639404CBD1A1BD2322B206C39140";
            final String ciphertext = "5A052F928464CC3E437187ADCFC7E8F1CF9DEAC7059B5264E4E940D8C35AA60E2277D4832843043F593F40E4084609C886681BCF5B570D353BFF24C0E1F4A65E";
            int keyLen = 32 - IV.length();  //Holds length of key after IV
            StringBuilder maxKey = new StringBuilder();
            StringBuilder padding = new StringBuilder();
            for (int j=0; j < keyLen; j++){ //Generate max hex value for for loop
                maxKey.append(Integer.toHexString(0xF));
            }
            int i = 0x0;
            String hexString;   //holds hex version of i in a string

            for (; i < Integer.parseInt(maxKey.toString(), 16); i++){
                hexString = Integer.toHexString(i);     //converts integer to hex string representation
                for(int k = 0; k < keyLen-hexString.length(); k++){ //Generate padding as a StringBuilder
                    padding.append("0");
                }
                System.out.print(decrypt(IV + padding.toString() + hexString, ciphertext));
                padding = new StringBuilder();  //clear padding
            }
            /*
            for (; i < 0xF; i++){
                //System.out.println(i);
                System.out.print(decrypt(IV + "000" + Integer.toHexString(i), ciphertext));
            }
            i++;
            for (; i < 0xFF; i++){
                //System.out.println(i);
                System.out.print(decrypt(IV + "00" + Integer.toHexString(i), ciphertext));
            }
            i++;
            for (; i < 0xFFF; i++){
                //System.out.println(i);
                System.out.print(decrypt(IV + "0" + Integer.toHexString(i), ciphertext));
            }
            i++;
            for (; i < 0xFFFF; i++){
                //System.out.println(i);
                System.out.print(decrypt(IV + Integer.toHexString(i), ciphertext));
            }*/
        }

        public static String decrypt(String keyHex, String ciphertextHex) throws Exception{
            SecretKey skey = new SecretKeySpec(DatatypeConverter
                    .parseHexBinary(keyHex), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skey);

            byte[] result = cipher.doFinal(DatatypeConverter.parseHexBinary(ciphertextHex));

            Boolean res = isValid(result);
            if (!res){
                return "";
            }
            return new String(result, "UTF-8") + "\n";


        }

        public static boolean isValid(byte[] arr){
            int threshold = 0;
            for (int j = 0; j < arr.length; j ++){
                if (arr[j] > 0x7E || arr[j] < 0x20){
                    threshold++;
                }
            }
            if (threshold > 20){
                return false;
            }else{
                return true;
            }
        }
}
