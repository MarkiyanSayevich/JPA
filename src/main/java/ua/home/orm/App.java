package ua.home.orm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ua.home.orm.entity.City;
import ua.home.orm.entity.Country;
import ua.home.orm.entity.User;

public class App 
{
    public static void main( String[] args ) throws Exception{
       EntityManagerFactory mf = Persistence.createEntityManagerFactory("join");       
       EntityManager em = mf.createEntityManager();
       em.getTransaction().begin();
       
       
       //addAll(em);
       
//       List<User> users =em.createQuery("SELECT u FROM User u",User.class).getResultList();
//       users.forEach(System.out::println);
       
//       List<Country> countries =  em.createQuery("SELECT c FROM Country c ORDER BY c.id DESC", Country.class).getResultList();
//       countries.forEach(System.out::println);
       
//       List<City> cities = em.createQuery("SELECT s FROM City s ORDER BY s.name",City.class).getResultList();
//       cities.forEach(System.out::println);
       
//       List<User> users = em.createQuery("SELECT u FROM User u ORDER BY u.id DESC",User.class).getResultList();
//       users.forEach(System.out::println);
       
//       List<Country> countries = em.createQuery("SELECT c FROM Country c WHERE c.name LIKE ?1",Country.class).setParameter(1, "c%").getResultList();      
//       countries.forEach(System.out::println);
       
       //НЕ ПАШЕ
//       List<City> cities = em.createQuery("SELECT s FROM City s Where s.name LIKE ?1 AND ?2", City.class).setParameter(1, "%e__").setParameter(2, "%2_").getResultList();
//       cities.forEach(System.out::println);
    		   
//       Integer minUser = em.createQuery("SELECT min(u.age) FROM User u", Integer.class).getSingleResult();
//       System.out.println(minUser);
       	
//       Double avg = em.createQuery("SELECT avg(u.age) FROM User u", Double.class).getSingleResult();
//       System.out.println("AVG: " + avg);
       
//       List<User> user = em.createQuery("SELECT u FROM User u Join u.city", User.class).getResultList();
//       user.forEach(System.out::println);
       
       
//       List<User> users = em.createQuery("SELECT u FROM User u JOIN u.city WHERE u.id NOT IN ?1", User.class).setParameter(1, Arrays.asList(2, 5, 9, 12, 13, 16)).getResultList();
//       users.forEach(System.out::println);
       
   
     //List<User> users = em.createQuery("SELECT u FROM User u  Left JOIN FETCH u.city s  Left JOIN FETCH s.country", User.class).getResultList();
      
       //task1(em);
       //task2(em);
       //task3(em);
       //task4(em);
       //task5(em);
       //task6(em);
       //task7(em);
       //task8(em);
       //task9(em);
       //task10(em);
       
       
      
       em.getTransaction().commit();
       em.close();
       mf.close(); 
    }
   
    static void addAll(EntityManager em) throws IOException {
    	
    	FileReader fr = null;
    	BufferedReader br = null;
    	String line;
    	int countCountry = 0;
    	int countCity = 0;
    	
    	File userFile = new File("users.txt");
    	File cityFile = new File("cities.txt");
    	File countryFile = new File("countries.txt");
    	
    	
    	//Додаємо країни 
    	
    	try {
    		fr = new FileReader(countryFile);
    		br = new BufferedReader(fr);
    		
    		while((line = br.readLine()) != null) {
    			countCountry+=1;
    			Country country = new Country();
    			country.setName(line);
    			em.persist(country);
    		}
    	}catch(FileNotFoundException fe){
    		System.err.println("File: " + countryFile + " - NOT FOUND");
    		return;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		fr.close();
    		br.close();
    	}
    	
    	
    	//Додаємо міста
    	
    	try {
    		List<Country> countries = em.createQuery("SELECT c FROM Country c", Country.class).getResultList();
    		
    		fr = new FileReader(cityFile);
    		br = new BufferedReader(fr);
    		
    		while((line = br.readLine())!=null) {
    			countCity+=1;
    			City city = new City();
    			city.setName(line);
    			city.setCountry(countries.get(new Random().nextInt(countCountry - 1) + 1));
    			em.persist(city);
    		}
    	}catch(FileNotFoundException fe) {
    		System.err.println("File: " + cityFile + " - NOT FOUND");
    		return;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		fr.close();
    		br.close();
    	}
    	
    	//Заповнюємо користувачів
    	
    	try {
    		fr = new FileReader(userFile);
    		br = new BufferedReader(fr);
    		
    		while((line = br.readLine())!=null) {
    			List<City> cities = em.createQuery("SELECT c FROM City c", City.class).getResultList();
    			User user = new User();
    			user.setFullName(line);
    			user.setAge(new Random().nextInt(40) + 60);
    			user.setCity(cities.get(new Random().nextInt(countCity - 1) + 1));
    			em.persist(user);
    		}
    	}catch(FileNotFoundException fe) {
    		System.err.println("File: " + userFile + " - NOTFOUND");
    		return;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		fr.close();
    		br.close();
    	}
    }
    
    static void task1(EntityManager em) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<User> cq = cb.createQuery(User.class);
    	Root<User> root = cq.from(User.class);
    	cq.select(root);
    	em.createQuery(cq).getResultList().forEach(System.out::println);
    }
    
    static void task2(EntityManager em) {
    	CriteriaBuilder cb =em.getCriteriaBuilder();
    	CriteriaQuery<Country> cq = cb.createQuery(Country.class);
    	Root<Country> root = cq.from(Country.class);
    	cq.orderBy(cb.desc(root.get("id")));
    	cq.select(root);
    	em.createQuery(cq).getResultList().forEach(System.out::println);
    }
    
    static void task3(EntityManager em) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<City> cq = cb.createQuery(City.class);
    	Root<City> root = cq.from(City.class);
    	cq.orderBy(cb.asc(root.get("name")));
    	cq.select(root);
    	em.createQuery(cq).getResultList().forEach(System.out::println);
    }
    
    static void task4(EntityManager em) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<User> cq = cb.createQuery(User.class);
    	Root<User> root = cq.from(User.class);
    	cq.orderBy(cb.desc(root.get("fullName")));
    	cq.select(root);
    	em.createQuery(cq).getResultList().forEach(System.out::println);
    }
    
    static void task5(EntityManager em) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Country> cq = cb.createQuery(Country.class);
    	Root<Country> root = cq.from(Country.class);
    	Expression<String> expression = root.get("name");
    	Predicate predicate = cb.like(expression, "a%");
    	cq.where(predicate);
    	em.createQuery(cq).getResultList().forEach(System.out::println);
    }
    
    static void task6(EntityManager em) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<City> cq = cb.createQuery(City.class);
    	Root<City> root = cq.from(City.class);
    	Expression<String> expression = root.get("name");
    	Predicate predicateOne = cb.like(expression, "%n_");
    	Predicate predicateTwo = cb.like(expression, "%r_");
    	Predicate predicateFinal = cb.or(predicateOne,predicateTwo);
    	cq.where(predicateFinal);
    	em.createQuery(cq).getResultList().forEach(System.out::println);
    }
    
    static void task7(EntityManager em) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
    	Root<User> root = cq.from(User.class);
    	cq.select(cb.min(root.get("age")));
    	int currAge = em.createQuery(cq).getSingleResult();
    	CriteriaQuery<User> cqu = cb.createQuery(User.class);
    	root = cqu.from(User.class);
    	Expression<Integer> expression = root.get("age");
    	Predicate predicate = cb.equal(expression, currAge);
    	cqu.where(predicate);
    	em.createQuery(cqu).getResultList().forEach(System.out::println);
    }
    
    static void task8(EntityManager em) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Double> cq = cb.createQuery(Double.class);
    	Root<User> root = cq.from(User.class);
    	cq.select(cb.avg(root.get("age")));
    	em.createQuery(cq).getResultList().forEach(System.out::println);
    }
    
    static void task9(EntityManager em) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<User> cq = cb.createQuery(User.class);
    	Root<User> root = cq.from(User.class);
    	cq.select(root);
    	Join<User, City> userCityJoin = root.join("city");
    	root.fetch("city");
    	em.createQuery(cq).getResultList().forEach(System.out::println);
    }
    
    static void task10(EntityManager em) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<User> cq = cb.createQuery(User.class);
    	Root<User> root = cq.from(User.class);
    	Expression<Integer> expression = root.get("id");
    	Predicate predicate = expression.in(Arrays.asList(2,5,9,12,13,16));
    	Join<User, City> userCityJoin = root.join("city");
    	cq.where(predicate.not());
    	em.createQuery(cq).getResultList().forEach(System.out::println);
    }
}

