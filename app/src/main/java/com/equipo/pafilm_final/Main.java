package com.equipo.pafilm_final;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== PRUEBA USUARIOS ===");

        // Insertamos 2 usuarios
        int idU1 = UsuarioDao.nuevoId();
        Usuario u1 = new Usuario("carlos", idU1, "1234");
        UsuarioDao.insertar(u1);
        System.out.println("Usuario insertado: " + u1.getNombreUsuario());

        int idU2 = UsuarioDao.nuevoId();
        Usuario u2 = new Usuario( "maria",idU2, "abcd");
        UsuarioDao.insertar(u2);
        System.out.println("Usuario insertado: " + u2.getNombreUsuario());

        // Comprobamos que se leen bien
        System.out.println("--- Todos los usuarios ---");
        int i = 0;
        while (i < UsuarioDao.obtenerUsuarios().size()) {
            Usuario u = UsuarioDao.obtenerUsuarios().get(i);
            System.out.println("ID: " + u.getIdUsuario() + " | Nombre: " + u.getNombreUsuario());
            i++;
        }

        // Probamos el login correcto
        Usuario loginCorrecto = UsuarioDao.login("carlos", "1234");
        if (loginCorrecto != null) {
            System.out.println("Login correcto: " + loginCorrecto.getNombreUsuario());
        } else {
            System.out.println("Login fallido");
        }

        // Probamos el login incorrecto
        Usuario loginFallido = UsuarioDao.login("carlos", "wrongpass");
        if (loginFallido != null) {
            System.out.println("Login correcto: " + loginFallido.getNombreUsuario());
        } else {
            System.out.println("Login fallido (correcto, la contraseña era incorrecta)");
        }

        // Probamos buscarPorId
        Usuario buscado = UsuarioDao.buscarPorId(1);
        if (buscado != null) {
            System.out.println("Encontrado por ID 1: " + buscado.getNombreUsuario());
        } else {
            System.out.println("No encontrado");
        }

        System.out.println("\n=== PRUEBA CONTENIDOS ===");

        // Insertamos 2 contenidos
        int idC1 = ContenidoDao.nuevoId();
        Contenido c1 = new Contenido(idC1, "Inception", 2010, 8.8, "Pelicula", false);
        ContenidoDao.insertar(c1);
        System.out.println("Contenido insertado: " + c1.getTitulo());

        int idC2 = ContenidoDao.nuevoId();
        Contenido c2 = new Contenido(idC2, "Breaking Bad", 2008, 9.5, "Serie", false);
        ContenidoDao.insertar(c2);
        System.out.println("Contenido insertado: " + c2.getTitulo());

        // Comprobamos que se leen bien
        System.out.println("--- Todos los contenidos ---");
        int j = 0;
        while (j < ContenidoDao.obtenerContenido().size()) {
            Contenido c = ContenidoDao.obtenerContenido().get(j);
            System.out.println("ID: " + c.getIdContenido() + " | Titulo: " + c.getTitulo() + " | Tipo: " + c.getTipo());
            j++;
        }

        // Probamos buscarPorId
        Contenido cBuscado = ContenidoDao.buscarPorId(1);
        if (cBuscado != null) {
            System.out.println("Encontrado por ID 1: " + cBuscado.getTitulo());
        } else {
            System.out.println("No encontrado");
        }

        System.out.println("\n=== PRUEBA RESENAS ===");

        // Insertamos 2 reseñas
        int idR1 = ResenaDao.nuevoId();
        Resena r1 = new Resena(idR1, "Obra maestra", "Pelicula", "Me encanto el final", 9.5f, u1.getIdUsuario(), c1.getIdContenido());
        ResenaDao.insertar(r1);
        System.out.println("Resena insertada: " + r1.getTitulo());

        int idR2 = ResenaDao.nuevoId();
        Resena r2 = new Resena(idR2, "Imprescindible", "Serie", "La mejor serie que he visto", 10.0f, u2.getIdUsuario(), c2.getIdContenido());
        ResenaDao.insertar(r2);
        System.out.println("Resena insertada: " + r2.getTitulo());

        // Comprobamos que se leen bien
        System.out.println("--- Todas las resenas ---");
        int k = 0;
        while (k < ResenaDao.obtenerResena().size()) {
            Resena r = ResenaDao.obtenerResena().get(k);
            System.out.println("ID: " + r.getIdResena() + " | Titulo: " + r.getTitulo() + " | Nota: " + r.getNota());
            k++;
        }

        // Probamos obtenerPorUsuario
        System.out.println("--- Resenas de carlos (id 1) ---");
        int l = 0;
        while (l < ResenaDao.obtenerPorUsuario(u1.getIdUsuario()).size()) {
            Resena r = ResenaDao.obtenerPorUsuario(u1.getIdUsuario()).get(l);
            System.out.println("Resena: " + r.getTitulo() + " | Comentario: " + r.getComentario());
            l++;
        }

        // Probamos obtenerPorContenido
        System.out.println("--- Resenas de Inception (id 1) ---");
        int m = 0;
        while (m < ResenaDao.obtenerPorContenido(c1.getIdContenido()).size()) {
            Resena r = ResenaDao.obtenerPorContenido(c1.getIdContenido()).get(m);
            System.out.println("Resena: " + r.getTitulo() + " | Nota: " + r.getNota());
            m++;
        }

        System.out.println("\n=== PRUEBA TERMINADA ===");
        System.out.println("Si ves todos los datos bien, el codigo funciona correctamente");
    }

}
