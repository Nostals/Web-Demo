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
@TableName("user")
public class UserPO {
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    @TableField("name")
    private String name;
    @TableField("password")
    private String password;
}
