package repository;
import model.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class UserRepository implements CrudRepository<User, String>{
    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public User deleteById(String id) {
        return null;
    }

    @Override
    public Optional<User> findById(String id) {
        User user = entityManager.find(User.class, id);
        if(user!=null) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
