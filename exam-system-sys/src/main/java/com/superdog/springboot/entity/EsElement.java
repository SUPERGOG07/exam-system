package com.superdog.springboot.entity;

import com.lczyfz.edp.springboot.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

;

@ApiModel(value = "EsElement", description = "EsElement")
public class EsElement extends DataEntity<EsElement> {

    /**
     * 出题人
     * 表字段 : es_element.owner
     *
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    @ApiModelProperty(value = "出题人")
    @NotBlank(message = "出题人不能为空")
    private String owner;

    /**
     * 问题
     * 表字段 : es_element.question
     *
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    @ApiModelProperty(value = "问题")
    @NotBlank(message = "问题不能为空")
    private String question;

    /**
     * 答案
     * 表字段 : es_element.answer
     *
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    @ApiModelProperty(value = "答案")
    @NotBlank(message = "答案不能为空")
    private String answer;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column es_element.owner
     *
     * @return the value of es_element.owner
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public String getOwner() {
        return owner;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column es_element.owner
     *
     * @param owner the value for es_element.owner
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column es_element.question
     *
     * @return the value of es_element.question
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public String getQuestion() {
        return question;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column es_element.question
     *
     * @param question the value for es_element.question
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column es_element.answer
     *
     * @return the value of es_element.answer
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column es_element.answer
     *
     * @param answer the value for es_element.answer
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table es_element
     *
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createBy=").append(createBy);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", remarks=").append(remarks);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", owner=").append(owner);
        sb.append(", question=").append(question);
        sb.append(", answer=").append(answer);
        sb.append("]");
        return sb.toString();
    }
}