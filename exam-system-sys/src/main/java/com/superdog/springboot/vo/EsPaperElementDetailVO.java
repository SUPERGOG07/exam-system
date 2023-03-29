package com.superdog.springboot.vo;

import com.lczyfz.edp.springboot.core.entity.BaseVO;

public class EsPaperElementDetailVO extends BaseVO {

    private String owner;

    private String question;

    private String answer;

    private Integer order;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "EsPaperElementDetailVo{" +
                "owner='" + owner + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", order=" + order +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
