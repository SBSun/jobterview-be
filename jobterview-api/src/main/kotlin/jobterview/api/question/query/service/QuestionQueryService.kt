package jobterview.api.question.query.service

import jobterview.api.question.query.service.usecase.QuestionQueryUseCase
import jobterview.api.question.repository.QuestionRepository
import jobterview.api.question.response.QuestionDetailResponse
import jobterview.api.question.response.QuestionResponse
import jobterview.api.question.vo.QuestionFilter
import jobterview.domain.question.Question
import jobterview.domain.question.exception.QuestionException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class QuestionQueryService (
    private val questionRepository: QuestionRepository
) : QuestionQueryUseCase {

    /**
     * 질문 ID로 질문 정보를 조회합니다.
     *
     * @param id 질문 ID
     * @return 질문 정보
     */
    override fun getDetail(id: UUID): QuestionDetailResponse {
        return QuestionDetailResponse(getById(id))
    }

    /**
     * 질문 목록을 조회합니다.
     *
     * @param filter 조회 조건
     * @param pageable 페이징 정보
     * @return 질문 정보 목록
     */
    override fun getQuestions(filter: QuestionFilter, pageable: PageRequest): Page<QuestionResponse> {
        return questionRepository.getQuestions(filter, pageable)
    }

    /**
     * 질문 ID로 질문 엔티티를 조회합니다.
     *
     * @param id 질문 ID
     * @return 질문 엔티티
     */
    private fun getById(id: UUID): Question {
        return questionRepository.findById(id)
            .orElseThrow { QuestionException.notFound() }
    }
}