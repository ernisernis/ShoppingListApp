package uk.ac.le.co2103.hw4;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository repo;
    private final LiveData<List<Product>> allProducts;
    private final LiveData<List<Item>> allItems;

    public ProductViewModel (Application application) {
        super(application);
        repo = new ProductRepository(application);
        allProducts = repo.getAllProducts();
        allItems = repo.getAllItems();
    }

    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    LiveData<List<Item>> getAllItems() { return allItems;}

    public void insert(Product product) {
        repo.insert(product);
    }

    public void insertItem (Item item) {
        repo.insertItem(item);
    }

    public void deleteProduct(Product product) {repo.deleteProduct(product);}

    public void deleteItem (Item item) {repo.deleteItem(item);}

}

