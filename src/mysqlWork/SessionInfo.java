package mysqlWork;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="session")
public final class SessionInfo {
        @Id
        @Column(name="chatId")
    private Long chatId;
        @Column(name="score")
    private Integer score;
        @Column(name = "userQuestions")
    private String userQuestions;

    public void setChatId(Long chatId){this.chatId = chatId;}
    public Long getChatId(){return chatId;}

    public void setScore(Integer score){this.score = score;}
    public Integer getScore(){return score;}

    public void setUserQuestions(String userQuestions){this.userQuestions = userQuestions;}
    public String getUserQuestions(){return userQuestions;}
}
