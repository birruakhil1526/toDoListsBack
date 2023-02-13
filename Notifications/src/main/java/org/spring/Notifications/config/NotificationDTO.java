package org.spring.Notifications.config;

import lombok.*;
import org.json.simple.JSONObject;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NotificationDTO {
    private JSONObject jsonObject;
}
