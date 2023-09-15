package kopo.poly.util;

public class EncrypTest {
    public static void main(String[] args) {
        System.out.println("----------------------------");
        System.out.println("해시 알고리즘");

        String planText = "암호화할 문자열";
        System.out.println("해시 암호화할 문자열 : " + planText);
        String hashEnc = EncryptUtil.encHashSHA256(planText);

        System.out.println("해시 암호화 결과 : " + hashEnc);
        System.out.println("-------------------------------");

        System.out.println("AES-128 암호화 알고리즘");
        System.out.println("AES-128 암호화할 문자열 : " + planText);
        String aesEnc = null;
        try {
            aesEnc = EncryptUtil.encAES128CBC(planText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("AES-128 암호화 결과 : " +aesEnc);

        String aesDec = null;
        try {
            aesDec = EncryptUtil.decAES128CBC(aesEnc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("AES-128 복호화 결과 : " + aesDec);
        System.out.println("-------------------------------");
    }
}
