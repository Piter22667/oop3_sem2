package dao;

import org.example.shop.dao.CartDao;
import org.example.shop.model.Cart;
import org.example.shop.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartDaoTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private CartDao cartDao;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        cartDao = new CartDao(mockConnection);
    }

    @Test
    void testGetProductsFromCart() throws Exception {
        int userId = 1;

        when(mockConnection.prepareStatement("SELECT c.user_id, c.product_id, c.quantity, c.added_at, p.name AS product_name, p.price " +
                "FROM cart c JOIN products p ON c.product_id = p.id WHERE c.user_id = ?"))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("user_id")).thenReturn(userId);
        when(mockResultSet.getInt("product_id")).thenReturn(101);
        when(mockResultSet.getInt("quantity")).thenReturn(2);
        when(mockResultSet.getTimestamp("added_at")).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(mockResultSet.getString("product_name")).thenReturn("Test Product");
        when(mockResultSet.getInt("price")).thenReturn(200);

        List<Cart> cartList = cartDao.getProductsFromCart(userId);

        assertNotNull(cartList);
        assertEquals(1, cartList.size());
        assertEquals(101, cartList.get(0).getProductId());
        assertEquals(2, cartList.get(0).getQuantity());
        verify(mockPreparedStatement, times(1)).setInt(1, userId);
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetCartUsers() throws Exception {
        when(mockConnection.prepareStatement("SELECT DISTINCT c.user_id, u.username FROM public.cart c JOIN public.users u ON c.user_id = u.id"))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("user_id")).thenReturn(1);
        when(mockResultSet.getString("username")).thenReturn("TestUser");

        List<Users> users = cartDao.getCartUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(1, users.get(0).getId());
        assertEquals("TestUser", users.get(0).getUsername());
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    void testAddToCart() throws Exception {
        int userId = 1;
        int productId = 101;
        int quantity = 2;

        when(mockConnection.prepareStatement("SELECT * FROM cart WHERE user_id = ? AND product_id = ?"))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        cartDao.addToCart(userId, productId, quantity);

        verify(mockPreparedStatement, times(1)).setInt(1, userId);
        verify(mockPreparedStatement, times(1)).setInt(2, productId);
        verify(mockPreparedStatement, times(1)).setInt(3, quantity);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
}