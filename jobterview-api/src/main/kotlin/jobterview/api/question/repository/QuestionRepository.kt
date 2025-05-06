package jobterview.api.question.repository

import jobterview.api.question.repository.custom.CustomQuestionRepository
import jobterview.domain.question.repository.QuestionJpaRepository

interface QuestionRepository : QuestionJpaRepository, CustomQuestionRepository {
}