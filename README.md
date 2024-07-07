# Proyecto de Gestión de Proveedores, Pedidos y Productos
Este es un proyecto de ejemplo para la gestión de proveedores, pedidos y productos, utilizando Spring Boot, JPA y JWT para autenticación y autorización.

## Endpoints

### Autenticación
- `POST /auth/login` - Autentica un usuario y devuelve un token JWT.

### Proveedores
- `GET /api/providers` - Obtiene todos los proveedores.
- `POST /api/providers` - Crea un nuevo proveedor.
- `PUT /api/providers/{id}` - Actualiza un proveedor existente.
- `DELETE /api/providers/{id}` - Elimina un proveedor.

### Pedidos
- `GET /api/orders` - Obtiene todos los pedidos.
- `POST /api/orders` - Crea un nuevo pedido.
- `PUT /api/orders/{id}` - Actualiza un pedido existente.
- `DELETE /api/orders/{id}` - Elimina un pedido.

### Productos
- `GET /api/products` - Obtiene todos los productos.
- `POST /api/products` - Crea un nuevo producto.
- `PUT /api/products/{id}` - Actualiza un producto existente.
- `DELETE /api/products/{id}` - Elimina un producto.
- `GET /api/products/find/{id}` - Encuentra un producto por su ID.

## Documentación

### Proveedores
- `http://localhost:8081/swagger-ui/index.html` - Documentación de la API para la gestión de proveedores.

### Pedidos
- `http://localhost:8082/swagger-ui/index.html` - Documentación de la API para la gestión de pedidos.

### Productos
- `http://localhost:8083/swagger-ui/index.html` - Documentación de la API para la gestión de productos.

