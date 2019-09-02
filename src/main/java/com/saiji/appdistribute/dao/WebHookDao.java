package com.saiji.appdistribute.dao;


import com.saiji.appdistribute.model.WebHook;
import org.springframework.data.repository.CrudRepository;

public interface WebHookDao extends CrudRepository<WebHook, String > {

}
