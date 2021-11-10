# JPA
## 1단계-엔티티 매핑
### 요구사항
- [x] `@DataJpaTest`를 사용하여 학습 테스트를 해 본다.
- [x] answer Entity에대한 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성
```SQL
create table answer
(
    id          bigint generated by default as identity,
    contents    clob,
    created_at  timestamp not null,
    deleted     boolean   not null,
    question_id bigint,
    updated_at  timestamp,
    writer_id   bigint,
    primary key (id)
)
```
- [x] delete_history Entity에대한 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성
```SQL
create table delete_history
(
    id            bigint generated by default as identity,
    content_id    bigint,
    content_type  varchar(255),
    create_date   timestamp,
    deleted_by_id bigint,
    primary key (id)
)
```
- [x] question Entity에대한 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성
```SQL
create table question
(
    id         bigint generated by default as identity,
    contents   clob,
    created_at timestamp    not null,
    deleted    boolean      not null,
    title      varchar(100) not null,
    updated_at timestamp,
    writer_id  bigint,
    primary key (id)
)
```
- [x] user Entity에대한 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성
```SQL
create table user
(
    id         bigint generated by default as identity,
    created_at timestamp   not null,
    email      varchar(50),
    name       varchar(20) not null,
    password   varchar(20) not null,
    updated_at timestamp,
    user_id    varchar(20) not null,
    primary key (id)
)

alter table user
    add constraint UK_a3imlf41l37utmxiquukk8ajc unique (user_id)
```

## 2단계- 연관 관계 매핑
### 요구사항
- [x] 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.
- [x] answer 엔티티는 question엔티티를 참조하고 question의 id값을 외래키로 사용한다.
```sql
alter table answer
    add constraint fk_answer_to_question
        foreign key (question_id)
            references question
```
- [x] answer 엔티티는 user엔티티를 참조하고 user의 id값을 외래키로 사용한다.
```sql
alter table answer
    add constraint fk_answer_writer
        foreign key (writer_id)
            references user
```
- [x] delete_history 엔티티는 user엔티티를 참조하고 user의 id값을 외래키로 사용한다.
```sql
alter table delete_history
    add constraint fk_delete_history_to_user
        foreign key (deleted_by_id)
            references user
```
- [x] question 엔티티는 user엔티티를 참조하고 user의 id값을 외래키로 사용한다.
```sql
alter table question
    add constraint fk_question_writer
        foreign key (writer_id)
            references user
```