# vaadin-spring-security
Integración entre Vaadin, Spring y Spring Security

Se ha integrado la demo existente de Vaadin Quicktickets y el nuevo add-on de Vaadin con Spring Framework. 
Además se ha añadido una integración con Spring Security para que permita realizar el login con Spring Security y también el Remember To.

El proyecto está construido sobre JAVA 8 además de contar también con JPA e Hibernate.

Cuenta con dos perfiles Usuario y Administrador. 

El menú esta generado en base a los perfiles existentes y el rol que se le asigne a la vista.

Además la navegación entre vistas está gestionada en base a los permisos y roles existentes sobre las vistas

http://dashboard.demo.vaadin.com/

https://vaadin.com/directory#!addon/vaadin-spring