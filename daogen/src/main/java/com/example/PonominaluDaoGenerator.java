package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class PonominaluDaoGenerator {

    public static void main(String[] args) throws Exception{
        Schema schema = new Schema(1, "com.ponominalu.nvv.ponominalutest.dao");

        Entity category = schema.addEntity("Category");
        category.addLongProperty("id").primaryKey().autoincrement();
        category.addStringProperty("title");
        category.addStringProperty("alias");
        category.addLongProperty("events_count");

        Entity event = schema.addEntity("Event");
        event.addLongProperty("id").primaryKey().autoincrement();
        event.addStringProperty("title");
        event.addLongProperty("sub_id");
        event.addStringProperty("date");
        event.addStringProperty("end_date");
        event.addStringProperty("image_name");
        event.addByteArrayProperty("image");
        event.addLongProperty("min_price");
        event.addLongProperty("max_price");
        event.addLongProperty("count");
        event.addStringProperty("description");
        event.addLongProperty("category_id");

        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }
}
