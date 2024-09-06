/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.senahibenatesena;

import org.hibernate.Session;
import org.hibernate.Transaction;

import DB.SessionFactoryHS;
import DB.User;
import java.util.List;

/**
 *
 * @author Santiago Romero
 */
public class SenaHibenateSena {

    public static void main(String[] args) {
        // Crear un usuario
        createUser("Santiago Romero", "password123");
        createUser("Profe Oscar", "clave785");
        createUser("Ximena Romero", "asdf7e8w");
        createUser("Santiago Osorio", "as52d4f856");

        // Leer un usuario
        User user = getUser(2L);
        if (user != null) {
            System.out.println("--------------------- GetUser ---------------------");
            System.out.println("Usuario encontrado: " + user.getNombre());
            System.out.println("---------------------------------------------------");
        }

        // Actualizar un usuario
        updateUser(32L, "Santiago Actualizado", "SecurityPassword");

        // Eliminar un usuario
        deleteUser(33L);
        
        // Listar todos los usuarios
        System.out.println("--------------------- ListUsers ---------------------");                                                
        listUsers();
        System.out.println("-----------------------------------------------------");                                                                        
        // Cierra la sesión de Hibernate
        SessionFactoryHS.shutdown();
    }

    // Crear un usuario (Create)
    public static void createUser(String nombre, String contraseña) {
        Session session = SessionFactoryHS.getSessionFactory().openSession();
        Transaction transaction = null;

        try {            
            transaction = session.beginTransaction();
            User user = new User();            
            user.setNombre(nombre);
            user.setContraseña(contraseña);            
            session.save(user);
            transaction.commit();            
            System.out.println("--------------------- CreateUser ---------------------");
            System.out.println("Usuario " + nombre + " guardado");
            System.out.println("------------------------------------------------------");                 
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    // Leer un usuario por su ID (Read)
    public static User getUser(Long id) {
        try (Session session = SessionFactoryHS.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

    // Actualizar un usuario (Update)
    public static void updateUser(Long id, String nuevoNombre, String nuevaContraseña) {
        Session session = SessionFactoryHS.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                user.setNombre(nuevoNombre);
                user.setContraseña(nuevaContraseña);
                session.update(user);
                transaction.commit();
                System.out.println("--------------------- UpdateUser ---------------------");                
                System.out.println("Usuario actualizado con id: "+id);
                System.out.println("------------------------------------------------------");
            } else {
                System.out.println("--------------------- UpdateUser ---------------------");                                
                System.out.println("Usuario no encontrado");
                System.out.println("------------------------------------------------------");
                
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    // Eliminar un usuario por su ID (Delete)
    public static void deleteUser(Long id) {
        Session session = SessionFactoryHS.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                System.out.println("--------------------- DeleteUser ---------------------");                                
                System.out.println("Usuario eliminado con el id: "+id);
                System.out.println("------------------------------------------------------");                                                               
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    // Listar todos los usuarios (Read All)
    public static void listUsers() {
        try (Session session = SessionFactoryHS.getSessionFactory().openSession()) {
            List<User> users = session.createQuery("from User", User.class).list();
            if (users.isEmpty()) {
                System.out.println("--------------------- ListUsers ---------------------");                  
                System.out.println("No hay usuarios en la base de datos");
                System.out.println("-----------------------------------------------------");                                                                                    
            } else {
                for (User user : users) {
                                                                  
                    System.out.println("Usuario: " + user.getNombre() + ", Contrasena: " + user.getContraseña());
                }
            }
        }
    }
}
