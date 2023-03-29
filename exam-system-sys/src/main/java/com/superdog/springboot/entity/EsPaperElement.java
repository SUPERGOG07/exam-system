package com.superdog.springboot.entity;

import com.lczyfz.edp.springboot.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

;

/**
 * @author superdog
 * @version 2023-3-27
 */
@ApiModel(value = "EsPaperElement", description = "EsPaperElement")
public class EsPaperElement extends DataEntity<EsPaperElement> {

    /**
     * 试卷编号
     * 表字段 : es_paper_element.paper
     *
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    @ApiModelProperty(value = "试卷编号")
    @NotBlank(message = "试卷编号不能为空")
    private String paper;

    /**
     * 题目编号
     * 表字段 : es_paper_element.element
     *
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    @ApiModelProperty(value = "题目编号")
    @NotBlank(message = "题目编号不能为空")
    private String element;

    /**
     * 题目序号
     * 表字段 : es_paper_element.order_num
     *
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    @ApiModelProperty(value = "题目序号")
    @NotBlank(message = "题目序号不能为空")
    private Integer orderNum;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column es_paper_element.paper
     *
     * @return the value of es_paper_element.paper
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public String getPaper() {
        return paper;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column es_paper_element.paper
     *
     * @param paper the value for es_paper_element.paper
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public void setPaper(String paper) {
        this.paper = paper == null ? null : paper.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column es_paper_element.element
     *
     * @return the value of es_paper_element.element
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public String getElement() {
        return element;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column es_paper_element.element
     *
     * @param element the value for es_paper_element.element
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public void setElement(String element) {
        this.element = element == null ? null : element.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column es_paper_element.order_num
     *
     * @return the value of es_paper_element.order_num
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column es_paper_element.order_num
     *
     * @param orderNum the value for es_paper_element.order_num
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table es_paper_element
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
        sb.append(", paper=").append(paper);
        sb.append(", element=").append(element);
        sb.append(", orderNum=").append(orderNum);
        sb.append("]");
        return sb.toString();
    }
}