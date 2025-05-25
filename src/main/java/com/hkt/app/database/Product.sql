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
