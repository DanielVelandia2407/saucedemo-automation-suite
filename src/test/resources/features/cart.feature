# language: es
@regression
Característica: Gestión del carrito de compras en SauceDemo

  Como usuario autenticado de SauceDemo
  Quiero poder agregar productos al carrito
  Para poder realizar una compra

  Escenario: Agregar producto al carrito
    Dado que el usuario ha iniciado sesión como "standard_user"
    Cuando agrega el primer producto al carrito
    Y navega al carrito
    Entonces el producto debe aparecer en el carrito

  Escenario: Verificar contador del carrito
    Dado que el usuario ha iniciado sesión como "standard_user"
    Cuando agrega el primer producto al carrito
    Entonces el contador del carrito debe mostrar 1
