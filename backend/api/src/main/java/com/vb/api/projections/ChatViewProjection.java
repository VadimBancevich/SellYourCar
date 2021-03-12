package com.vb.api.projections;

public interface ChatViewProjection {

    Long getId();
    Long getCarId();
    Long getBrandId();
    Long getModelId();
    Long getGenerationId();
    String getFirstCarImg();
    String getLastMessageContent();
    String getLastMessageFromUserName();
    Integer getUnreadCount();

}
