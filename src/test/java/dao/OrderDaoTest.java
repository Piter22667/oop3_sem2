package dao;

import org.example.shop.dao.CartDao;
import org.example.shop.dao.OrderDao;
import org.example.shop.model.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderDaoTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private OrderDao orderDao;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        orderDao = new OrderDao(mockConnection);
    }

@Test
void testGetAllOrders() throws Exception {
    // Mock the behavior of the connection, prepared statement, and result set
    when(mockConnection.prepareStatement("SELECT user_id, order_date, total_price  FROM orders")).thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true).thenReturn(false);
    when(mockResultSet.getInt("user_id")).thenReturn(1);
    when(mockResultSet.getTimestamp("order_date")).thenReturn(new java.sql.Timestamp(System.currentTimeMillis()));
    when(mockResultSet.getInt("total_price")).thenReturn(100);

    List<Orders> orders = orderDao.getAllOrdersList();

    assertEquals(1, orders.size());
    assertEquals(1, orders.get(0).getUserId());
    assertEquals(100, orders.get(0).getTotalPrice());

    verify(mockPreparedStatement, times(1)).executeQuery();
}






}
