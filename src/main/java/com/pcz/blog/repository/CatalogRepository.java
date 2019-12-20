package com.pcz.blog.repository;

import com.pcz.blog.domain.Catalog;
import com.pcz.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author picongzhi
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    /**
     * 根据用户查询分类
     *
     * @param user 用户
     * @return List<Catalog>
     */
    List<Catalog> findByUser(User user);

    /**
     * 根据用户和名字查询分类
     *
     * @param user 用户
     * @param name 名字
     * @return List<Catalog>
     */
    List<Catalog> findByUserAndName(User user, String name);
}
