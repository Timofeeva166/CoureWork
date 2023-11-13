package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }


    @SneakyThrows
    public static void cleanDatabase() {
        runner.execute(getConnection(), "DELETE FROM credit_request_entity");
        runner.execute(getConnection(), "DELETE FROM order_entity");
        runner.execute(getConnection(), "DELETE FROM payment_entity");
    }
    @SneakyThrows
    public static String getPaymentStatus() {
        var status = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        var conn = getConnection();
        return runner.query(conn, status, new ScalarHandler<String>());
    }

    @SneakyThrows
    public static int  getPaymentAmount() {
        var amount = "SELECT amount FROM payment_entity ORDER BY created DESC LIMIT 1";
        return runner.query(getConnection(), amount, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getCreditRequestStatus() {
        var status = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        return runner.query(getConnection(), status, new ScalarHandler<>());
    }
}
