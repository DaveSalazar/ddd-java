package com.application.administration.users.infrastructure.persistence;

import com.application.administration.users.domain.User;
import com.application.administration.users.domain.UserEmail;
import com.application.administration.users.domain.UserRepository;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.shared.domain.Service;
import com.application.shared.domain.criteria.*;
import com.application.shared.infrastructure.hibernate.HibernateRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional("administration-transaction_manager")
public class HibernateUserRepository extends HibernateRepository<User> implements UserRepository {

    public HibernateUserRepository(@Qualifier("administration-session_factory") SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    @Override
    public void save(User user) {
        persist(user);
    }

    @Override
    public Optional<User> authenticate(UserEmail email) {
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter(new FilterField("email"), FilterOperator.EQUAL, new FilterValue(email.value())));
        Criteria criteria = new Criteria(new Filters(filters), FilterOrder.none());
        return Optional.ofNullable(byCriteria(criteria).get(0));
    }

    @Override
    public Optional<User> search(UserId id) {
        return byId(id);
    }

    @Override
    public List<User> searchAll() {
        return all();
    }

    @Override
    public List<User> matching(Criteria criteria) {
        return byCriteria(criteria);
    }
}
