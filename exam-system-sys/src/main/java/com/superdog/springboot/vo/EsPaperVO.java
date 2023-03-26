package com.superdog.springboot.vo;

import com.lczyfz.edp.springboot.core.entity.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class EsPaperVO extends BaseVO {

    @ApiModelProperty(value = "出卷人")
    @NotBlank(message = "出卷人不能为空")
    private String owner;

    @ApiModelProperty(value = "允许开始时间")
    @NotBlank(message = "允许开始时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "强制结束时间")
    @NotBlank(message = "强制结束时间不能为空")
    private Date endTime;

    @ApiModelProperty(value = "试卷题目数量")
    @NotBlank(message = "试卷题目数量不能为空")
    private Integer count;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "EsPaperVO{" +
                "owner='" + owner + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", count=" + count +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
