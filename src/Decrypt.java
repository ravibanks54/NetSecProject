/*
 * Created by ravibhankharia on 11/27/16.
 * Network Security Computer Project
 */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Decrypt {
        public static void main(String[] args) throws Exception {
            final long startTime = System.currentTimeMillis();
            final String IV;
            final String ciphertext;
            int probNumber = Integer.parseInt(args[0]);
            if (probNumber == 1){
                IV = "639404CBD1A1BD2322B206C39140";
                ciphertext = "5A052F928464CC3E437187ADCFC7E8F1CF9DEAC7059B5264E4E940D8C35AA60E2277D4832843043F593F40E4084609C886681BCF5B570D353BFF24C0E1F4A65E";
            }else if(probNumber == 2){
                IV = "F806274AC0B446C18725ABDCE5";
                ciphertext = "9D736AD64EFE153E6BEDE689772976ED83FB89D0503B27E7B4E2C4CDBE7B3BD9C1CE5E800D3929E543C3AD1B0D862990D7BCF77B74A126E27F5901EEFC5044BA";
            }else if(probNumber == 3){
                IV = "0AA4A910D451E069611D5571";
                ciphertext = "574DD238070EC66A027F120B3D67A4B1FF20D1AAD52893CD2970E76BE73A2C4AE8AE87D1DC4CD4E6CE3733A27D401339E1E2A3FA9A0E86829284CACD5A850BCD";
            }else{
                IV = "9D0B180B5CD9DC074ACB0E";
                ciphertext = "7102108459F8B9726887034491C1B409C29BF90CD1895B80815ABF2434DD57327CDFF16B9CF0C90C5F39CC92FC6EF99CDDE1D0FA90236F9474DF142B6BF1B64B";
            }

            int keyLen = 32 - IV.length();  //Holds length of key after IV
            StringBuilder maxKey = new StringBuilder();

            for (int j=0; j < keyLen; j++) { //Generate max hex value for for loop
                maxKey.append(Integer.toHexString(0xF));
            }
            long maxKeyLong = Long.parseLong(maxKey.toString(), 16);
            int numDivisions = 16;
            long keySpace = maxKeyLong/numDivisions;
            for (int k = 0; k < numDivisions; k++){
                new DecryptThread(k*keySpace, (k+1)*keySpace, IV, ciphertext).start();
            }
            /*
            int i = 0x0;
            String hexString;   //holds hex version of i in a string
            String output;
            StringBuilder padding = new StringBuilder();

            long maxKeyLong = Long.parseLong(maxKey.toString(), 16);
            for (; i < maxKeyLong; i++){
                if (i%1000000 == 0){
                    System.out.println(i);
                }
                hexString = Integer.toHexString(i);     //converts integer to hex string representation
                for(int k = 0; k < keyLen-hexString.length(); k++){ //Generate padding as a StringBuilder
                    padding.append("0");
                }
                output = decrypt(IV + padding.toString() + hexString, ciphertext);
                if (output != null){
                    System.out.print(output);
                }
                padding = new StringBuilder();  //clear padding
            }
            */
            final long endTime = System.currentTimeMillis();
            System.out.println("Total execution time: " + (endTime - startTime) );

        }

        public static String decrypt(String keyHex, String ciphertextHex) throws Exception{
            SecretKey skey = new SecretKeySpec(DatatypeConverter
                    .parseHexBinary(keyHex), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skey);

            byte[] result = cipher.doFinal(DatatypeConverter.parseHexBinary(ciphertextHex));

            Boolean res = isValid(result);
            if (!res){
                return null;
            }
            return new String(result, "UTF-8") + "\n";


        }

        private static boolean isValid(byte[] arr){
            int threshold = 0;
            for (int j = 0; j < arr.length; j++){
                if (arr[j] == 0x0){
                    arr[j] = 0x20;  //Ignore the padding at the end (convert to spaces
                }
                if (arr[j] > 0x7E || arr[j] < 0x20){    //Check if valid ASCII value
                    if (threshold>3){
                        return false;
                    }else{
                        threshold++;
                    }
                }
            }

            return true;
        }
}
