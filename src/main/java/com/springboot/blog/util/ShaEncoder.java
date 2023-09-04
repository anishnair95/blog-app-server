package com.springboot.blog.util;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.apache.commons.codec.digest.DigestUtils.sha512Hex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class for encoding purpose
 */
public class ShaEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShaEncoder.class);

    public static String hashKeyGenerator(String type, String... params) {

        LOGGER.info("Inside ShaEncoder hashKeyGenerator()");
        if (params == null || params.length == 0) {
            throw new RuntimeException("params for key generation cannot be empty");
        }
        StringBuilder sb = new StringBuilder();
        for (String param : params) {
            sb.append(param);
        }
        switch (type) {
            case "256Hex":
                return sha256Hex(sb.toString());
            default:
                return sha512Hex(sb.toString());
        }
    }


    public static void main(String args[]) {
        String str = "javaguides";
        System.out.println(hashKeyGenerator("256Hex", str));
    }
}
