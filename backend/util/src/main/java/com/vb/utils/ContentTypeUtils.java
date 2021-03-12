package com.vb.utils;

import org.apache.http.entity.ContentType;

import java.util.Arrays;

public class ContentTypeUtils {

    private ContentTypeUtils() {
    }

    public static String convertImageContentTypeToExtension(ContentType contentType) {
        if (contentType.equals(ContentType.IMAGE_JPEG)) {
            return "jpg";
        } else if (contentType.equals(ContentType.IMAGE_PNG)) {
            return "png";
        }
        throw new IllegalArgumentException("No such extension for ContentType - " + contentType);
    }

    public static void checkContentType(ContentType contentTypeForCheck, ContentType... checkableContentTypes) {
        if (!Arrays.asList(checkableContentTypes).contains(contentTypeForCheck)) {
            throw new IllegalArgumentException("Wrong content type");
        }
    }

}
