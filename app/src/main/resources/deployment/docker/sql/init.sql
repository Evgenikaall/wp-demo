CREATE TABLE t_order_data (
    identifier uuid PRIMARY KEY,
    ordered_at TIMESTAMP NOT NULL,
    transport_status VARCHAR(50) NOT NULL,
    order_status VARCHAR(50) NOT NULL,
    original_data JSONB
);
