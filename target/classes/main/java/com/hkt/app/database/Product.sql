USE CSM;
GO

CREATE TABLE categories (
                            id INT IDENTITY(1,1) PRIMARY KEY,
                            name NVARCHAR(100) NOT NULL UNIQUE
);
GO

CREATE TABLE units (
                       id INT IDENTITY(1,1) PRIMARY KEY,
                       name NVARCHAR(50) NOT NULL UNIQUE
);
GO

CREATE TABLE products (
                          id NVARCHAR(20) PRIMARY KEY,
                          name NVARCHAR(100) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          quantity INT NOT NULL,
                          unit_id INT NOT NULL,
                          category_id INT NOT NULL,
                          status TINYINT DEFAULT 1,
                          description NVARCHAR(MAX),
                          image_url NVARCHAR(255),
                          created_at DATETIME DEFAULT GETDATE(),
                          updated_at DATETIME DEFAULT GETDATE(),
                          FOREIGN KEY (category_id) REFERENCES categories(id),
                          FOREIGN KEY (unit_id) REFERENCES units(id)
);
GO

-- Thêm dữ liệu vào bảng categories
INSERT INTO categories (name) VALUES
(N'Beverages'),
(N'Snacks'),
(N'Dairy'),
(N'Fruits'),
(N'Vegetables');

-- Thêm dữ liệu vào bảng units
INSERT INTO units (name) VALUES
                             (N'Bottle'),
                             (N'Pack'),
                             (N'Kg'),
                             (N'Piece'),
                             (N'Box');

-- Thêm dữ liệu vào bảng products
INSERT INTO products (id, name, price, quantity, unit_id, category_id, status, description, image_url)
VALUES
    ('P001', N'Coca-Cola 1.5L', 1.50, 100, 1, 1, 1, N'Carbonated soft drink', N'https://example.com/images/coca.jpg'),
    ('P002', N'Lays Chips', 0.99, 200, 2, 2, 1, N'Potato chips - classic flavor', N'https://example.com/images/lays.jpg'),
    ('P003', N'Milk Vinamilk 1L', 1.20, 150, 1, 3, 1, N'UHT whole milk', N'https://example.com/images/milk.jpg'),
    ('P004', N'Green Apple', 2.30, 80, 3, 4, 1, N'Fresh imported green apples', N'https://example.com/images/apple.jpg'),
    ('P005', N'Carrot', 1.10, 60, 3, 5, 1, N'Fresh organic carrots', N'https://example.com/images/carrot.jpg'),
    ('P006', N'Oreo Cookies', 1.75, 120, 2, 2, 1, N'Chocolate sandwich cookies', N'https://example.com/images/oreo.jpg'),
    ('P007', N'Banana', 0.80, 90, 3, 4, 1, N'Local ripe bananas', N'https://example.com/images/banana.jpg');

ALTER TABLE products
ADD CONSTRAINT FK_products_units
FOREIGN KEY (unit_id) REFERENCES units(id);


ALTER TABLE products
ADD CONSTRAINT fk_product_category
FOREIGN KEY (category_id)
REFERENCES categories(id);
