package com.red.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "日志实体类", description = "定义了日志的各个属性，配合日志切面使用")
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = -6309732882044872298L;

    @TableId(value = "logId", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "日志ID", name = "logId", dataType = "UUID", required = true)
    private String logId;

    @TableField(value = "userId")
    @ApiModelProperty(value = "用户ID", name = "userId", dataType = "UUID", required = true)
    private String userId;

    @TableField(value = "packageName")
    @ApiModelProperty(value = "包名", name = "packageName", dataType = "String")
    private String packageName;

    @TableField(value = "executionTime")
    @ApiModelProperty(value = "执行时间", name = "executionTime", dataType = "Long")
    private Long executionTime;

    @TableField(value = "methodName")
    @ApiModelProperty(value = "方法名", name = "method", dataType = "String")
    private String methodName;

    @TableField(value = "params")
    @ApiModelProperty(value = "参数", name = "params", dataType = "String")
    private String params;

    @TableField(value = "description")
    @ApiModelProperty(value = "描述", name = "description", dataType = "String")
    private String description;

    @TableField(value = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime", dataType = "Date")
    private Date createTime;

}
