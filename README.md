# ATM Web - Instrucciones de uso

## Resumen
Proyecto es de un cajero automático web (WAR) construido con JSF y servlets. Incluye:
- Login y registro de cuentas.
- Operaciones: depósito, retiro, consulta de saldo.
- Historial de transacciones y exportación a CSV.
- Carga inicial de clientes desde `src/main/resources/clients.csv`.

## Requisitos
- Java JDK 11+ (se recomienda JDK 17 o la versión usada en tu entorno; en el proyecto se probó con JDK 17+).
- Apache Tomcat 9.x (por ejemplo Tomcat 9.0.115). La aplicación está preparada para Tomcat 9.
- Maven (para compilar desde línea de comandos) o Eclipse con soporte Maven/WTP para desplegar desde el IDE.

> Nota: Tomcat 9 no incluye CDI por defecto; el proyecto incluye las dependencias necesarias (Weld) para que JSF funcione correctamente en Tomcat.

## Estructura principal
- `src/main/java/atm/` : código Java (beans, servlets, modelos).
- `src/main/webapp/` : páginas JSF (`*.xhtml`), JSP de fallback, CSS y `WEB-INF`.
- `src/main/resources/clients.csv` : clientes de prueba (account,balance,pin).
- `pom.xml` : dependencias y empaquetado WAR.

## Usuarios de prueba
El archivo `src/main/resources/clients.csv` contiene 5 clientes iniciales. Ejemplos:
- 100001 / PIN 1111 (saldo 500.00)
- 100002 / PIN 2222 (saldo 1200.50)
- 100003 / PIN 3333 (saldo 49.99)
- 100004 / PIN 4444 (saldo 0.00)
- 100005 / PIN 5555 (saldo 750.25)

## Pasos: compilar y desplegar
A) Usando Eclipse (recomendado):
1. Importa el proyecto: `File -> Import -> Existing Maven Projects` y selecciona la carpeta del proyecto.
2. Espera a que Eclipse descargue dependencias (Maven). Si se requiere, `Right-click Project -> Maven -> Update Project...`.
3. Asegúrate de tener un servidor Tomcat 9 configurado en la vista *Servers* de Eclipse.
4. `Right-click project -> Run As -> Run on Server` y elige tu Tomcat 9.
5. Observa la consola de Eclipse para verificar que no haya errores al arrancar. Si aparece una excepción, revisa las dependencias y los logs (console / `Servers` view).

B) Usando Maven y Tomcat manualmente:
1. Abre un terminal en la carpeta del proyecto:

```bash
cd /d D:\ATM-WEB-FINAL-TOMCAT9
mvn clean package -DskipTests
```
2. Copia `target/atm-web.war` a la carpeta `TOMCAT_HOME/webapps` y arranca Tomcat.
3. Revisa `TOMCAT_HOME/logs/catalina.*.log` si hay problemas.

## URLs y pruebas rápidas
- Página de entrada (JSF): `http://localhost:8080/atm-web/index.xhtml` (si se solicita `index.jsp` el proyecto redirige automáticamente a `index.xhtml`).
- Registro (JSF): `http://localhost:8080/atm-web/register.xhtml` (también se acepta `/register` y `/register/` por redirecciones añadidas).
- Menú principal: `http://localhost:8080/atm-web/menu.xhtml` (accesible tras login).

Flujo recomendado de prueba:
1. Abrir `index.xhtml` -> Registrar una nueva cuenta o usar un usuario de prueba.
2. Iniciar sesión con cuenta y PIN.
3. Usar `Depositar` y `Retirar` (valida que los mensajes de error aparezcan si PIN inválido, monto inválido o saldo insuficiente).
4. Ir a `Historial` y `Exportar historial` para generar el CSV en la carpeta `data` del servidor (se crea si no existe).

## Notas técnicas y consejos
- Si ves `javax.faces.FacesException: Unable to find CDI BeanManager` en los logs al arrancar, asegúrate de que Maven haya descargado las dependencias (Weld) y que el WAR desplegado contenga `weld-servlet-shaded-*.jar` en `WEB-INF/lib`.
- Si usas Eclipse, después de cambios en `pom.xml` ejecuta `Maven -> Update Project` y publica de nuevo en el servidor.
- Para depuración, revisa: `D:\EclipseWorkspace\ATM\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\logs\catalina.*.log` (ruta de ejemplo cuando se usa Tomcat desde Eclipse).

## Dónde editar los datos de prueba
- `src/main/resources/clients.csv` contiene las 3 columnas: `accountNumber,balance,pin`. Puedes editarlo y reiniciar la aplicación para recargar los clientes (se cargan en `@PostConstruct` al arrancar el contexto).

## Autores / Equipo
Por favor reemplaza estos nombres por los reales si deseas que aparezcan en la versión final:
- Nombre 1: Eric Eduardo Bonilla Rivera - 202110080015
- Nombre 2: Margie Nicole Dubon Romero  - 202220070063
- Nombre 3: Gabriela Maria Franco Peres - 201810110007

Fecha: 2026-03-08
