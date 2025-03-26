package com.cungthinh.notificationservices.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetListUserIdsResponse {
    private boolean success;
    private int code;
    private String message;
    private List<String> data; // User IDs
    private String timestamp;
}
