package uk.ac.le.co2103.hw4;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert (Product product);

    @Insert
    void insertItem (Item item);

    @Query("DELETE FROM products")
    void deleteAll();

    @Delete
    public void deleteUserById(Product product);

    @Delete
    public void deleteItemById(Item item);

    @Query("SELECT * FROM products ORDER BY name ASC")
    LiveData<List<Product>> getShoppingList();

    @Query("SELECT * FROM items ORDER BY name ASC")
    LiveData<List<Item>> getItemList();
}
