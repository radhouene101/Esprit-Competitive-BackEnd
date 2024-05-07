package com.apex.picloud.services.contentModeration;

import com.apex.picloud.models.User;

public interface IContentModerationService {
    boolean containsForbiddenWords(String text) ;
}
