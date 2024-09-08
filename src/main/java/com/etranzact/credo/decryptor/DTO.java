package com.etranzact.credo.decryptor;


import java.math.BigDecimal;


 record DTO(
        String transactionReferenceId,
        String customerAccountNumber,
        String accountName,
        BigDecimal amount,
        String checkSum,
        String checkSumId,
        BigDecimal bankCharge,
        BigDecimal adminFee,
        BigDecimal amountNetCharges,
        BigDecimal vatOnNetCharges,
        BigDecimal amountNetChargesAndVat,
        String srcAcct,
        String srcAcctBankCode,
        String srcAcctName,
        String sessionId
) { }
