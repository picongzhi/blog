package com.pcz.blog.service;

import com.pcz.blog.domain.Catalog;
import com.pcz.blog.domain.User;

import java.util.List;

/**
 * @author picongzhi
 */
public interface CatalogService {
    /**
     * 保存catalog
     *
     * @param catalog catalog
     * @return Catalog
     */
    Catalog saveCatalog(Catalog catalog);

    /**
     * 根据id删除catalog
     *
     * @param id id
     */
    void removeCatalog(Long id);

    /**
     * 根据id获取catalog
     *
     * @param id id
     * @return Catalog
     */
    Catalog getCatalogById(Long id);

    /**
     * 根据用户获取分类列表
     *
     * @param user 用户
     * @return List<Catalog>
     */
    List<Catalog> listCatalogs(User user);
}
