package com.saiji.appdistribute.dao;

import com.saiji.appdistribute.model.App;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AppDao extends CrudRepository<App, String> {

    @Query("select a from App a where a.bundleID=:bundleID and a.platform=:platform")
    public App get(@Param("bundleID") String bundleID, @Param("platform") String platform);

    @Query("select a from App a where a.shortCode=:shortCode")
    public App findByShortCode(@Param("shortCode") String shortCode);

    @Override
    @Query("select a from App a order by a.currentPackage.createTime desc ")
    Iterable<App> findAll();
}
