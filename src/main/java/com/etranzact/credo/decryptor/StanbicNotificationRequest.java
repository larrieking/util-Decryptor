package com.etranzact.credo.decryptor;

import lombok.Builder;
//{
//        "transactionReferenceId": "{prefix}-{ checkSumId}",
//        "customerAccountNumber":"{prefix}123456",
//        "accountName":" Dummy Virtual-ID Name 1",
//        "amount":"2500.00",
//        "checkSum":"$2a$09$gK7efUlJdKF61u/GEvnfe.WItODiBOajtdmjidqc1o.c2ec75gNR.",
//        "checkSumId":5698709759,
//        "bankCharge":"40.31",
//        "adminFee":"53.75",
//        "amountNetCharges":"2405.94",
//        "vatOnNetCharges":"180.45",
//        "amountNetChargesAndVat":"2225.49",
//        "srcAcct":"6021545607",
//        "srcAcctBankCode":"991228",
//        "srcAcctName":"Funds Source Account Name",
//        "sessionID":"Funds Source Transaction session ID"
//        }


@Builder
public record StanbicNotificationRequest(
        String transactionReferenceId,
        String customerAccountNumber,
        String accountName,
        String amount,
        String checkSum,
        Long  checkSumId,
        String bankCharge,
        String adminFee,
        String amountNetCharges,
        String vatOnNetCharges,
        String amountNetChargesAndVat,
        String srcAcct,
        String srcAcctBankCode,
        String srcAcctName,
        String sessionId
) { }
