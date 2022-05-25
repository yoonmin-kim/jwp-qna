package qna.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import qna.CannotDeleteException;

@Entity
@Table(name = "question")
public class Question extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(length = 100, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    protected Question() {

    }

    public Question(String title, String contents) {
        this(null, title, contents);
    }

    public Question(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Question writeBy(User writer) {
        this.writer = writer;
        return this;
    }

    public boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        this.answers.add(answer);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public User getWriter() {
        return writer;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    public void deleteQuestion(User actionUser) throws CannotDeleteException {
        if (!this.isOwner(actionUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
        if (this.isNotSameAnswer()) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }

        this.delete();
        answersDelete();
    }

    public List<DeleteHistory> makeDeleteHistoryes() {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(DeleteHistory.questionDeleteHistory(this));
        this.getAnswers().forEach((answer -> deleteHistories.add(
                DeleteHistory.answersDeleteHistory(answer))
        ));
        return deleteHistories;
    }

    private void delete() {
        this.deleted = true;
    }

    private void answersDelete() {
        this.answers.forEach(Answer::delete);
    }

    private boolean isNotSameAnswer() {
        return this.getAnswers()
                .stream()
                .anyMatch((answer) -> !answer.isOwner(this.writer));
    }


    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                ", title='" + title + '\'' +
                ", writer=" + writer +
                ", answers=" + answers +
                '}';
    }

}
