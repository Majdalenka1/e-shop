package com.company;

import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

public interface GoodsMethods {

    /**
     * This method loads an item with given id
     * @param id - id of the item which we want to load
     * @return
     */
    @Select("SELECT * FROM items WHERE id = #{id}")
    Item loadItemById(Integer id);

    /**
     * This method deletes all items that are not in stock
     */
    @Delete("DELETE FROM items WHERE numberInStock = 0")
    void deleteAllOutOfStockItems();

    /**
     * This method loads all items that are in stock
     * @return
     */
    @Select("SELECT * FROM items")
    List<Item> loadAllAvailableItems();

    /**
     * This method saves the given item
     * @param item - item to be saved
     */
    @Insert("INSERT INTO items(id,partNo,serialNo,name,description,numberInStock,price) values(#{id},#{partNo},#{serialNo},#{name},#{description},#{numberInStock},#{price})")
    void saveItem(Item item);

    /**
     * This method updates a price of an item
     * @param id - id of an item which price is to be updated
     * @param newPrice - new price
     */
    @Update("UPDATE items SET price = #{param2} WHERE id = #{param1}")
    void updatePrice(Integer id, BigDecimal newPrice);

}