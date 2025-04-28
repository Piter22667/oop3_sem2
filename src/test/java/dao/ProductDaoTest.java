package dao;

import org.example.shop.dao.ProductDao;
import org.example.shop.model.Products;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductDaoTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private ProductDao productDao;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        productDao = new ProductDao(mockConnection);
    }

    @Test
    void testGetAllProducts() throws Exception {
        when(mockConnection.prepareStatement("SELECT * FROM products")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Test Product");
        when(mockResultSet.getString("description")).thenReturn("Test Description");
        when(mockResultSet.getInt("price")).thenReturn(100);
        when(mockResultSet.getInt("quantity")).thenReturn(10);
        when(mockResultSet.getString("image_url")).thenReturn("test.jpg");

        var products = productDao.getAllProducts();

        assertEquals(1, products.size());
        assertEquals("Test Description", products.get(0).getDescription());
        verify(mockPreparedStatement, times(1)).executeQuery();
    }


    @Test
    void testGetProductById() throws Exception {
        int productId = 1;

        when(mockConnection.prepareStatement("SELECT * FROM products WHERE id = ?")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(productId);
        when(mockResultSet.getString("name")).thenReturn("Test Product");
        when(mockResultSet.getString("description")).thenReturn("Test Description");
        when(mockResultSet.getInt("price")).thenReturn(100);
        when(mockResultSet.getInt("quantity")).thenReturn(10);
        when(mockResultSet.getString("image_url")).thenReturn("https://test2.jpg");

        var product = productDao.getProductById(productId);

        assertNotNull(product);
        assertEquals(productId, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Test Description", product.getDescription());
        assertEquals(100, product.getPrice());
        assertEquals(10, product.getQuantity());
        assertEquals("https://test2.jpg", product.getImage_url());

        verify(mockPreparedStatement, times(1)).setInt(1, productId);
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    void testCreateProduct() throws Exception {
        Products product = new Products(0, "Test Product", "Test Description", 100, 10, "test.jpg");

        when(mockConnection.prepareStatement("INSERT INTO products (name, description, price, quantity, image_url) VALUES (?, ?, ?, ?, ?)"))
                .thenReturn(mockPreparedStatement);

        productDao.createProduct(product);
        //Перевіримо, що метод викликається з правильними параметрами при додаванні продуктуу
        verify(mockPreparedStatement, times(1)).setString(1, product.getName());
        verify(mockPreparedStatement, times(1)).setString(2, product.getDescription());
        verify(mockPreparedStatement, times(1)).setInt(3, product.getPrice());
        verify(mockPreparedStatement, times(1)).setInt(4, product.getQuantity());
        verify(mockPreparedStatement, times(1)).setString(5, product.getImage_url());
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testDeleteProduct() throws Exception {
        int productId = 1;

        when(mockConnection.prepareStatement("DELETE FROM products WHERE id = ?")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDao.deleteProduct(productId);

        verify(mockPreparedStatement, times(1)).setInt(1, productId);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }



}