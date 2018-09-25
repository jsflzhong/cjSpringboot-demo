/*
 * This file is generated by jOOQ.
*/
package com.cj.jooq.jooq.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class URolePermission implements Serializable {

    private static final long serialVersionUID = 1484963583;

    private String id;
    private String rid;
    private String pid;

    public URolePermission() {}

    public URolePermission(URolePermission value) {
        this.id = value.id;
        this.rid = value.rid;
        this.pid = value.pid;
    }

    public URolePermission(
        String id,
        String rid,
        String pid
    ) {
        this.id = id;
        this.rid = rid;
        this.pid = pid;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRid() {
        return this.rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("URolePermission (");

        sb.append(id);
        sb.append(", ").append(rid);
        sb.append(", ").append(pid);

        sb.append(")");
        return sb.toString();
    }
}
