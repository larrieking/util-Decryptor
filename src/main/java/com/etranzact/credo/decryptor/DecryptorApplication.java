package com.etranzact.credo.decryptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import jakarta.xml.bind.DatatypeConverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class DecryptorApplication {

    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 16;
    private static final int IV_LENGTH = 12;

    String newKey = "E0CB97592A026980C0C9966FEFAD5430b3c76baad0c816de70cdc2d3f8c6a0e2";

    String ivkey = "E0CB97592A026980C0C9966FEFAD5430b3c76baad0c816de70cdc2d3f8c6a0e2";//"E0CB97592A026980C0C9966FEFAD52114c137bd623473cc582ddaa0526659ae1";
    String reversedKey = "2e0a6c8f3d2cdc07ed618c0daab67c3b0345DAFEF6699C0C089620A29579BC0E";//"1ea9566250aadd285cc374326db731c41125DAFEF6699C0C089620A29579BC0E";

    String encryptedMessage = "43FFC2428B8E4A5ECF7EDECA07ABDD84D236997788AD3724F74A96C797AE535600D1638959BE4D9EC3FD7DD2E9CEE3CF084D120263B701C7B23F17FC1AE06352D8DDDBBE883B0771CE1D7E87CCE00CC30161F731FFA6232A7D962A83CCF7B7841144D66F71F71681763C7D401ECDE024347149DFB2C7342DF8D846658ED33BD89A415542E6FE6D0D6F7286BA262E30D78C86AFC5ED85ADCCC7EF00B6B7BCAE34775A6605687D3C87F63B2B8B51DCE241F6BC53A0141E768A64ACC882BA9CFABEDAEC82E3BA07581ABF5662225C1866B6E2065C6F60374324B43956B10520986DA93A3C85FEFAE24617C011A401336D2F430DC616BD9003AFB81C2235A616256100A94589F1505A7712CAF7EDE2AE100A63652F01876398623758DE7A2535978DDC8A51EE68AC4913C801B1D7FC9C661C0CBB2689DF875D57FF3B90821AA14FB476A17B10A3069AAD53B41E4421607E03D31D01FB33757E5F0ACCF9C6E99A8D8C00E10289F697D90F29D06FE3DAFC72B6BEB51F72959FC68247448E2A24095421D54BC83264D310F086413B25EBFD06382AFA76F88EFB82CBCE54781DE800E5EFB27AC3AD70E1681A2458AFC018CE73A03E20362EE5C09235F46A3DECC72420BF56317473752E7712263684A3BF7F251076091E1972A2497F5662645BE6D6D8F2E3B72B4F8288544A77E7F851D6BF17CEBE22EC95357D28366CF26E5D3A7DE544DE1C2794905B16F574C70B953E5A16BF39736EC18443DB275792EF7C1C70D09E8CE321EDB0E0CA69B1645B37A5F344D86D04A75103A695CD31A2C2BCEA103BA6507395163782EEF8060AF7BF13CBD8C09FDDAD10817CCA2ADD204ED750267A24159EE84D2A97E4944738437897314D1F22036A30416AD0520B5A9EC1C51001D4CF24AD4374541EF0D84468840FFE7D6CD9C917AD4A50193EDCEF44277D5B402843AECEF1B02B0F747B650E2F2B";

   // String encryptedMessage = "43FFC2428B8E4A5ECF7EDECA07ABDD84D236997788AD3724F74A96C797AE535600D1638959BE4D9EC3FD7DD2E9CEE3CF084D120263B701C7B23F17FC1AE06352D8DDDBBE883B0771CE1D7E87CCE00CC30161F731FFA6232A7D962A83CCF7B7841144D66F71F71681763C7D401ECDE024347149DFB2C7342DF8D846658ED33BD89A415542E6FE6D0D6F7286BA262E30D78C86AFC5ED85ADCCC7EF00B6B7BCAE34775A6605687D3C87F63B2B8B51DCE241F6BC53A0141E768A64ACC882BA9CFABEDAEC82E3BA07581ABF5662225C1866B6E2065C6F60374324B43956B10520986DA93A3C85FEFAE24617C011A401336D2F430DC616BD9003AFB81C2235A616256100A94589F1505A7712CAF7EDE2AE100A63652F01876398623758DE7A2535978DDC8A51EE68AC4913C801B1D7FC9C661C0CBB2689DF875D57FF3B90821AA14FB476A17B10A3069AAD53B41E4421607E03D31D01FB33757E5F0ACCF9C6E99A8D8C00E10289F697D90F29D06FE3DAFC72B6BEB51F72959FC68247448E2A24095421D54BC83264D310F086413B25EBFD06382AFA76F88EFB82CBCE54781DE800E5EFB27AC3AD70E1681A2458AFC018CE73A03E20362EE5C09235F46A3DECC72420BF56317473752E7712263684A3BF7F251076091E1972A2497F5662645BE6D6D8F2E3B72B4F8288544A77E7F851D6BF17CEBE22EC95357D28366CF26E5D3A7DE544DE1C2794905B16F574C70B953E5A16BF39736EC18443DB275792EF7C1C70D09E8CE321EDB0E0CA69B1645B37A5F344D86D04A75103A695CD31A2C2BCEA103BA6507395163782EEF8060AF7BF13CBD8C09FDDAD10817CCA2ADD204ED750267A24159EE84D2A97E4944738437897314D1F22036A30416AD0520B5A9EC1C51001D4CF24AD4374541EF0D84468840FFE7D6CD9C917AD4A50193EDCEF44277D5B402843AECEF1B02B0F747B650E2F2B";

    private final ObjectMapper objectMapper;
    private final CredoHttpClient credoClient;
    private final CybersourceHttpClient cybersourceHttpClient;


    @Bean
    ApplicationRunner ussdRunner(){
        return arg ->{

       InterswitchWebhookRequest request =     InterswitchWebhookRequest.builder()
                    .event("TRANSACTION.COMPLETED")
                    .uuid("uatp005GZr12v4ZC66EC")
                    .timestamp(1723321665180L)
                    .data(InterswitchWebhookRequest.Data.builder()
                            .channel("USSD")
                            .remittanceAmount(1676311)
                            .bankCode("011")
                            .amount(1690000)
                            .paymentReference("101300013145915245")
                            .splitAccounts(new ArrayList<>())
                            .transactionDate(1723321665180L)
                            .responseCode("00")
                            .responseDescription("Approved by Financial Institution")
                            .paymentId(1501258153)
                            .merchantCustomerId("2348034704828")
                            .token(null)
                            .accountNumber(null)
                            .escrow(false)
                            .merchantReference("uatp005GZr12v4ZC66EC")
                            .currencyCode("566")
                            .merchantCustomerName("2348034704828")
                            .cardNumber("")
                            .build()).build();
       String data = objectMapper.writeValueAsString(request);

       //String data1 = "{\"event\":\"TRANSACTION.COMPLETED\",\"uuid\":\"D9RR008UPd00izOn27F5-Ehn\",\"timestamp\":1723126651410,\"data\":{\"remittanceAmount\":150570,\"bankCode\":\"363\",\"amount\":151800,\"paymentReference\":\"OPA|API|MX45143|08-08-2024|1498733827|991603\",\"channel\":\"API\",\"splitAccounts\":[],\"retrievalReferenceNumber\":\"666654797793\",\"transactionDate\":1723126651410,\"responseCode\":\"00\",\"responseDescription\":\"Approved by Financial Institution\",\"paymentId\":1498733827,\"merchantCustomerId\":\"anointingntun@gmail.com\",\"escrow\":false,\"merchantReference\":\"D9RR008UPd00izOn27F5-Ehn\",\"currencyCode\":\"566\",\"cardNumber\":\"\"}}";

       //request = objectMapper.readValue(data1, new TypeReference<> (){});
      String generatedHmacSha512 = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, "toXuBDQJcJJ0IosMdgN8").hmacHex(objectMapper.writeValueAsString(request));

      System.out.println(generatedHmacSha512);

       credoClient.notifyUSSD(request, generatedHmacSha512);

        };
    }

    public DecryptorApplication(ObjectMapper objectMapper, CredoHttpClient credoClient, CybersourceHttpClient cybersourceHttpClient) {
        this.objectMapper = objectMapper;
        this.credoClient = credoClient;
        this.cybersourceHttpClient = cybersourceHttpClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(DecryptorApplication.class, args);
    }

//
//    @Bean
//    ApplicationRunner applicationRunner() {
//        return args -> {
//
//
//            StanbicNotificationRequest request = StanbicNotificationRequest.builder()
//                    .transactionReferenceId("5211-8350467111")
//                    .accountName("John Doe")
//                    .amount("1100")
//                    .adminFee("20.00")
//                    .amountNetChargesAndVat("1234")
//                    .checkSum("123456718")
//                    .checkSumId(5698709759L)
//                    .bankCharge("20.00")
//                    .customerAccountNumber("5430000046")
//                    .srcAcctBankCode("00015")
//                    .sessionId("00010011300911114643131410000012")
//                    .srcAcctName("Dummy Virtual-ID Name 1")
//                    .vatOnNetCharges("200.79")
//                    .srcAcct("5430000046")
//                    .amountNetCharges("2222222")
//                    .build();
//
//            //String message = encryptPayload(objectMapper.writeValueAsString(List.of(request)), ivkey, reversedKey);
//            String message = encrypt(objectMapper.writeValueAsString(List.of(request)), hexToBytes(newKey));
//try {
//    CybersourcePaymentRequest cybersourcePaymentRequest = getCybersourcePaymentRequest();
//
//    IntegrationsConfig config = new IntegrationsConfig("o/PDGgFkMg/R8G4W9msjAgeI5UtuOS/pUCfmzX5snPY=", "197ab6fc-e867-4382-8235-d3467a6be3e6", "acl123_1720515180");
//    String digest = GenerateDigest(objectMapper.writeValueAsString(cybersourcePaymentRequest));
//    log.error("digest {}",objectMapper.writeValueAsString(cybersourcePaymentRequest));
//    String date = convertRFCDateformat(LocalDateTime.now());
//
//    String signatureParam = "host: apitest.cybersource.com\n" +
//            "date: " + date + "\n" +
//            "request-target: post /pts/v2/payments\n" +
//            "digest: " + digest + "\n" +
//            "v-c-merchant-id: " + config.organizationId();
//
//    Map<String, String> headers = Map.of(
//            "Digest", digest,
//            "Host", "apitest.cybersource.com",
//            "Request-target", "post /pts/v2/payments",
//            "Date", date,
//            "v-c-merchant-id", config.organizationId(),
//
//            "Signature", GenerateSignatureFromParams(config, signatureParam, objectMapper)
//    );
//
//    cybersourceHttpClient.initiatePayment(cybersourcePaymentRequest, headers);
////   StanbicResponse stanbicResponse = credoClient.notifyStanbic(Map.of("request", message));
////    log.error(objectMapper.writeValueAsString(stanbicResponse));
//   // System.out.println(message);
//
//}catch (Exception e){
//    log.error(e.getMessage());
//}
//            //System.out.println("Encrypted message: " + encryptPayload("Hello World", ivkey, reversedKey));
//
//
////System.out.println("Decrypted message: " + decryptPayload(encryptedMessage, ivkey, reversedKey));
//            // System.out.println("Encrypted message: " + encryptPayload(decryptPayload(encryptedMessage, ivkey, reversedKey), ivkey, reversedKey));
//        };
//    }

    private static CybersourcePaymentRequest getCybersourcePaymentRequest() {
        CybersourcePaymentRequest.ClientReferenceInformation clientReferenceInformation =
                new CybersourcePaymentRequest.ClientReferenceInformation("TC50171_3");

        CybersourcePaymentRequest.PaymentInformation.Card transCard =
                new CybersourcePaymentRequest.PaymentInformation.Card("5555555555554444", "03", "2050");

        CybersourcePaymentRequest.PaymentInformation paymentInformation =
                new CybersourcePaymentRequest.PaymentInformation(transCard);

        CybersourcePaymentRequest.OrderInformation.AmountDetails amountDetails =
                new CybersourcePaymentRequest.OrderInformation.AmountDetails("10000", "NGN");

        CybersourcePaymentRequest.OrderInformation.BillTo billTo =
                new CybersourcePaymentRequest.OrderInformation.BillTo(
                        "John",
                        "Doe",
                        "1 Market St",
                        "san francisco",
                        "CA",
                        "94105",
                        "US",
                        "test@cybs.com",
                        "4158880000"
                );

        CybersourcePaymentRequest.OrderInformation orderInformation =
                new CybersourcePaymentRequest.OrderInformation(amountDetails, billTo);

        return new CybersourcePaymentRequest(
                clientReferenceInformation,
                paymentInformation,
                orderInformation
        );

    }

//
//    public static String decryptPayload(String encryptedMessage, String ivkey, String
//            reversedKey) {
//
//        BytesEncryptor encryptor = Encryptors.stronger(ivkey, reversedKey);
//        byte[] encryptedBytes =
//                DatatypeConverter.parseHexBinary(encryptedMessage);
//        byte[] decryptedBytes = encryptor.decrypt(encryptedBytes);
//        return new String(decryptedBytes);
//
//    }
//
//    public static String encryptPayload(String message, String ivkey, String
//            reversedKey) {
//
//        BytesEncryptor encryptor = Encryptors.stronger(ivkey, reversedKey);
//
//        byte[] decryptedBytes = encryptor.encrypt(message.getBytes());
//        return DatatypeConverter.printHexBinary(decryptedBytes);
//
//    }




    public static String decrypt(String cipherText, byte[] key) throws Exception {


        byte[] cipherTextWithIv = hexToBytes(cipherText);
        byte[] iv = new byte[IV_LENGTH];
        byte[] actualCipherText = new byte[cipherTextWithIv.length - IV_LENGTH];
        System.arraycopy(cipherTextWithIv, 0, iv, 0, IV_LENGTH);
        System.arraycopy(cipherTextWithIv, IV_LENGTH, actualCipherText, 0, actualCipherText.length);


        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, ENCRYPTION_ALGORITHM), parameterSpec);
        byte[] plainText = cipher.doFinal(actualCipherText);
        return new String(plainText, StandardCharsets.UTF_8);
    }


    public static String encrypt(String plainText, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] iv = new byte[IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);

        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, ENCRYPTION_ALGORITHM), parameterSpec);
        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] cipherTextWithIv = new byte[IV_LENGTH + cipherText.length];
        System.arraycopy(iv, 0, cipherTextWithIv, 0, IV_LENGTH);
        System.arraycopy(cipherText, 0, cipherTextWithIv, IV_LENGTH, cipherText.length);

        return bytesToHex(cipherTextWithIv);
    }


    public static byte[] hexToBytes(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


    public static String GenerateDigest(String bodyText) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bodyText.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        return "SHA-256=" + Base64.getEncoder().encodeToString(digest);
    }


    public static String GenerateSignatureFromParams(IntegrationsConfig config, String param, ObjectMapper objectMapper) throws InvalidKeyException, NoSuchAlgorithmException, JsonProcessingException {
       log.error("config {}",objectMapper.writeValueAsString(config));
        byte[] decodedKey = Base64.getDecoder().decode(config.sharedSecret());
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(originalKey);
        hmacSha256.update(param.getBytes());
        byte[] HmachSha256DigestBytes = hmacSha256.doFinal();
        String signature = Base64.getEncoder().encodeToString(HmachSha256DigestBytes);

        return "keyid=\"" +config.secret()+ "\"," +
                " algorithm=\"" + "HmacSHA256" + "\"," +
                " headers=\"" + "host date request-target digest v-c-merchant-id" + "\"," +
                " signature=\"" +signature + "\"";


    }


    public static String convertRFCDateformat(LocalDateTime now) {

        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("GMT"));

        DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        return formatter.format(zonedDateTime);

    }

    record IntegrationsConfig(String sharedSecret, String secret, String organizationId){}



}
