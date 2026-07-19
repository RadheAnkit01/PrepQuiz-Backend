package online.prepquiz.Prep.Quiz.assessment.service;

import lombok.AllArgsConstructor;
import online.prepquiz.Prep.Quiz.assessment.dto.CreateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.dto.CreateAssessmentQuestionDto;
import online.prepquiz.Prep.Quiz.assessment.dto.UpdateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.dto.UpdateAssessmentQuestionDto;
import online.prepquiz.Prep.Quiz.assessment.dto.AssessmentResponseDto;
import online.prepquiz.Prep.Quiz.assessment.entity.Assessment;
import online.prepquiz.Prep.Quiz.assessment.entity.AssessmentQuestion;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentScopeType;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentStatus;
import online.prepquiz.Prep.Quiz.assessment.mapper.AssessmentMapper;
import online.prepquiz.Prep.Quiz.assessment.mapper.AssessmentQuestionMapper;
import online.prepquiz.Prep.Quiz.assessment.repository.AssessmentRepository;
import online.prepquiz.Prep.Quiz.assessment.validator.AssessmentValidator;
import online.prepquiz.Prep.Quiz.chapter.ChapterService;
import online.prepquiz.Prep.Quiz.common.exception.ResourceNotFoundException;
import online.prepquiz.Prep.Quiz.course.CourseService;
import online.prepquiz.Prep.Quiz.question.entity.Question;
import online.prepquiz.Prep.Quiz.question.repository.QuestionRepository;


import online.prepquiz.Prep.Quiz.question.service.QuestionService;
import online.prepquiz.Prep.Quiz.subject.SubjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AssessmentServiceImpl implements AssessmentService{
    private final AssessmentRepository assessmentRepository;
    private final AssessmentMapper assessmentMapper;
    private final AssessmentQuestionMapper assessmentQuestionMapper;
    private final AssessmentValidator assessmentValidator;
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;
    private final ChapterService chapterService;
    private final SubjectService subjectService;
    private final CourseService courseService;


    @Override
    public AssessmentResponseDto createAssessment(
            CreateAssessmentDto request
    ) {

        assessmentValidator.validateCreate(request);

        Assessment assessment =
                assessmentMapper.toEntity(request);

        assessment.setAssessmentQuestions(
                buildAssessmentQuestions(
                        assessment,
                        request.getAssessmentQuestions()
                )
        );

        Assessment savedAssessment =
                assessmentRepository.save(assessment);

        return assessmentMapper.toResponse(savedAssessment);
    }

    @Override
    @Transactional(readOnly = true)
    public AssessmentResponseDto getAssessmentById(Long id) {

        Assessment assessment = getAssessmentOrThrow(id);

        return assessmentMapper.toResponse(assessment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssessmentResponseDto> getAssessments(
            AssessmentScopeType scopeType,
            Long scopeId,
            AssessmentStatus status,
            int pageNo, int pageSize, String direction, String sortBy
    ) {
            Sort sort = direction.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending():
                    Sort.by(sortBy).descending();
            Pageable pageable =  PageRequest.of(pageNo,pageSize,sort);

        Page<Assessment> page;
        if (scopeType != null && scopeId != null && status != null) {
            page = assessmentRepository
                    .findByScopeTypeAndScopeIdAndStatus(
                            scopeType,
                            scopeId,
                            status,
                            pageable
                    );
        } else if (scopeType != null && scopeId != null) {
            page = assessmentRepository
                    .findByScopeTypeAndScopeId(
                            scopeType,
                            scopeId,
                            pageable
                    );
        } else if (status != null) {
            page = assessmentRepository
                    .findByStatus(
                            status,
                            pageable
                    );
        } else {
            page = assessmentRepository.findAll(pageable);
        }
        return page.map(assessmentMapper::toResponse);
    }


    @Override
    public AssessmentResponseDto updateAssessment(
            Long id,
            UpdateAssessmentDto request
    ) {

        Assessment assessment =
                getAssessmentOrThrow(id);

        assessmentValidator.validateUpdate(
                assessment,
                request
        );

        assessmentMapper.updateEntity(
                request,
                assessment
        );
        syncAssessmentQuestions(
                assessment,
                request.getAssessmentQuestions()
        );
        assessmentRepository.saveAndFlush(assessment);
        Assessment updatedAssessment = getAssessmentOrThrow(assessment.getId());
        return assessmentMapper.toResponse(
                updatedAssessment
        );
    }



    @Override
    public void deleteAssessment(Long id) {

        Assessment assessment =
                getAssessmentOrThrow(id);

        assessmentValidator.validateDelete(
                assessment
        );

        assessmentRepository.delete(assessment);
    }





    @Override
    public void publishAssessment(Long id) {

        Assessment assessment =
                getAssessmentOrThrow(id);

        assessmentValidator.validatePublish(
                assessment
        );

        assessment.setStatus(
                AssessmentStatus.PUBLISHED
        );

        assessmentRepository.save(assessment);
    }




    @Override
    public void archiveAssessment(Long id) {

        Assessment assessment =
                getAssessmentOrThrow(id);

        assessmentValidator.validateArchive(
                assessment
        );

        assessment.setStatus(
                AssessmentStatus.ARCHIVED
        );

        assessmentRepository.save(assessment);
    }




    //--------------------
    //   Helper
    //-------------------

    private Assessment getAssessmentOrThrow(Long id) {

        return assessmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assessment not found with id : " + id
                        )
                );
    }

    private Question getQuestionOrThrow(Long id) {

        return questionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Question not found with id : " + id
                        )
                );
    }

    private void syncAssessmentQuestions(
            Assessment assessment,
            List<UpdateAssessmentQuestionDto> requests
    ) {

        Map<Long, AssessmentQuestion> existingQuestionMap =
                assessment.getAssessmentQuestions()
                        .stream()
                        .collect(Collectors.toMap(
                                AssessmentQuestion::getId,
                                Function.identity()
                        ));

        Set<Long> requestIds = new HashSet<>();

        for (UpdateAssessmentQuestionDto request : requests) {

            // New Question
            if (request.getId() == null) {

                Question question =
                        getQuestionOrThrow(request.getQuestionId());

                AssessmentQuestion assessmentQuestion =
                        assessmentQuestionMapper.toEntity(request);

                assessmentQuestion.setAssessment(assessment);
                assessmentQuestion.setQuestion(question);

                assessment.getAssessmentQuestions()
                        .add(assessmentQuestion);

                continue;
            }

            requestIds.add(request.getId());

            AssessmentQuestion assessmentQuestion =
                    existingQuestionMap.get(request.getId());

            if (assessmentQuestion == null) {

                throw new ResourceNotFoundException(
                        "Assessment Question not found with id : "
                                + request.getId()
                );
            }

            // Update scalar fields
            assessmentQuestionMapper.updateEntity(
                    request,
                    assessmentQuestion
            );

            // Question may have changed
            if (!assessmentQuestion.getQuestion().getId()
                    .equals(request.getQuestionId())) {

                Question question =
                        getQuestionOrThrow(request.getQuestionId());

                assessmentQuestion.setQuestion(question);
            }
        }

        assessment.getAssessmentQuestions()
                .removeIf(question ->
                        question.getId() != null
                                && !requestIds.contains(question.getId()));
    }

    private List<AssessmentQuestion> buildAssessmentQuestions(
            Assessment assessment,
            List<CreateAssessmentQuestionDto> requests
    ) {

        List<AssessmentQuestion> assessmentQuestions = new ArrayList<>();

        for (CreateAssessmentQuestionDto request : requests) {

            Question question =
                    getQuestionOrThrow(request.getQuestionId());

            AssessmentQuestion assessmentQuestion =
                    assessmentQuestionMapper.toEntity(request);

            assessmentQuestion.setAssessment(assessment);
            assessmentQuestion.setQuestion(question);

            assessmentQuestions.add(assessmentQuestion);
        }

        return assessmentQuestions;
    }
}
