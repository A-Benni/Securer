package edu.junit5.quickstart;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

public class ARC4 {

    private Cipher cipher;
    private  byte[] iv;
    private byte[] output;

    private final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    //von hier neu--------------------------------------------------
    static String encryptedText ="" ;
    String text = ReadFile.getTextFile();

    public ARC4() throws Exception {
    }

    public static String getEncryptText() throws Exception {
        return  encryptedText ;
    }



    /**
     * Konstruktor fuer die Klasse AESEncrypt
     * @param blockMode das blockMode der AES-Verschlüsslung
     * @param padding das Padding von Daten vor der Verschlüsselung
     */
   /* public ATR4(String blockMode, String padding) throws Exception {
        this.blockMode = blockMode;
        this.padding = padding;
    }*/

    /**
     * Diese Methode initialisiert das Cipher object für AES-Verschlüsslungsverfahren,und BC als Provider
     * dann liefert es zurueck.
     * @return cipher
     */
    public Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        cipher = Cipher.getInstance("ARC4/", "BC");
        return cipher;
    }


    /**
     * Diese Methode initialisiert das Key für AES-Verschlüsslungsverfahren,und BC als Provider
     * dann liefert es zurueck.
     * @return  key
     */
    public SecretKey getKey() throws NoSuchProviderException, NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator kGen = KeyGenerator.getInstance("ARC4", "BC");
        SecretKey key = kGen.generateKey();

        return key;
    }

    /**
     * Diese Methode Konvertieren ein String zu Hexadezimalzahl
     * @param arg String
     * @return String
     */
    public String toHex(String arg) {

        return String.format("%040x", new BigInteger(1, arg.getBytes(/*UTF-8*/)));
    }

    /**
     * Diese Methode Konvertieren ein byte-Array zu String in UTF8
     * @param bytes byte-Array
     * @return  String in UTF8
     */
    public String decodeUTF8(byte[] bytes) {
        return new String(bytes,  StandardCharsets.UTF_8);
    }

    /**
     * Diese Methode Konvertieren ein String zu byte-Array
     * @param string String in UTF8
     * @return bytes-Array
     */
    public byte[] encodeUTF8(String string) {
        return string.getBytes( StandardCharsets.UTF_8);
    }


    /**
     * Diese Methode zu Verschlüsslung ein Text(String) mit AES-Verschlüsslungsverfahren
     * und initialisiert ein IV wenn es benötigt wird
     * das Blockmode und das Padding werden in Betracht gezogen
     // * @param text der Text-String
     * @param key SecretKey der initialisierte Schlüssel
     * @return output der verschlüsselte Text
     */
    public byte[] Encrypt( SecretKey key) throws Exception {

        //String textToHex = toHex(text);
        // key = this.getKey();
        System.out.println("input    : " + text);
        System.out.println("Key :" + key.getEncoded());

        Cipher cipher =this.getCipher();
        //das output als byte-Array definieren
        byte[] output;

        //Convert von String zu byte[] oder von String zu Hex
        byte[] input =encodeUTF8(text);
        //byte[] input = Hex.decode(textToHex);
        System.out.println("input in byte[]   : " +input);
        //System.out.println("input in Hex   : " + Hex.toHexString(input));

        //hier wird ein IV bei CBC oder CTR...BlockMode erstellt

            SecureRandom random = new SecureRandom();
            this.iv = new byte[cipher.getBlockSize()];
            random.nextBytes(iv);

            //die Chiffre in ENCRYPT_MODE initialisieren mit iv
            cipher.init(Cipher.ENCRYPT_MODE, key);


        try {
            //das output verschlüsseln
            output = cipher.doFinal(input);
            // System.out.println("AES-Encrypted: " + Hex.toHexString(output));
            System.out.println("AES-Encrypted: " +  decodeUTF8(output));
            //inencrypted text als String spiechern
            encryptedText = decodeUTF8(output);
        }
        catch (IllegalBlockSizeException e) {
            System.out.println("Illegale Blocksize " + e);
            return null;

        }

        return output;

    }


    /**
     * Diese Methode zu Entschlüsslung ein byte-Array(byte[])
     * @param output der Verschlüsselter Text-String in byte[]
     * @param key SecretKey der initialisierte Schlüssel
     * @return outputE der verschlüsselte Text
     */
    public byte[] Decrypt (byte[] output  , SecretKey key) throws Exception {
        // das Cipher Object für AES
        Cipher cipher =this.getCipher();

        //hier wird ein IV bei CBC oder CTR...BlockMode mitgegeben


            //die Chiffre in DECRYPT_MODE initialisieren mit iv
            cipher.init(Cipher.DECRYPT_MODE, key);




        //das output Entschlüsseln
        byte[] output2 = cipher.doFinal(output);

        //Decrypting auf die Console ausgeben
        System.out.println("decrypt: " + decodeUTF8(output2));

        return output2;
    }

    /**
     * Diese Methode um das ganze Programm zu testen
     * @param text String
     */
    public void test(String text) throws Exception {
        //ein key erstellen
        SecretKey key = this.getKey();

        //verschlüsslung
        byte[] output =  Encrypt( key );
        //Entschlüsslung
        Decrypt(output,key);

    }








    public static void main(String[] args) throws Exception {

        //Test1
        System.out.println("-------------Test1-------------");
        ARC4 obj = new ARC4();
        obj.test("Abode Benni");
        ReadFile.fileou();

//Test2
       /** System.out.println("-------------Test2-------------");
        AESEncrypt obj2 = new AESEncrypt("CBC" , "PKCS7Padding");
        obj2.test("Benni Entwicklung Sichere Software");
        ReadFile.fileou();
        //Test3
        System.out.println("-------------Test3-------------");
        AESEncrypt obj3 = new AESEncrypt("CTS" , "NoPadding");
        obj3.test("Benni Entwicklung Sichere Software");
        ReadFile.fileou(); */

        //testen CTR...CFB...OFB sie sind alle ohne paddind



        //CTS ...streaming blockMode ----> ohne Padding
        //CTS Klartext = Geheimtext
        //letzten zwei Blocke vertauschen und dan ein zweites mal verschlüsseln dann am ende hängen
        //bedingung : mindestens ein KlarText größer als ein klarTextblock

    }

}

