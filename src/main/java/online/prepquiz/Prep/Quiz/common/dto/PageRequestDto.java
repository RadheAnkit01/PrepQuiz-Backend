package online.prepquiz.Prep.Quiz.common.dto;

public record PageRequestDto(
        int page,
        int pageSize,
        String sortBy,
        String direction
) {

}