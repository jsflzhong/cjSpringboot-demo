package com.cj.httpClient.po;

/**
 * 矩阵开关
 *
 * @Author Zzwei
 * @Create 2018 - 10 - 15 11:17
 */
public class SysMatrix2 {
    private Long id;

    private String type = "";

    private String name;

    private String key1 = "";

    private String key2 = "";

    private String key3 = "";

    private String key4 = "";

    private String key5 = "";

    private String managerId;

    private String value;

    private String option1;

    private String option2;

    private String comment;

    private String createUser;

    private String createDate;

    private String updateUser;

    private String updateDate;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    private String mode;

    public Long getId() {
        return id;
    }

    public SysMatrix2 setId(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public SysMatrix2 setType(String type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public SysMatrix2 setName(String name) {
        this.name = name;
        return this;
    }

    public String getKey1() {
        return key1;
    }

    public SysMatrix2 setKey1(String key1) {
        this.key1 = key1;
        return this;
    }

    public String getKey2() {
        return key2;
    }

    public SysMatrix2 setKey2(String key2) {
        this.key2 = key2;
        return this;
    }

    public String getKey3() {
        return key3;
    }

    public SysMatrix2 setKey3(String key3) {
        this.key3 = key3;
        return this;
    }

    public String getKey4() {
        return key4;
    }

    public SysMatrix2 setKey4(String key4) {
        this.key4 = key4;
        return this;
    }

    public String getKey5() {
        return key5;
    }

    public SysMatrix2 setKey5(String key5) {
        this.key5 = key5;
        return this;
    }

    public String getManagerId() {
        return managerId;
    }

    public SysMatrix2 setManagerId(String managerId) {
        this.managerId = managerId;
        return this;
    }

    public String getValue() {
        return value;
    }

    public SysMatrix2 setValue(String value) {
        this.value = value;
        return this;
    }

    public String getOption1() {
        return option1;
    }

    public SysMatrix2 setOption1(String option1) {
        this.option1 = option1;
        return this;
    }

    public String getOption2() {
        return option2;
    }

    public SysMatrix2 setOption2(String option2) {
        this.option2 = option2;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public SysMatrix2 setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getCreateUser() {
        return createUser;
    }

    public SysMatrix2 setCreateUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public SysMatrix2 setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public SysMatrix2 setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public SysMatrix2 setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    @Override
    public String toString() {
        return "id["+getId()+"],"
                + getKeyInfo()+","
                +"value["+getValue()+"],"
                +"option1["+getOption1()+"],"
                +"option2["+getOption2()+"]"
                ;
    }

    protected String getKeyInfo(){
        return "type["+getType()+"],"
                +"key1["+getKey1()+"],"
                +"key2["+getKey2()+"],"
                +"key3["+getKey3()+"],"
                +"key4["+getKey4()+"],"
                +"key5["+getKey5()+"],"
                +"managerId["+getManagerId()+"]";
    }
}
