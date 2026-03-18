package com.me.crypto.trading.common.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Txns {
    public static String TXN_CODE_PREFIX = "TXN-";

    public static String generateTxnCode(){
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(Miscs.DATETIME_FORMAT_ISO_8601_BASIC));
        String randomPart = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 6)
                .toUpperCase();

        return TXN_CODE_PREFIX + timestamp + "-" + randomPart;
    }
}
