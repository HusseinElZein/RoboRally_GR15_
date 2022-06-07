package exercise12;

import java.util.List;

public interface IProductService
{
    public Product getProductById(int id);
    boolean addProduct(Product p);
    public boolean updateProduct(int id, Product p);
    public boolean deleteProductById(int id);
}
