package com.example.logintest.data_number;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("msg")
public class MsgPO {
    @TableId(value = "msgid",type = IdType.AUTO)
    private String msgid;
    @TableField("time")
    private String time;
    @TableField("userid")
    private String userid;
    @TableField("msg")
    private String msg;
}
