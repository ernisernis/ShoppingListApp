package uk.ac.le.co2103.hw4;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemIDPK")
    public int itemIDPK;

    @ColumnInfo(name = "itemId")
    public int itemId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "unit")
    public String unit;

    public Item(int itemId, String name, int quantity, String unit) {
        this.itemId = itemId;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }


    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

}
