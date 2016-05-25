/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.greenrobot.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class GreenDaoGenerator {
    private static String entityPath = "com.aiwan.ace.aiwan.Db.entity";

    public static void main(String[] args) throws Exception {
        int dbVersion = 1;
        Schema schema = new Schema(dbVersion, "com.aiwan.ace.aiwan.Db.dao");

        schema.enableKeepSectionsByDefault();
        addDepartment(schema);
        addUserInfo(schema);
        addGroupInfo(schema);
        addMessage(schema);
        addSessionInfo(schema);

        // todo 绝对路径,根据自己的路径设定， 例子如下
        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }

    private static void addDepartment(Schema schema){
        Entity department = schema.addEntity("DepartmentEntity");
        department.setTableName("Department");
        department.setClassNameDao("DepartmentDao");
        department.setJavaPackage(entityPath);

        department.addIdProperty().autoincrement();
        department.addIntProperty("departId").unique().notNull().index();
        department.addStringProperty("departName").unique().notNull().index();
        department.addIntProperty("priority").notNull();
        department.addIntProperty("status").notNull();

        department.addIntProperty("created").notNull();
        department.addIntProperty("updated").notNull();

        department.setHasKeepSections(true);
    }

    private static void addUserInfo(Schema schema) {
        Entity userInfo = schema.addEntity("UserEntity");
        userInfo.setTableName("UserInfo");
        userInfo.setClassNameDao("UserDao");
        userInfo.setJavaPackage(entityPath);

        userInfo.addIdProperty().autoincrement();
        userInfo.addIntProperty("peerId").unique().notNull().index();
        userInfo.addIntProperty("gender").notNull();
        userInfo.addStringProperty("mainName").notNull();
        // 这个可以自动生成pinyin
        userInfo.addStringProperty("pinyinName").notNull();
        userInfo.addStringProperty("realName").notNull();
        userInfo.addStringProperty("avatar").notNull();
        userInfo.addStringProperty("phone").notNull();
        userInfo.addStringProperty("email").notNull();
        userInfo.addIntProperty("departmentId").notNull();

        userInfo.addIntProperty("status").notNull();
        userInfo.addIntProperty("created").notNull();
        userInfo.addIntProperty("updated").notNull();

        userInfo.setHasKeepSections(true);
    }

    private static void addGroupInfo(Schema schema) {
        Entity groupInfo = schema.addEntity("GroupEntity");
        groupInfo.setTableName("GroupInfo");
        groupInfo.setClassNameDao("GroupDao");
        groupInfo.setJavaPackage(entityPath);

        groupInfo.addIdProperty().autoincrement();
        groupInfo.addIntProperty("peerId").unique().notNull();
        groupInfo.addIntProperty("groupType").notNull();
        groupInfo.addStringProperty("mainName").notNull();
        groupInfo.addStringProperty("avatar").notNull();
        groupInfo.addIntProperty("creatorId").notNull();
        groupInfo.addIntProperty("userCnt").notNull();

        groupInfo.addStringProperty("userList").notNull();
        groupInfo.addIntProperty("version").notNull();
        groupInfo.addIntProperty("status").notNull();
        groupInfo.addIntProperty("created").notNull();
        groupInfo.addIntProperty("updated").notNull();

        groupInfo.setHasKeepSections(true);
    }

    private static void addMessage(Schema schema){
        Entity message = schema.addEntity("MessageEntity");
        message.setTableName("Message");
        message.setClassNameDao("MessageDao");
        message.setJavaPackage(entityPath);

        message.implementsSerializable();
        message.addIdProperty().autoincrement();
        Property msgProId = message.addIntProperty("msgId").notNull().getProperty();
        message.addIntProperty("fromId").notNull();
        message.addIntProperty("toId").notNull();
        Property sessionPro  = message.addStringProperty("sessionKey").notNull().getProperty();
        message.addStringProperty("content").notNull();
        message.addIntProperty("msgType").notNull();
        message.addIntProperty("displayType").notNull();

        message.addIntProperty("status").notNull().index();
        message.addIntProperty("created").notNull().index();
        message.addIntProperty("updated").notNull();

        Index index = new Index();
        index.addProperty(msgProId);
        index.addProperty(sessionPro);
        index.makeUnique();
        message.addIndex(index);

        message.setHasKeepSections(true);
    }

    private static void addSessionInfo(Schema schema){
        Entity sessionInfo = schema.addEntity("SessionEntity");
        sessionInfo.setTableName("Session");
        sessionInfo.setClassNameDao("SessionDao");
        sessionInfo.setJavaPackage(entityPath);

        sessionInfo.addIdProperty().autoincrement();
        sessionInfo.addStringProperty("sessionKey").unique().notNull(); //.unique()
        sessionInfo.addIntProperty("peerId").notNull();
        sessionInfo.addIntProperty("peerType").notNull();

        sessionInfo.addIntProperty("latestMsgType").notNull();
        sessionInfo.addIntProperty("latestMsgId").notNull();
        sessionInfo.addStringProperty("latestMsgData").notNull();

        sessionInfo.addIntProperty("talkId").notNull();
        sessionInfo.addIntProperty("created").notNull();
        sessionInfo.addIntProperty("updated").notNull();

        sessionInfo.setHasKeepSections(true);
    }

}
