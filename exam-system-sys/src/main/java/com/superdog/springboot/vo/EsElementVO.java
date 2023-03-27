package com.superdog.springboot.vo;

import com.lczyfz.edp.springboot.core.entity.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "EsElementVO", description = "EsElementVO")
public class EsElementVO extends BaseVO {

    @ApiModelProperty(value = "出题人",example = "560595969486352384")
    @NotBlank(message = "出题人不能为空")
    private String owner;

    @ApiModelProperty(value = "问题",example = "1+1=?")
    @NotBlank(message = "问题不能为空")
    private String question;

    @ApiModelProperty(value = "答案",example = "2")
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
