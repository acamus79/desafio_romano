# desafio_romano
11/07/2024 18:45 hs
 La idea es que:
	La fase 1 la entregues el día lunes 15/07 a última hora.  
	La fase 2 el miércoles 17/07 a última hora o el jueves al mediodía a más tardar.

-----------------------------

# Kata Números romanos

### Fase 1

Desarrollar un componente que permita convertir números enteros a romanos y viceversa según el siguiente esquema: 

* 1 ➔ I
* 2 ➔ II
* 3 ➔ III
* 4 ➔ IV
* 5 ➔ V
* 9 ➔ IX
* 21 ➔ XXI
* 50 ➔ L
* 100 ➔ C
* 500 ➔ D
* 1000 ➔ M


En ambos métodos de conversión, el componente debe validar si se ingresa un valor no permitido y responder con una excepción personalizada. 

**Plus Fase 1:** Aplicar TDD o al menos hacer Tests unitarios del componente probando al menos 2 border cases para cada método de conversión.


### Fase 2 

Exponer el método del componente que convierte valores numéricos arábigos a romanos en un endpoint (GET) 
Exponer el método del componente que convierte valores numéricos romanos a arábigos en un endpoint (GET)

**Plus Fase 2:** Aplicar TDD (Test de integración usando la suite de Spring). 


### Requerimientos/Restricciones

**Fase 1 y 2:** Usar Java 17 o superior. Maven o Gradle para la gestión de dependencias. 
Para los puntos plus de cada fase, en lo relacionado a la infraestructura de tests se pueden usar las siguientes herramientas JUnit5+Mockito o Spock y Spring Boot Testing. 
**Fase 2:** Usar Spring boot 3+.

Completar y modificar este readme e incluirlo como parte del repositorio agregando detalles sobre cómo construir el proyecto desde cero y ponerlo en ejecución. 



 
