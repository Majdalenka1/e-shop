package com.company;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            CreateDB createDB = session.getMapper(CreateDB.class);
            createDB.createTable();

            GoodsMethods mapper = session.getMapper(GoodsMethods.class);
            mapper.saveItem(new Item(10, "444-555", "123", "Christmas Piggie", "The most beautiful piggie in the world", 1, new BigDecimal(999999.90)));
            mapper.saveItem(new Item(11, "444-666", "456", "Pinky Unicorn", "Almost always available", 1000, new BigDecimal(0.90)));
            mapper.saveItem(new Item(12, "333-555", "567", "Hungry Dogie", "Always looking for the bone, but sold out", 0, new BigDecimal(333)));
            printState(session);
            //piggie was too cheap, update price
            mapper.updatePrice(10, new BigDecimal(99999999.90));
            printState(session);
            //dogie is out stock, purge
            mapper.deleteAllOutOfStockItems();
            printState(session);
            //get piggie
            Item item = mapper.loadItemById(10);
            System.out.println(item);

        }
    }

    private static void printState(SqlSession session){
        GoodsMethods mapper = session.getMapper(GoodsMethods.class);
        List<Item> items = mapper.loadAllAvailableItems();
        for (Item item: items) {
            System.out.println("item = " + item);
        }
        System.out.println("####################");
    }
}
