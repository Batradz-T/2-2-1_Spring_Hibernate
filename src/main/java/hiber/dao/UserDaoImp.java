package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
      //sessionFactory.getCurrentSession().save(user.getCar());
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("From User");
      return query.getResultList();
   }

   @Override
   public User getUserByModelAndSeries(String model, int series) {
      Query query = sessionFactory.getCurrentSession().createQuery("FROM Car where model=:modelName and series=:seriesName");
      query.setParameter("modelName", model).setParameter("seriesName", series);
      List<Car> carList = query.getResultList();
      User user = null;
      if (!carList.isEmpty()) {
         Long key = carList.get(0).getId();
         List<User> result = sessionFactory.getCurrentSession().createQuery("From User u where u.id=:key").setParameter("key", key).getResultList();
         user = result.get(0);
      }

//      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User u INNER JOIN u.car c WHERE c.model=:modelName and c.series=:seriesName");
//      query.setParameter("modelName", model).setParameter("seriesName", series);
//      List<User> userList = query.getResultList();

      return  user;
   }

   @Override
   public void delete(Long id) {
      Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM User WHERE id = :userId");
      query.setParameter("userId", id);
      query.executeUpdate();
   }

}
