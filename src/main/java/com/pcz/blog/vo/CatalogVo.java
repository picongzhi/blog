package com.pcz.blog.vo;

import com.pcz.blog.domain.Catalog;

import java.io.Serializable;

/**
 * @author picongzhi
 */
public class CatalogVo implements Serializable {
    private String username;
    private Catalog catalog;

    public CatalogVo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
