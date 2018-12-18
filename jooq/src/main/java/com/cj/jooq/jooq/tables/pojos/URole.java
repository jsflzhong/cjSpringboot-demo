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
public class URole implements Serializable {

    private static final long serialVersionUID = 970285260;

    private String id;
    private String name;
    private String role;

    public URole() {}

    public URole(URole value) {
        this.id = value.id;
        this.name = value.name;
        this.role = value.role;
    }

    public URole(
        String id,
        String name,
        String role
    ) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("URole (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(role);

        sb.append(")");
        return sb.toString();
    }
}