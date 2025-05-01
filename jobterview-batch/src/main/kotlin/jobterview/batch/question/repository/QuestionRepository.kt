package jobterview.batch.question.repository

import jobterview.batch.question.repository.custom.CustomQuestionRepository
import jobterview.domain.question.repository.QuestionJpaRepository

interface QuestionRepository : QuestionJpaRepository, CustomQuestionRepository {
}