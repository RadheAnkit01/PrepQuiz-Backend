package online.prepquiz.Prep.Quiz.assessment.dto;

public interface AssessmentQuestionRequest {

    Long getQuestionId();

    Integer getDisplayOrder();

    Integer getMarks();

    Integer getNegativeMarks();
}
