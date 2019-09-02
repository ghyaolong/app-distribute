package com.saiji.appdistribute.dao;

import org.springframework.data.repository.CrudRepository;
import com.saiji.appdistribute.model.Package;

public interface PackageDao extends CrudRepository <Package, String > {

}
