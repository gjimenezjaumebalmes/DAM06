Descripció del projecte

Aquest projecte mostra un exemple d'aplicació web que utilitza una base de dades per gestionar empleats i departaments. La base de dades s'ha creat a Railway, i s'utilitzen les següents taules: employees i departments.

L'aplicació permet visualitzar la llista d'empleats i departaments, així com afegir nous empleats i departaments mitjançant formularis web.

Configuració del projecte

Abans d'executar el projecte, assegura't de configurar les credencials de la base de dades al fitxer application.properties. Pots trobar aquest fitxer a la carpeta de configuració del projecte.

spring.datasource.url=jdbc:mysql://root:R4NbWxaR1q83JQfk1MrA@containers-us-west-155.railway.app:7187/railway
spring.datasource.username=root
spring.datasource.password=R4NbWxaR1q83JQfk1MrA

Descripció del codi
Classe Employees i Departments

Aquestes classes representen els models de dades per als empleats i departaments. Cada classe conté els atributs corresponents a les columnes de les taules de la base de dades.
Repositoris (EmployeeRepository i DepartmentRepository)

Aquests repositoris utilitzen JPA (Java Persistence API) per realitzar operacions de lectura/escriptura a la base de dades. Proporcionen mètodes per realitzar consultes i manipular les dades de les taules.
Serveis (EmployeeService i DepartmentService)

Els serveis encapsulen la lògica de negoci de l'aplicació. Interactuen amb els repositoris per realitzar operacions a la base de dades i proporcionen mètodes que són utilitzats pels controladors.
Controladors (EmployeeController i DepartmentController)

Els controladors són responsables de gestionar les sol·licituds HTTP i coordinar les respostes. Utilitzen els serveis corresponents per realitzar operacions a la base de dades i retornen les vistes o respostes adequades.

Vistes (HTML/Thymeleaf)

Es proporcionen fitxers HTML amb codi Thymeleaf per mostrar la interfície d'usuari. S'utilitzen etiquetes Thymeleaf per mostrar les dades recuperades de la base de dades i per crear formularis d'inserció.


