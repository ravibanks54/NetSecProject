/**
 * Created by ravibhankharia on 11/30/16.
 * Decrypt Thread
 */
public class DecryptThread extends Thread{

    long start;
    long end;
    String IV;
    String ciphertext;

    public DecryptThread(long start, long end, String IV, String ciphertext){
        this.start = start;
        this.end = end;
        this.IV = IV;
        this.ciphertext = ciphertext;
    }
    @Override
    public void run(){
        String hexString;   //holds hex version of i in a string
        String output;
        StringBuilder padding = new StringBuilder();

        int keyLen = 32 - IV.length();

        for (long i = start; i < end; i++) {
            if (i % 1000000 == 0) {
                System.out.println(i);
            }
            hexString = Long.toHexString(i);     //converts integer to hex string representation
            for (int k = 0; k < keyLen - hexString.length(); k++) { //Generate padding as a StringBuilder
                padding.append("0");
            }
            try {
                output = Decrypt.decrypt(IV + padding.toString() + hexString, ciphertext);
            } catch (Exception e) {
                output = "";
            }
            if (output != null){
                System.out.print(output);
            }
            padding.setLength(0);  //clear padding
        }
    }
}
