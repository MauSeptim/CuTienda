
# Proyecto CuTienda

CuTienda es una aplicación de pedidos en línea diseñada exclusivamente para la comunidad universitaria. Inspirada en la experiencia de plataformas como UberEats, Didi etc. CuTienda facilita a los estudiantes realizar pedidos o ventas de alimentos y otros productos de las tiendas y restaurantes dentro del campus. Nuestro objetivo es mejorar la vida diaria en la universidad, ofreciendo una solución rápida, conveniente y eficiente para acceder a tus comidas favoritas y productos esenciales sin salir del campus.

## Microservicios

### Autenticación

Función: Manejar el registro e inicio de sesión de los usuarios. 
- Base de datos no relacional mongo
- java 


|Iduser	|Usuario	|Contraseña	|Tipo-de-usuario|
|:-|:-|:-|:-|
|Int	|Varchar|	Varchar|	int|


### Gestión de usuarios

Función: Almacenar y gestionar perfiles de los vendedores, incluyendo su información básica y detalles de contacto. 
- Java
- Mysql 


|Iduser|	Nombre|	Correo|	Telefono|	Puntuacion|	Tipo-de-usuario|	Foto|
|:-|:-|:-|:-|:-|:-|:-|
|Int |	Varchar|	Varchar|	Int|	Int|	int|	varchar|


### Gestión de productos

Función: Permitir a los vendedores subir productos, agregar descripciones, imágenes, precios, y etiquetas por tipo de producto. 

|Idprod	|Nombre|	Descripcion|	Foto|	Precio|	Etiqueta|	idproveedor|
|:-|:-|:-|:-|:-|:-|:-|
|Int|	Varchar|	Varchar|	Varchar|	Float|	varchar|	int|


|Idproveedor|	Nombre|	Empresa|
|:-|:-|:-|
|Int|	Varchar|	Varchar|

### Servicio de Ubicación: 

  Función: Permitir a los vendedores compartir su ubicación actual o la ubicación de su punto de venta.
  Administrar permiso de hardware para ubicación
  - Java
  - Mysql
  - api de google 

  |lugarid|	Coordenada|
  |:-|:-|
  |Int|	varchar|

### Servicio de Filtrado: 
Función: Gestionar las etiquetas y categorías que se pueden asignar a los productos para facilitar la búsqueda y organización. 

|tipoid|nombreProducto|CategoriaTipo|categoriaPrecio|categoriaVendedor|
|:-|:-|:-|:-|:-|
|Int |varchar	|varchar	|float	|varchar |


### Servicio de Notificaciones: 
Función: Notificar a los vendedores y compradores sobre interacciones relevantes (nuevas compras, preguntas de clientes, etc.). 
- Python 


