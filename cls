[33m70b0ca8[m[33m ([m[1;36mHEAD[m[33m -> [m[1;32mmain[m[33m, [m[1;31morigin/main[m[33m)[m Creacion de las clases para los controladores de Materia. Y metodos para mostrar lista de materias, agregar materias, mostrar materia con ID.
[33m8161b9a[m Agregacion de Paginacion. Creacion de metodo getEstudiantes tipo Page.
[33mf614a20[m Separando los nombres y apellidos a otra clase y usando la notacion Embeddable y Embbeded para que la clase Nombre este embebida a la entidad Estudiante.
[33me57688c[m Agregando Entidad Materia. Creando metodos Add para la entidad y modificacion de metodo setLibro a add. Relacion muchos a muchos
[33mca871a3[m Creacion de nueva entidad Libro. Agregacion de metodo para asignar libro a un estudiante. Relacion uno a muchos.
[33m544d8c3[m Creacion de nueva entidad llamada CuentaBancaria que tiene una relacion con estudiante de uno a uno. Eliminar datos y agregar nuevos datos a data.sql. Agregar otras clases para Cuenta como: CuentaBancariaController, Service, Repository.
[33mca53897[m Agregacion de Transactional para los metodos
[33mf2ff099[m Agregando otros metodos de consultas derivados: findEstudianteByPrimerNombreOrPrimerApellido que tienen dos parametros.
[33m7dc6bc9[m Borrando EstudianteRepositoryMentiras de la clase EstudianteService
[33me6ab633[m Modificacion de metodos, usando Repositorio de JPA, Cambiando el metodo void de getEstudiante a tipo de dato Estudiante, agregando Check para el email, mejorando logica
[33m546b83e[m Agregando nuevos atributos y la conexion de base de datos con JPA
[33m981ff8c[m Agregando propiedades en application.properties
[33m9f1287d[m Agregando dependencia JPA manualmente y cambio de version en tag Parent
[33me7b9604[m Modificando la ruta de RequestMapping, agregando versiones.
[33m354183d[m Agregando metodo Get para ahora con id, para buscar un estudiante. Tambien modificacion de las rutas.
[33me146141[m Agregacion de PUT (actualizar datos de un estudiante). y uso de stream con lambda
[33m0f9be8d[m Agregacion de metodo DELETE y modificacion de long a Long.
[33med2a780[m Subiendo ejemplo de como usar Spring
