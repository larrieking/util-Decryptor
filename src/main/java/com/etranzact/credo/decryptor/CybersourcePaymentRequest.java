package com.etranzact.credo.decryptor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CybersourcePaymentRequest(
        ClientReferenceInformation clientReferenceInformation,
        PaymentInformation paymentInformation,
        OrderInformation orderInformation) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ClientReferenceInformation(String code) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record PaymentInformation(Card card) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Card(String number, String expirationMonth, String expirationYear) {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record OrderInformation(AmountDetails amountDetails, BillTo billTo) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record AmountDetails(String totalAmount, String currency) {}

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record BillTo(
                String firstName,
                String lastName,
                String address1,
                String locality,
                String administrativeArea,
                String postalCode,
                String country,
                String email,
                String phoneNumber) {}
    }
}
