package com.saiji.appdistribute.utils.parser;

import com.saiji.appdistribute.model.Package;

public interface PackageParser {
    // 解析包
    public Package parse(String filePath);
}
