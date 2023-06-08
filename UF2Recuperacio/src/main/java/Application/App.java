package Application;



import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class App {

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		System.out.println("===========================================");
		System.out.println("MANTENIMENT EMPLEATS");
		System.out.println("===========================================");
		System.out.println("Escolliu quina acció voleu fer:");
		System.out.println("1. Consultar empleats");
		System.out.println("2. Inserir nou empleat");
		System.out.print("Opció: ");

		try {
			int option = Integer.parseInt(reader.readLine());

			if (option == 1) {
				consultaEmpleats(session);
			} else if (option == 2) {
				inserirNouEmpleat(session);
			} else {
				System.out.println("Opció invàlida.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		session.getTransaction().commit();
		session.close();
	}

	private static void consultaEmpleats(Session session) throws IOException {
		System.out.println("===========================================");
		System.out.println("CONSULTA EMPLEATS");
		System.out.println("===========================================");
		System.out.print("Introduir el nom de departament: ");
		String departmentName = reader.readLine();

		String hql = "SELECT d.deptno FROM Departments d WHERE d.deptname = :deptname";
		Query<Integer> query = session.createQuery(hql, Integer.class);
		query.setParameter("deptname", departmentName);

		Integer departmentNo = query.uniqueResult();
		if (departmentNo != null) {
			Departments department = session.get(Departments.class, departmentNo);
			System.out.println("\nEmpleats del departament " + department.getDeptName() + ":");
			List<Employees> employees = department.getEmployees();
			System.out.println("Emp_no\tNom\tCognoms\tData_naix\tRol\tSalari");
			System.out.println("======\t====\t=======\t========\t===\t=====");
			for (Employees employee : employees) {
				System.out.println(employee.getEmpNo() + "\t" + employee.getFirstName() + "\t"
						+ employee.getLastName() + "\t" + employee.getBirthDate() + "\t" + employee.getRole()
						+ "\t" + employee.getSalary());
			}
		} else {
			System.out.println("El departament no existeix.");
		}
	}

	private static void inserirNouEmpleat(Session session) throws IOException {
		System.out.println("===========================================");
		System.out.println("NOU EMPLEAT");
		System.out.println("===========================================");
		System.out.print("Nom: ");
		String firstName = reader.readLine();
		System.out.print("Cognom: ");
		String lastName = reader.readLine();
		System.out.print("Data de naixement (yyyy-MM-dd): ");
		String birthDate = reader.readLine();
		System.out.print("Gènere (M/F): ");
		String gender = reader.readLine();
		System.out.print("Data d'incorporació (yyyy-MM-dd): ");
		String hireDate = reader.readLine();
		System.out.print("Salari: ");
		float salary = Float.parseFloat(reader.readLine());
		System.out.print("Rol: ");
		String role = reader.readLine();
		System.out.print("Departament: ");
		int departmentNo = Integer.parseInt(reader.readLine());

		Departments department = session.get(Departments.class, departmentNo);
		if (department != null) {
			Employees employee = new Employees(firstName, lastName, birthDate, gender, hireDate, role, salary);
			department.addEmployee(employee);
			session.save(employee);
			System.out.println("Nou empleat inserit amb èxit.");
		} else {
			System.out.println("El departament no existeix.");
		}
	}
}
