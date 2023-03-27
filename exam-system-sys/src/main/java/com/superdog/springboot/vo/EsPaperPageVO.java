package com.superdog.springboot.vo;

import com.lczyfz.edp.springboot.core.vo.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "EsPaperPageVO", description = "EsPaperPageVO")
public class EsPaperPageVO extends PageVO {

    @ApiModelProperty(value = "出卷人",example = "")
    private String owner;

    @ApiModelProperty(value = "允许开始时间",example = "")
    private Date startTime;

    @ApiModelProperty(value = "强制结束时间",example = "")
    private Date endTime;

    @ApiModelProperty(value = "试卷题目数量",example = "")
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
        return "EsPaperPageVO{" +
                "owner='" + owner + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", count=" + count +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
