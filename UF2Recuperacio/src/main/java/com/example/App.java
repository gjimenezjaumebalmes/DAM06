package com.example;

import com.example.model.Departments;
import com.example.model.Employees;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class App {

	private static final SessionFactory sessionFactory;

	static {
		try {
			Configuration configuration = new Configuration().configure();
			sessionFactory = configuration.buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String[] args) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				menuPrincipal(session);
				transaction.commit();
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				e.printStackTrace();
			}
		}
	}

	public static void menuPrincipal(Session session) {
		Scanner scanner = new Scanner(System.in);
		int opcion = 0;

		while (opcion != 4) {
			System.out.println("MENU PRINCIPAL");
			System.out.println("===============");
			System.out.println("1. Gestió de departaments");
			System.out.println("2. Gestió d'empleats");
			System.out.println("3. Consultes");
			System.out.println("4. Sortir");
			System.out.print("Opció: ");
			opcion = scanner.nextInt();

			switch (opcion) {
				case 1:
					menuGestioDepartaments(session);
					break;
				case 2:
					menuGestioEmpleats(session);
					break;
				case 3:
					menuConsultes(session);
					break;
				case 4:
					System.out.println("Fins aviat!");
					break;
				default:
					System.out.println("Opció invàlida");
					break;
			}
		}
	}

	public static void menuGestioDepartaments(Session session) {
		Scanner scanner = new Scanner(System.in);
		int opcion = 0;

		while (opcion != 5) {
			System.out.println("MENU GESTIÓ DE DEPARTAMENTS");
			System.out.println("==========================");
			System.out.println("1. Llistar departaments");
			System.out.println("2. Inserir nou departament");
			System.out.println("3. Actualitzar departament");
			System.out.println("4. Eliminar departament");
			System.out.println("5. Tornar al menú principal");
			System.out.print("Opció: ");
			opcion = scanner.nextInt();

			switch (opcion) {
				case 1:
					llistarDepartaments(session);
					break;
				case 2:
					inserirNouDepartament(session);
					break;
				case 3:
					actualitzarDepartament(session);
					break;
				case 4:
					eliminarDepartament(session);
					break;
				case 5:
					System.out.println("Tornant al menú principal...");
					break;
				default:
					System.out.println("Opció invàlida");
					break;
			}
		}
	}

	public static void llistarDepartaments(Session session) {
		TypedQuery<Departments> query = session.createQuery("FROM Departments", Departments.class);
		List<Departments> departments = query.getResultList();

		if (departments.isEmpty()) {
			System.out.println("No hi ha cap departament");
		} else {
			System.out.println("LLISTA DE DEPARTAMENTS");
			System.out.println("======================");
			for (Departments department : departments) {
				System.out.println("DeptNo: " + department.getDeptNo() + ", DeptName: " + department.getDeptName());
			}
		}
	}

	public static void inserirNouDepartament(Session session) {
		System.out.println("===========================================");
		System.out.println("NOU DEPARTAMENT");
		System.out.println("===========================================");

		Scanner sc = new Scanner(System.in);
		System.out.print("Nom del departament: ");
		String nomDepartament = sc.nextLine();

		System.out.print("Ubicació del departament: ");
		String ubicacioDepartament = sc.nextLine();

		Departments nouDepartament = new Departments();
		nouDepartament.setDeptName(nomDepartament);
		nouDepartament.setLocation(ubicacioDepartament);

		Transaction transaction = null;
		try {
			if (!session.getTransaction().isActive()) {
				transaction = session.beginTransaction();
			}
			session.save(nouDepartament);
			if (transaction != null) {
				transaction.commit();
			}
			System.out.println("Departament inserit correctament");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			System.out.println("Error en inserir el departament");
		}
	}


	public static void actualitzarDepartament(Session session) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("ACTUALITZAR DEPARTAMENT");
		System.out.println("=======================");
		System.out.print("DeptNo del departament a actualitzar: ");
		int deptNo = scanner.nextInt();
		scanner.nextLine(); // Consumir el salto de línea

		Departments department = session.get(Departments.class, deptNo);
		if (department == null) {
			System.out.println("No s'ha trobat cap departament amb el DeptNo especificat");
			return;
		}

		System.out.print("Nou nom del departament: ");
		String nouNomDepartament = scanner.nextLine();

		department.setDeptName(nouNomDepartament);
		session.update(department);
		System.out.println("S'ha actualitzat el departament correctament");
	}

	public static void eliminarDepartament(Session session) {
		System.out.println("===========================================");
		System.out.println("ELIMINAR DEPARTAMENT");
		System.out.println("===========================================");

		Scanner sc = new Scanner(System.in);
		System.out.print("Introdueix el número de departament a eliminar: ");
		int deptNo = sc.nextInt();

		Transaction transaction = null;
		try {
			if (!session.getTransaction().isActive()) {
				transaction = session.beginTransaction();
			}

			Departments departament = session.get(Departments.class, deptNo);
			if (departament != null) {
				session.delete(departament);
				if (transaction != null) {
					transaction.commit();
				}
				System.out.println("Departament eliminat correctament");
			} else {
				System.out.println("No s'ha trobat cap departament amb aquest número de departament");
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			System.out.println("Error en eliminar el departament");
		}
	}


	public static void menuGestioEmpleats(Session session) {
		Scanner scanner = new Scanner(System.in);
		int opcion = 0;

		while (opcion != 5) {
			System.out.println("MENU GESTIÓ D'EMPLEATS");
			System.out.println("======================");
			System.out.println("1. Llistar empleats");
			System.out.println("2. Inserir nou empleat");
			System.out.println("3. Actualitzar empleat");
			System.out.println("4. Eliminar empleat");
			System.out.println("5. Tornar al menú principal");
			System.out.print("Opció: ");
			opcion = scanner.nextInt();

			switch (opcion) {
				case 1:
					llistarEmpleats(session);
					break;
				case 2:
					inserirNouEmpleat(session);
					break;
				case 3:
					actualitzarEmpleat(session);
					break;
				case 4:
					eliminarEmpleat(session);
					break;
				case 5:
					System.out.println("Tornant al menú principal...");
					break;
				default:
					System.out.println("Opció invàlida");
					break;
			}
		}
	}

	public static void llistarEmpleats(Session session) {
		TypedQuery<Employees> query = session.createQuery("FROM Employees", Employees.class);
		List<Employees> employees = query.getResultList();

		if (employees.isEmpty()) {
			System.out.println("No hi ha cap empleat");
		} else {
			System.out.println("LLISTA D'EMPLEATS");
			System.out.println("=================");
			for (Employees employee : employees) {
				System.out.println("EmpNo: " + employee.getEmpNo() + ", Nom: " + employee.getFirstName() + " " + employee.getLastName());
			}
		}
	}

	public static void inserirNouEmpleat(Session session) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("NOU EMPLEAT");
		System.out.println("===============");
		System.out.print("Nom: ");
		String nom = scanner.nextLine();
		System.out.print("Cognoms: ");
		String cognoms = scanner.nextLine();
		System.out.print("Data de naixement (format: dd/MM/yyyy): ");
		String dataNaixementStr = scanner.nextLine();
		Date dataNaixement = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dataNaixement = dateFormat.parse(dataNaixementStr);
		} catch (ParseException e) {
			System.out.println("Format de data incorrecte. S'assignarà la data actual.");
			dataNaixement = new Date();
		}
		System.out.print("Gènere: ");
		String genere = scanner.nextLine();
		System.out.print("Data d'incorporació (format: dd/MM/yyyy): ");
		String dataIncorporacioStr = scanner.nextLine();
		Date dataIncorporacio = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dataIncorporacio = dateFormat.parse(dataIncorporacioStr);
		} catch (ParseException e) {
			System.out.println("Format de data incorrecte. S'assignarà la data actual.");
			dataIncorporacio = new Date();
		}
		System.out.print("Rol: ");
		String rol = scanner.nextLine();
		System.out.print("Salari: ");
		double salari = scanner.nextDouble();
		scanner.nextLine();

		System.out.print("DeptNo del departament: ");
		int deptNo = scanner.nextInt();
		scanner.nextLine();

		Departments department = session.get(Departments.class, deptNo);
		if (department == null) {
			System.out.println("No s'ha trobat cap departament amb el DeptNo especificat");
			return;
		}

		Employees employee = new Employees(nom, cognoms, dataNaixement, genere, dataIncorporacio, rol, salari, department);
		session.save(employee);
		System.out.println("S'ha afegit el nou empleat correctament");
	}

	public static void actualitzarEmpleat(Session session) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("ACTUALITZAR EMPLEAT");
		System.out.println("===================");
		System.out.print("EmpNo de l'empleat a actualitzar: ");
		int empNo = scanner.nextInt();
		scanner.nextLine(); // Consumir el salto de línea

		Employees employee = session.get(Employees.class, empNo);
		if (employee == null) {
			System.out.println("No s'ha trobat cap empleat amb l'EmpNo especificat");
			return;
		}

		System.out.print("Nou nom de l'empleat: ");
		String nouNom = scanner.nextLine();
		System.out.print("Nou cognoms de l'empleat: ");
		String nousCognoms = scanner.nextLine();

		employee.setFirstName(nouNom);
		employee.setLastName(nousCognoms);
		session.update(employee);
		System.out.println("S'ha actualitzat l'empleat correctament");
	}

	public static void eliminarEmpleat(Session session) {
		System.out.println("===========================================");
		System.out.println("ELIMINAR EMPLEAT");
		System.out.println("===========================================");

		Scanner sc = new Scanner(System.in);
		System.out.print("Introdueix el número d'empleat a eliminar: ");
		int empNo = sc.nextInt();

		Transaction transaction = null;
		try {
			if (!session.getTransaction().isActive()) {
				transaction = session.beginTransaction();
			}

			Employees empleat = session.get(Employees.class, empNo);
			if (empleat != null) {
				session.delete(empleat);
				if (transaction != null) {
					transaction.commit();
				}
				System.out.println("Empleat eliminat correctament");
			} else {
				System.out.println("No s'ha trobat cap empleat amb aquest número d'empleat");
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			System.out.println("Error en eliminar l'empleat");
		}
	}

	public static void menuConsultes(Session session) {
		Scanner scanner = new Scanner(System.in);
		int opcion = 0;

		while (opcion != 3) {
			System.out.println("MENU CONSULTES");
			System.out.println("================");
			System.out.println("1. Consultar empleats per departament");
			System.out.println("3. Tornar al menú principal");
			System.out.print("Opció: ");
			opcion = scanner.nextInt();

			switch (opcion) {
				case 1:
					consultarEmpleatsPerDepartament(session);
					break;
				case 2:
					System.out.println("Tornant al menú principal...");
					break;
				default:
					System.out.println("Opció invàlida");
					break;
			}
		}
	}

	public static void consultarEmpleatsPerDepartament(Session session) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("CONSULTAR EMPLEATS PER DEPARTAMENT");
		System.out.println("=================================");
		System.out.print("DeptNo del departament: ");
		int deptNo = scanner.nextInt();
		scanner.nextLine(); // Consumir el salto de línea

		Departments department = session.get(Departments.class, deptNo);
		if (department == null) {
			System.out.println("No s'ha trobat cap departament amb el DeptNo especificat");
			return;
		}

		List<Employees> employees = department.getEmployees();
		if (employees.isEmpty()) {
			System.out.println("No hi ha empleats en aquest departament");
		} else {
			System.out.println("LLISTA D'EMPLEATS PER DEPARTAMENT");
			System.out.println("===============================");
			for (Employees employee : employees) {
				System.out.println("EmpNo: " + employee.getEmpNo() + ", Nom: " + employee.getFirstName() + " " + employee.getLastName());
			}
		}
	}

}
