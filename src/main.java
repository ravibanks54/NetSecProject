/**
 * Created by ravibhankharia on 11/27/16.
 */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class main {
        public static void main(String[] args) throws Exception {
            final String keyHex = "6F1C5CD9270AC8DDEAE6430F3096C806";
            final String ciphertextHex = "1137590E7602256E37FCD36855CC9353C1F2C21171F2EC0391BEEE9A0A19B084";

            SecretKey key = new SecretKeySpec(DatatypeConverter
                    .parseHexBinary(keyHex), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] result = cipher.doFinal(DatatypeConverter.parseHexBinary(ciphertextHex));

            String out = new String(result);
            System.out.println(out);

            System.out.println(DatatypeConverter.printHexBinary(result));
        }
}
