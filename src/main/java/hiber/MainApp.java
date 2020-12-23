package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);


      userService.add(new User("User1", "Lastname1", "user1@mail.ru", new Car("Model1", 101)));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", new Car("Model2", 102)));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru", new Car("Model3", 103)));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru", new Car("Model4", 104)));

      List<User> users = userService.listUsers();
      for (User user : users) {
         userService.delete(user.getId());
;
      }

      userService.add(new User("Ivanov", "Ivan", "@email", new Car("BMV", 777)));
      System.out.println(userService.getUserByModelAndSeries("BMV", 777));
      //userService.delete(2L);

      context.close();
   }
}
