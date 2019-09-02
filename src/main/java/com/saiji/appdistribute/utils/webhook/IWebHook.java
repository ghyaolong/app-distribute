package com.saiji.appdistribute.utils.webhook;


import com.saiji.appdistribute.model.App;
import com.saiji.appdistribute.utils.PathManager;

public interface IWebHook {
    void sendMessage(App app, PathManager pathManager);
}
