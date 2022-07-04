package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerDto;
import com.mysite.sbb.user.SiteUserDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class QuestionDto {
  private Integer id;
  private String subject;
  private String content;
  private LocalDateTime createDate;
  private List<AnswerDto> answerList;
  private SiteUserDto author;
  private LocalDateTime modifyDate;
  private Set<SiteUserDto> voter;
}
