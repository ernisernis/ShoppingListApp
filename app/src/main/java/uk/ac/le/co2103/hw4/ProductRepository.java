package uk.ac.le.co2103.hw4;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {

    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;
    private LiveData<List<Item>> allItems;

    ProductRepository(Application application) {
        ProductDB db = ProductDB.getDatabase(application);
        productDao = db.productDao();
        allProducts = productDao.getShoppingList();
        allItems = productDao.getItemList();
    }
    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    LiveData<List<Item>> getAllItems() {return allItems;}

    void insert(Product product){
        ProductDB.databaseWriteExecutor.execute(() -> {
            productDao.insert(product);
        });
    }
    void insertItem(Item item) {
        ProductDB.databaseWriteExecutor.execute(() -> {
            productDao.insertItem(item);
        });
    }

    private static class deleteProductAsyncTask extends AsyncTask<Product,Void,Void> {

        private ProductDao mAsyncTaskDao;

        deleteProductAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            mAsyncTaskDao.deleteUserById(products[0]);
            return null;
        }
    }
    private static class deleteProductAsyncTask2 extends AsyncTask<Item,Void,Void> {

        private ProductDao mAsyncTaskDao;

        deleteProductAsyncTask2(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            mAsyncTaskDao.deleteItemById(items[0]);
            return null;
        }
    }

    public void deleteProduct(Product product) {
        new deleteProductAsyncTask(productDao).execute(product);
    }

    public void deleteItem (Item item) {
        new deleteProductAsyncTask2(productDao).execute(item);
    }

}
