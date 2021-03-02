package zephyr.jwt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

/**
 * http://www.ruanyifeng.com/blog/2019/04/oauth-grant-types.html
 *  https://tools.ietf.org/html/rfc6749#section-4.2.2
 *
 *
 * https://oauth.b.com/token?grant_type=client_credentials&client_id=CLIENT_ID&client_secret=CLIENT_SECRET
 *      {
 *        "access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLkuLvpophqMUJ0dHFyWiIsIm5iZiI6MTU5MjU1Nzc0MiwiZXhwIjoxNTkyNTY0OTQyLCJpYXQiOjE1OTI1NTc3NDJ9.lrrRVG5eGlU6FcuR0m9-04rIZpp1qju71Hdj1ORI0TA",
 *        "token_type":"Bearer",
 *        "expires_in":3600
 *      }
 *
 */
public class AppIdDemo {

    //生成 app_secret 密钥
    private final static String SERVER_NAME = "my demo";
    private final static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * 利用62个可打印字符，通过随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，然后通过模62操作，结果作为索引取出字符，
     */
    public static String getAppId() {
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    /**
     * 通过appId和内置关键词生成APP Secret
     */
    public static String getAppSecret(String appId) {
        try {
            String[] array = new String[]{appId, SERVER_NAME};
            StringBuilder sb = new StringBuilder();
            // 字符串排序
            Arrays.sort(array);
            for (String s : array) {
                sb.append(s);
            }
            String str = sb.toString();
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuilder hexStr = new StringBuilder();
            for (byte b : digest) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        String appId = getAppId();
        String appSecret = getAppSecret(appId);
        System.out.println("appId: "+appId);
        System.out.println("appSecret: "+appSecret);
    }
}
