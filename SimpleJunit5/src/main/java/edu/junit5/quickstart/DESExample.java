package edu.junit5.quickstart;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;


/**
 * a simple example of DES in ECB mode.
 * es wird ein KeyBytes für die Keygrösse erstellt
 * es wird ein SecretKey (inthält die Keygrösse und ein Algorithm) erstellt
 * es wird ein Chipger esstellt (inthält Algorithm/Mode/Padding und Provider) erstellt
 */
public class DESExample {

    public static void main(String[] args)
            throws Exception {

        //add Provider BC
        Security.addProvider(new BouncyCastleProvider());

        //Keygrösse 8 Byte
        byte[][] keyBytes = {Hex.decode("0101010101010101" ) , Hex.decode("1f1f1f1f0e0e0e0e" ), Hex.decode("E0E0E0E0F1F1F1F1" )};



        for(int i=0; i<keyBytes.length; i++) {
            System.out.println("---------------------" + (i +1) + "---------------------");
            System.out.println("Key :" + Hex.toHexString(keyBytes[i]));



            SecretKeySpec key = new SecretKeySpec(keyBytes[2], "DES");

            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding", "BC");
            //......DES geht nicht bei 256 bits SChlüssellänge, da die Schlüsselgrösse  grösser als 8 bytes ist....

            byte[] input = Hex.decode("a0a1a2a3a4a5a6a7a0a1a2a3a4a5a6a7");

            //das Input auf die Console ausgeben
            System.out.println("input    : " + Hex.toHexString(input));

            //das cipher encryptin
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] output = cipher.doFinal(input);

            //das encrypted-input auf die Console ausgeben
            System.out.println("encrypted: " + Hex.toHexString(output));

            //nochmal verschlüsseln
            //wir bekommen das gleiche ergebnes wie das input ---d.h Schwachstelle
            output = cipher.doFinal(output);
            System.out.println("doubleencrypted: " + Hex.toHexString(output));

            //das cipher decryptin
            cipher.init(Cipher.DECRYPT_MODE, key);

            //das decrypted-Wort auf die Console ausgeben
            System.out.println("decrypted: "
                    + Hex.toHexString(cipher.doFinal(output)));

            System.out.println("-------------------------------------------");
            System.out.println();

        }
    }
}
