# language: es
@smoke
Característica: Inicio de sesión en SauceDemo

  Como usuario de SauceDemo
  Quiero poder iniciar sesión con mis credenciales
  Para acceder al inventario de productos

  Escenario: Login exitoso con usuario estándar
    Dado que el usuario navega a la página de login
    Cuando ingresa el usuario "standard_user" y la contraseña "secret_sauce"
    Entonces debe ser redirigido al inventario de productos

  Escenario: Login fallido con usuario bloqueado
    Dado que el usuario navega a la página de login
    Cuando ingresa el usuario "locked_out_user" y la contraseña "secret_sauce"
    Entonces debe ver el mensaje de error "Sorry, this user has been locked out"

  Escenario: Login con campos vacíos
    Dado que el usuario navega a la página de login
    Cuando no ingresa ningún dato
    Entonces debe ver el mensaje de error "Username is required"
