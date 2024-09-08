package com.etranzact.credo.decryptor;

import lombok.Builder;

@Builder
public record ProvidusRequest(String accountNumber,
                              String channelId,
                              String currency,
                              int feeAmount,
                              String initiationTranRef,
                              String ipAddress,
                              String sessionId,
                              int settledAmount,
                              String settlementId,
                              String sourceAccountName,
                              String sourceAccountNumber,
                              String sourceBankName,
                              String tranDateTime,
                              String tranRemarks,
                              int transactionAmount,
                              String userAgent,
                              boolean utilized,
                              int vatAmount) {
}
