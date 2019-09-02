package com.saiji.appdistribute.service;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import com.saiji.appdistribute.dao.PackageDao;
import com.saiji.appdistribute.utils.ImageUtils;
import com.saiji.appdistribute.utils.PathManager;
import com.saiji.appdistribute.utils.parser.ParserClient;
import com.saiji.appdistribute.vo.PackageViewModel;
import com.saiji.appdistribute.model.Package;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.File;

@Service
public class PackageService {

    @Resource
    private PackageDao packageDao;
    @Resource
    private PathManager pathManager;

    public Package buildPackage(String filePath) {
        Package aPackage = ParserClient.parse(filePath);
        try {
            String fileName = aPackage.getPlatform() + "." + FilenameUtils.getExtension(filePath);
            // 更新文件名
            aPackage.setFileName(fileName);

            String packagePath = PathManager.getFullPath(aPackage);
            String tempIconPath = PathManager.getTempIconPath(aPackage);
            String iconPath = packagePath + File.separator + "icon.png";
            String sourcePath = packagePath + File.separator + fileName;
            String jpgIconPath = packagePath + File.separator + "icon.jpg";

            // 拷贝图标
            ImageUtils.resize(tempIconPath, iconPath, 192, 192);
            // 生成钉钉发送所需要图片
            ImageUtils.convertPNGToJPG(iconPath, jpgIconPath, 64, 64);
            // 源文件
            FileUtils.copyFile(new File(filePath), new File(sourcePath));

            // 删除临时图标
            FileUtils.forceDelete(new File(tempIconPath));
            // 源文件
            FileUtils.forceDelete(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aPackage;
    }

    @Transactional
    public Package save(Package aPackage) {
        return this.packageDao.save(aPackage);
    }

    @Transactional
    public Package get(String id) {
        Package aPackage = this.packageDao.findById(id).get();
        return aPackage;
    }

    @Transactional
    public PackageViewModel findById(String id) {
        Package aPackage = this.packageDao.findById(id).get();
        PackageViewModel viewModel = new PackageViewModel(aPackage, this.pathManager);
        return viewModel;
    }

    @Transactional
    public void deleteById(String id) {
        Package aPackage = this.packageDao.findById(id).get();
        if (aPackage != null) {
            this.packageDao.deleteById(id);
            String path = PathManager.getFullPath(aPackage);
            PathManager.deleteDirectory(path);
        }

    }
}
