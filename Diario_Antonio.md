-- Antonio: Encargado del Modelo

10/05/2026 --

Empezamos con la documentación del proyecto, poniendo en marcha las ideas y teniendo todo claro para por donde empezar y qué debe hacer cada uno

13/05/2026 -- 

Yo me encargo del modelo y ha sido un poco lioso porque habia empezado en eclipse para luego pasarlo a Android Studio pero no son iguales 
He tenido un par de problemas y me he pasado directamente a hacerlo entero en Android Studio 

14/05/2026 --

He creado el repositorio en github y he transfirido todo a mis compañeros ya que sin las clases ni metodos no pueden empezar porque se pueden liar,
nos hemos organizado en cuanto a los push pull y demas para no cometer fallos en cuanto a coger el proyecto y no tenerlo actualizado, 
he empezado con el codigo creando las clases de Usuario, Resena, Contenido y sus Daos pero nose que ponerles todavía.
Voy a crear un diagrama de entidad y luego ya veo con eso mas claro todo

15/05/2026 --

Para este trabajo al final he usado SQLite, que es la base de datos que viene con Android. Porque al hacer la app con lo mismo que en java da fallos 
no tengo ni idea por qué pero no me dejaba.
Al principio iba a usar archivos JSON pero al final lo he pasado todo a SQL para que sea una base de datos de verdad. (mucho lio la verdad)

Me he informado y ayudado de un par de IAs para saber que era la clase DatabaseHelper. (he usado gemini y claude) 
Cabe aclarar que las Ias que he usado solamente ha sido para desenstancarme de por dónde iba encaminado ya que en android es diferente a como 
tenía pensado en mi cabeza y la estructura de control que estoy acostumbrado a usar en eclipse

Esta clase es la que se encarga de crear las tres tablas: la de Usuarios, la de Contenido y la de las Reseñas. 
He usado el comando CREATE TABLE que hemos dado en clase para decirle qué campos tiene cada una y cuales son las claves primarias.

Luego están los DAOs, que es donde tengo todas las funciones y metodos para mandarlo a sql. 

Me he vuelto a informar porque estaba un poco perdido y para cuando quiero leer algo de la base de datos debo mandarle el objeto cursor para tener el resultado.
Funciona igual que el ResultSet de Java y por ahí lo he entendido mejor 
es como una tabla que vas recorriendo fila por fila con un bucle para sacar los nombres, las notas y todo lo que tiene la tabla.

Para conectar con la base de datos uso una función que la abre.Como el connect en java Si voy a escribir uso getWritableDatabase y si solo voy a mirar uso getReadableDatabase.

También he puesto un par de cosas automáticas para que sea más fácil. Por ejemplo, cuando alguien escribe una reseña nueva, 
la propia app calcula solo la nota media de la película para que siempre esté actualizada. 
También he creado unm metodo para que los ID se pongan solos buscando el número más alto que haya en la tabla + 1 de cada registro tanto contenido como usuario.

Con esto ya se quedan todos los datos guardados en el móvil aunque cierres la aplicación y se queda en la base de datos


16/05/2026 --

Termino de agregar un par de cosas a la documentación del proyecto. 

