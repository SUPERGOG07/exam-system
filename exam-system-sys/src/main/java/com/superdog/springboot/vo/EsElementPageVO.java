package com.superdog.springboot.vo;

import com.lczyfz.edp.springboot.core.vo.PageVO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class EsElementPageVO extends PageVO {

    @ApiModelProperty(value = "出题人")
    @NotBlank(message = "出题人不能为空")
    private String owner;

    @ApiModelProperty(value = "问题")
    @NotBlank(message = "问题不能为空")
    private String question;

    @ApiModelProperty(value = "答案")
    @NotBlank(message = "答案不能为空")
    private String answer;

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

    @Override
    public String toString() {
        return "EsElementVO{" +
                "owner='" + owner + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
