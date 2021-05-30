package uk.ac.le.co2103.hw4;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {

    @PrimaryKey(autoGenerate = true)
    public int listId;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "producturi")
    public String producturi;

    public Product(@NonNull String name, String producturi) {
        this.name = name;
        this.producturi = producturi;
    }

    public int getId() { return listId; }
    public String getProduct() {
        return name;
    }
    public String getProducturi() {
        return producturi;
    }
    public String getProductName() {
        return name;
    }
}
