package com.cj.httpClient.po;



import java.util.List;

public class MatrixSwitchResp{

    private static final long serialVersionUID = 1L;

    private List<SysMatrix2> list;

    private SysMatrix2 sysMatrix;

    public SysMatrix2 getSysMatrix() {
        return sysMatrix;
    }

    public void setSysMatrix(SysMatrix2 sysMatrix) {
        this.sysMatrix = sysMatrix;
    }

    public List<SysMatrix2> getList() {
        return list;
    }

    public void setList(List<SysMatrix2> list) {
        this.list = list;
    }
}
