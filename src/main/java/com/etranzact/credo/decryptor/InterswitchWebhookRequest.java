package com.etranzact.credo.decryptor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterswitchWebhookRequest {

    @JsonProperty("event")
    public String event;
    @JsonProperty("uuid")
    public String uuid;
    @JsonProperty("timestamp")
    public Long timestamp;
    @JsonProperty("data")
    public Data data;

    @lombok.Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        @JsonProperty("remittanceAmount")
        public Integer remittanceAmount;
        @JsonProperty("bankCode")
        public String bankCode;
        @JsonProperty("amount")
        public Integer amount;
        @JsonProperty("paymentReference")
        public String paymentReference;
        @JsonProperty("channel")
        public String channel;
        @JsonProperty("splitAccounts")
        public List<Object> splitAccounts;
        @JsonProperty("retrievalReferenceNumber")
        public String retrievalReferenceNumber;
        @JsonProperty("transactionDate")
        public Long transactionDate;
        @JsonProperty("accountNumber")
        public Object accountNumber;
        @JsonProperty("responseCode")
        public String responseCode;
        @JsonProperty("token")
        public Object token;
        @JsonProperty("responseDescription")
        public String responseDescription;
        @JsonProperty("paymentId")
        public Integer paymentId;
        @JsonProperty("merchantCustomerId")
        public String merchantCustomerId;
        @JsonProperty("escrow")
        public Boolean escrow;
        @JsonProperty("merchantReference")
        public String merchantReference;
        @JsonProperty("currencyCode")
        public String currencyCode;
        @JsonProperty("merchantCustomerName")
        public String merchantCustomerName;
        @JsonProperty("cardNumber")
        public String cardNumber;

    }

}
