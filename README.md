# **Taller 7 - Aplicación Destribuida Segura en todos sus frentes**
### *Hecho por Ricardo Pulido Renteria*
---

En este taller, se trabaja con Spark y Docker para crear un servicio sencillo de login creando nuestras propias credenciales con el fin de presentar una aplicación segura y manejando el protocolo HTTPS respectivamente. Adicional a esto, se despliega el servicio en una instancia de EC2 de AWS.

## **Descarga y ejecución**

Para poder ejecutar este proyecto, el cual se ejecutará en tu ambiente local por fines de desarrollo y pruebas, debes contar con algunos elementos que serán indicados a continuación.


## **Prerequisitos**

La ejecución de este proyecto requiere de:
- `Java (versión 17 o superior)`
- `Maven (3.8.1 o superior)`
- `Docker Desktop`
- `Conexión a internet`


## **Instalación**

Para poder trabajar con el proyecto hay 2 opciones, descargarlo desde GitHub o descargar la imagen del proyecto de Docker Hub.

**_Nota:_** Ambas requieren de que tengas corriendo la aplicación de DockerHub.

### GitHub
Sí se descarga desde GitHub, primero se clona el repositorio en su máquina con el comando
```bash
git clone https://github.com/RicardoPR17/arep-taller7.git
```
o puede descargarlo en formato zip y descomprimirlo. Luego, se deben ejecutar los siguientes comandos:
  1. Acceder al directorio del proyecto usando el comando 
```bash
cd arep-taller7
```
  2. Una vez dentro del directorio del proyecto general, se necesita generar la carpeta _target_ del proyecto. Para esto, se ejecuta el siguiente comando
```bash
mvn clean install
```

Hecho esto, estaremos listos para ejecutar el proyecto.    

## **Ejecución**

Para ejecutar nuestro proyecto, debemos abrir 2 terminales de comandos. En una ejecutaremos el primer comando y en otra el segundo de los presentados a continuación

```bash
java -cp "target/classes;target/dependency/*" edu.escuelaing.arem.ase.app.HelloService
java -cp "target/classes;target/dependency/*" edu.escuelaing.arem.ase.app.UserDB
```

Hecho esto, podemos acceder a la ruta https://localhost:5000/index.html.

**Nota:** Si nos aparece un mensaje diciendo que no es seguro, debemos ignorar este aviso y darle a continuar aceptando el riesgo o simplemente continuar. Esto porque las credenciales las firmamos nosotros mismos y el navegador no confía en esas llaves dado que le son desconocidas. No tendremos riesgo de infectar nuestra máquina:

## **Uso**

Accedemos desde la ruta https://localhost:5000/index.html y veremos una página con login básico. Aquí, podemos escribir nuestro usuario y contraseña para luego dar clic en el botón de _login_. Esto enviará nuestros datos al servicio y validará si estamos registrados, si es así verifica la contraseña y según eso nos dirá si fue un login satisfactorio o si hubo problemas.


## **Diseño**

Se manejaron 2 grupos de clases, uno para presentar el login cuyas clases son [HelloService](src/main/java/edu/escuelaing/arem/ase/app/HelloService.java) y [SecureUrlReader](src/main/java/edu/escuelaing/arem/ase/app/SecureUrlReader.java) y el otro grupo se encarga de validar el inicio de sesión que son [Cypher](src/main/java/edu/escuelaing/arem/ase/app/Cypher.java) y [UserDB](src/main/java/edu/escuelaing/arem/ase/app/UserDB.java).

En el grupo de login, se manejó tanto el servicio de front con el formulario de inicio de sesión y la conexión hacía la validación del usuario. Esto por medio de la lectura segura de URL usando los certificados creados durante la sesión de laboratorio para crear un canal seguro dodne se comunique el cliente con el servicio de login.

Con el método de `location` se asigna la carpeta dentro del directorio _resources_ donde buscar los archivos estáticos que sean solicitados. En este caso, la carpeta se llama _public_ y en ella tenemos los archivos del formulario junto a su respectiva hoja de estilos y scripts.

Por su parte, para la gestión del usuario se tiene la clase [UserDB](src/main/java/edu/escuelaing/arem/ase/app/UserDB.java) que hace las veces de una base de datos donde maneja las funcionalidades de almacenamiento y consulta de registros, siendo revisar en un mapa si el usuario existe como llave y que su contraseña cifrada corresponda con el hash almacenado. Para asegurar que se almacena adecuadamente la contraseña, se guarda este valor como un hash generado por la clase [Cypher](src/main/java/edu/escuelaing/arem/ase/app/Cypher.java) que encripta la contraseña ingresada inicialmente bajo el algoritmo de `SHA3-256`.

Cuando el usuario ingresa, se hace la comparación entre la contraseña ingresada encriptada con este algoritmo y la contraseña almacenada del usuario también bajo este algoritmo. Si son iguales, se confirma la autenticidad del usuario y se entrega un mensaje que confirma el inicio de sesión. En caso contrario, se informa que la contraseña es incorrecta dado que los hash no coinciden.

## **Pruebas**

Para estas pruebas, vamos a acceder a la ruta http://localhost:5000/index.html usando el navegador de Firefox y veremos las peticiones realizadas en el apartado de red de su inspección de recursos.

+ Primero, iniciamos el servicio ejecutando tanto una máquina virtual con el servicio de la clase **login** y otra con el servicio de la clase **UserDB**

![Login corriendo](<Imágenes README/login corriendo.png>)
![UserDB corriendo](<Imágenes README/userdb corriendo.png>)

+ Ahora, si accedemos a la ruta mencionada, veremos la siguiente página con el inicio de sesión

![Login](<Imágenes README/login.png>)

+ Enviando un usuario y contraseña correcto, veremos un mensaje confirmando el inicio de sesión satisfactorio

![Login Ricardo](<Imágenes README/loginR.png>)

+ Ingresando con el mismo usuario pero la contraseña incorrecta, veremos que nos notifica de este error

![Login erróneo](<Imágenes README/LoginRmal.png>)

+ Si enviamos el otro nombre de usuario y su respectiva contraseña, veremos nuevamente el mensaje satisfactorio

![Login Recker](<Imágenes README/loginRec.png>)

+ Si no enviamos una contraseña, nos presenta un error diferente indicando que debemos enviarla

![Login sin contraseña](<Imágenes README/LoginSinpass.png>)

+ En caso de ingresar un nombre de usuario no registrado, nos indicará de dicha situación

![Usuario no encontrado](<Imágenes README/notFound.png>)

## **Despliegue en AWS**

A continuación, se muestra la operación de este servicio de mensajes en una instancia EC2 de AWS: 

## **Construido con**
  - [Git](https://git-scm.com) - Control de versiones
  - [Maven](https://maven.apache.org) - Administrador de dependencias
