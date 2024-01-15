package com.springboot.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsCursor {

    private String pageId;
    private int pageSize;
    private String sortBy;
    private String pageKey;
    private String direction;

    public String encodeCursorString() {
        String cursorString = String.format("%s\0%s\0%s\0%s\0%s",
                this.pageKey, this.pageId, this.pageSize, this.sortBy, this.direction);
        return Base64.getEncoder().encodeToString(cursorString.getBytes(StandardCharsets.UTF_8));
    }

    public static Optional<PostsCursor> decodeCursorString(String cursorString) {
        if (cursorString == null) {
            return Optional.empty();
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(cursorString);
            String decodedCursorString = new String(decodedBytes, StandardCharsets.UTF_8);
            String[] cursorItems = decodedCursorString.split("\0", 5);

            String pageKey = cursorItems[0];
            String pageId = cursorItems[1];
            int pageSize = Integer.parseInt(cursorItems[2]);
            String sortBy = cursorItems[3];
            String direction = cursorItems[4];

            return Optional.of(new PostsCursor(pageId, pageSize, sortBy, pageKey, direction));
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }
}
