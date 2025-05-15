create table job
(
    id          uuid         not null
        primary key,
    position    varchar(255) not null,
    description varchar(255) not null,
    created_at  timestamp(6) not null,
    updated_at  timestamp(6) not null
);

comment on table job is '직업';
comment on column job.position is '포지션';
comment on column job.description is '직업 설명';
comment on column job.created_at is '등록일시';
comment on column job.updated_at is '수정일시';

alter table job
    owner to postgres;


create table question
(
    id         uuid         not null
        primary key,
    job_id     uuid         not null,
    content    text         not null,
    answer     text         not null,
    difficulty varchar(255) not null,
    created_at timestamp(6) not null
);

comment on table question is '질문';
comment on column question.job_id is '직업 ID';
comment on column question.content is '질문';
comment on column question.answer is '답변';
comment on column question.created_at is '등록일시';

alter table question
    owner to postgres;


create table mail_verification
(
    id          uuid         not null
        primary key,
    job_id      uuid         not null,
    email       varchar(255) not null,
    verify_code varchar(255) not null,
    is_verified boolean      not null,
    verified_at timestamp(6),
    expired_at  timestamp(6) not null,
    created_at  timestamp(6) not null
);

comment on table mail_verification is '메일 인증 정보';
comment on column mail_verification.job_id is '직업 ID';
comment on column mail_verification.email is '이메일';
comment on column mail_verification.verify_code is '인증 코드';
comment on column mail_verification.is_verified is '인증 여부';
comment on column mail_verification.verified_at is '인증일시';
comment on column mail_verification.expired_at is '만료일시';
comment on column mail_verification.created_at is '등록일시';

alter table mail_verification
    owner to postgres;


create table subscription
(
    id         uuid         not null
        primary key,
    email      varchar(255) not null,
    job_id     uuid         not null
        constraint fkq5xpv50j509b0c31e00w1ep17
        references job,
    created_at timestamp(6) not null
);

comment on table subscription is '구독';
comment on column subscription.email is '이메일';
comment on column subscription.created_at is '등록일시';

alter table subscription
    owner to postgres;


create table sent_question
(
    id              uuid         not null
        primary key,
    email           varchar(255) not null,
    question_id     uuid
        constraint fkmeicdtgq40noj2vo0v7m1xyax
        references question,
    subscription_id uuid
        constraint fksmr4v53boahmlua43odxd5faf
        references subscription,
    created_at      timestamp(6) not null
);

comment on table sent_question is '질문 이메일 발송 이력';
comment on column sent_question.email is '이메일';
comment on column sent_question.created_at is '등록일시';

alter table sent_question
    owner to postgres;


create table mail_token
(
    email varchar(255) not null
        primary key,
    token varchar(255) not null
        constraint ukiluqfbcg81vlsfp5sxl41wwpy
            unique
);

comment on table mail_token is '메일 토큰';
comment on column mail_token.email is '이메일';

alter table mail_token
    owner to postgres;

