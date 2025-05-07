package jobterview.domain.mail

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "mail_token")
@Comment("메일 토큰")
class MailToken (

    @Id
    @Column(nullable = false)
    @Comment("이메일")
    val email: String,

    @Column(nullable = false, unique = true)
    val token: String
)