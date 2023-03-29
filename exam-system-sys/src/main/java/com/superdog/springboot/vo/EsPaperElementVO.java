package com.superdog.springboot.vo;

import com.lczyfz.edp.springboot.core.entity.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "EsPaperElementVO", description = "EsPaperElementVO")
public class EsPaperElementVO extends BaseVO {

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
    @NotNull(message = "题目序号不能为空")
    private Integer orderNum;

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "EsPaperElementVO{" +
                ", element='" + element + '\'' +
                ", orderNum=" + orderNum +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
