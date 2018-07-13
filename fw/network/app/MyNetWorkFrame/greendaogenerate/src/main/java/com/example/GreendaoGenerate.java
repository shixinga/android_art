package com.example;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.DaoGenerator;
public class GreendaoGenerate {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.shixing.filedownload.db"); //包名
        Entity entity = schema.addEntity("DownloadEntity"); //表名
        entity.addLongProperty("start_position");
        entity.addLongProperty("end_position");
        entity.addLongProperty("progress_position");
        entity.addStringProperty("download_url");
        entity.addIntProperty("thread_id");
        entity.addIdProperty().autoincrement();
        try {
            new DaoGenerator().generateAll(schema, "greendaogenerate/src/main/java/dbfilegenerated");  //项目名/目录名
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
