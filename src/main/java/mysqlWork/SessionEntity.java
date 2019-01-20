package mysqlWork;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "session", schema = "botoop", catalog = "")
public class SessionEntity {
    private long chatId;
    private Integer score;
    private String userQuestions;

    public SessionEntity(long chatId, Integer score, String userQuestions) {
        this.chatId = chatId;
        this.score = score;
        this.userQuestions = userQuestions;
    }

    @Id
    @Column(name = "chatId", nullable = false)
    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Basic
    @Column(name = "score", nullable = true)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "userQuestions", nullable = true, length = 255)
    public String getUserQuestions() {
        return userQuestions;
    }

    public void setUserQuestions(String userQuestions) {
        this.userQuestions = userQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionEntity that = (SessionEntity) o;
        return chatId == that.chatId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(chatId);
    }
}
