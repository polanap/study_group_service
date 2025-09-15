package org.example.study_group_service.models;

import java.util.List;

public enum RequestStatus {
    NEW,
    APPROVED,
    REJECTED;

    public static List<RequestStatus> possibleStatuses(RequestStatus requestStatus) {
        return switch (requestStatus) {
            case NEW -> List.of(RequestStatus.APPROVED, RequestStatus.REJECTED);
            case APPROVED -> List.of();
            case REJECTED -> List.of();
        };
    }

    public static boolean isNext (RequestStatus oldStatus, RequestStatus newStatus) {
        return possibleStatuses(oldStatus).contains(newStatus);
    }
}
