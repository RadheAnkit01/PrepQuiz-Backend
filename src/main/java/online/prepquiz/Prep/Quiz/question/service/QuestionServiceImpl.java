package online.prepquiz.Prep.Quiz.question.service;

import lombok.RequiredArgsConstructor;
import online.prepquiz.Prep.Quiz.chapter.Chapter;
import online.prepquiz.Prep.Quiz.chapter.ChapterRepository;
import online.prepquiz.Prep.Quiz.common.dto.PageResponse;
import online.prepquiz.Prep.Quiz.common.exception.BadRequestException;
import online.prepquiz.Prep.Quiz.common.exception.ResourceNotFoundException;
import online.prepquiz.Prep.Quiz.common.mapper.PageResponseMapper;
import online.prepquiz.Prep.Quiz.question.QuestionSpecification;
import online.prepquiz.Prep.Quiz.question.dto.*;
import online.prepquiz.Prep.Quiz.question.entity.Option;
import online.prepquiz.Prep.Quiz.question.entity.Question;
import online.prepquiz.Prep.Quiz.question.enums.Difficulty;
import online.prepquiz.Prep.Quiz.question.enums.QuestionScopeType;
import online.prepquiz.Prep.Quiz.question.enums.QuestionStatus;
import online.prepquiz.Prep.Quiz.question.enums.QuestionType;
import online.prepquiz.Prep.Quiz.question.mapper.OptionMapper;
import online.prepquiz.Prep.Quiz.question.mapper.QuestionMapper;
import online.prepquiz.Prep.Quiz.question.repository.QuestionRepository;
import online.prepquiz.Prep.Quiz.question.validation.QuestionValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;
    private final ChapterRepository chapterRepository;
    private final QuestionMapper questionMapper;
    private final OptionMapper optionMapper;
    private final QuestionValidator questionValidator;

    @Override
    public QuestionResponseDto createQuestion(CreateQuestionDto request) {
        questionValidator.validateForCreate(request);

        Question question = questionMapper.toEntity(request);

        question.setChapter(getChapterOrThrow(request.getChapterId()));
        question.setStatus(QuestionStatus.ACTIVE);

        List<Option> options = buildOptions(question, request.getOptions());

        question.setOptions(options);

        question = questionRepository.save(question);

        return questionMapper.toResponse(question);
    }

    @Override
    @Transactional
    public BulkQuestionResponseDto createQuestions(
            List<CreateQuestionDto> requests
    ) {
        if (requests.isEmpty()) {

            throw new BadRequestException(
                    "Question list cannot be empty."
            );
        }

        if (requests.size() > 500) {

            throw new BadRequestException(
                    "Maximum 500 questions can be uploaded at a time."
            );
        }

        List<Question> questions = new ArrayList<>();
        for (CreateQuestionDto request : requests) {
            questionValidator.validateForCreate(request);
            Question question = questionMapper.toEntity(request);
            Chapter chapter = getChapterOrThrow(request.getChapterId());
            question.setChapter(chapter);
            question.setOptions(
                    buildOptions(
                            question,
                            request.getOptions()
                    )
            );
            questions.add(question);
        }
        List<Question> savedQuestions =
                questionRepository.saveAll(questions);
        return BulkQuestionResponseDto.builder()
                .total(requests.size())
                .success(savedQuestions.size())
                .failed(0)
                .questions(
                        savedQuestions.stream()
                                .map(questionMapper::toResponse)
                                .toList()
                )
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionResponseDto getQuestionById(Long questionId) {

        Question question = getQuestionOrThrow(questionId);

        return questionMapper.toResponse(question);
    }


    @Override
    public PageResponse<QuestionResponseDto> getQuestions(
            QuestionScopeType scopeType,
            Long scopeId,
            QuestionType questionType,
            Difficulty difficulty,
            QuestionStatus status,
            int pageNo,
            int pageSize,
            String direction,
            String sortBy
    ) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Specification<Question> specification =
                Specification.where(
                        QuestionSpecification.hasScope(scopeType, scopeId)
                ).and(
                        QuestionSpecification.hasDifficulty(difficulty)
                ).and(
                        QuestionSpecification.hasStatus(status)
                ).and(
                        QuestionSpecification.hasQuestionType(questionType)
                );
        Page<QuestionResponseDto> page =
                questionRepository
                        .findAll(specification, pageable)
                        .map(questionMapper::toResponse);
        return PageResponseMapper.from(page);
    }

    @Override
    public QuestionResponseDto updateQuestion(Long id, UpdateQuestionDto request) {
        Question question = getQuestionOrThrow(id);

        questionValidator.validateForUpdate(
                id,
                question.getChapter().getId(),
                request.getText(),
                request.getQuestionType(),
                request.getOptions()
        );

        // update question
        questionMapper.updateEntity(request, question);
        // sync options
        syncOptions(question, request.getOptions());
        Question savedquestion = questionRepository.save(question);
        return questionMapper.toResponse(savedquestion);

    }

    @Override
    public void deleteQuestion(Long id) {
        Question question = getQuestionOrThrow(id);
        questionRepository.delete(question);
    }



    private Chapter getChapterOrThrow(Long chapterId) {

        return chapterRepository.findById(chapterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Chapter not found with id : " + chapterId
                        ));
    }

    private Question getQuestionOrThrow(Long questionId) {

        return questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Question not found with id : " + questionId
                        ));
    }

    private List<Option> buildOptions(
            Question question,
            List<CreateOptionDto> requests
    ) {
        List<Option> options = new ArrayList<>();
        for (CreateOptionDto request : requests) {
            Option option = optionMapper.toEntity(request);
            option.setQuestion(question);
            options.add(option);
        }
        return options;
    }

    private void syncOptions(
            Question question,
            List<UpdateOptionDto> requests
    ) {

        Map<Long, Option> existingOptionMap =
                question.getOptions()
                        .stream()
                        .collect(Collectors.toMap(
                                Option::getId,
                                Function.identity()
                        ));

        Set<Long> requestIds = new HashSet<>();

        for (UpdateOptionDto request : requests) {

            if (request.getId() == null) {

                Option option = optionMapper.toEntity(request);

                option.setQuestion(question);

                question.getOptions().add(option);

                continue;
            }

            requestIds.add(request.getId());

            Option option =
                    existingOptionMap.get(request.getId());

            if (option == null) {

                throw new ResourceNotFoundException(
                        "Option not found with id : "
                                + request.getId()
                );
            }

            optionMapper.updateEntity(option, request);
        }

        question.getOptions().removeIf(option ->

                option.getId() != null &&

                        !requestIds.contains(option.getId())

        );
    }




}
