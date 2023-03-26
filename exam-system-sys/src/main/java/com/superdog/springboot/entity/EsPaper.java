package com.superdog.springboot.entity;

import com.lczyfz.edp.springboot.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.Date;

;

@ApiModel(value = "EsPaper", description = "EsPaper")
public class EsPaper extends DataEntity<EsPaper> {

    /**
     * 出卷人
     * 表字段 : es_paper.owner
     *
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    @ApiModelProperty(value = "出卷人")
    @NotBlank(message = "出卷人不能为空")
    private String owner;

    /**
     * 允许开始时间
     * 表字段 : es_paper.start_time
     *
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    @ApiModelProperty(value = "允许开始时间")
    @NotBlank(message = "允许开始时间不能为空")
    private Date startTime;

    /**
     * 强制结束时间
     * 表字段 : es_paper.end_time
     *
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    @ApiModelProperty(value = "强制结束时间")
    @NotBlank(message = "强制结束时间不能为空")
    private Date endTime;

    /**
     * 试卷题目数量
     * 表字段 : es_paper.count
     *
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    @ApiModelProperty(value = "试卷题目数量")
    @NotBlank(message = "试卷题目数量不能为空")
    private Integer count;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column es_paper.owner
     *
     * @return the value of es_paper.owner
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public String getOwner() {
        return owner;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column es_paper.owner
     *
     * @param owner the value for es_paper.owner
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column es_paper.start_time
     *
     * @return the value of es_paper.start_time
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column es_paper.start_time
     *
     * @param startTime the value for es_paper.start_time
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column es_paper.end_time
     *
     * @return the value of es_paper.end_time
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column es_paper.end_time
     *
     * @param endTime the value for es_paper.end_time
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column es_paper.count
     *
     * @return the value of es_paper.count
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public Integer getCount() {
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column es_paper.count
     *
     * @param count the value for es_paper.count
     * @mbggenerated Sun Mar 26 22:52:59 CST 2023
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table es_paper
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
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", count=").append(count);
        sb.append("]");
        return sb.toString();
    }
}