package online.prepquiz.Prep.Quiz.common.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PageResponse<T>(
        List<T> content,

        int page,
        int size,

        long totalElements,
        int totalPages,

        boolean first,
        boolean last,

        boolean hasNext,
        boolean hasPrevious,

        int numberOfElements,
        boolean empty
) {
}