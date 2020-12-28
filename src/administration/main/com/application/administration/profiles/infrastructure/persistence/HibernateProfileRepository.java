package com.application.administration.profiles.infrastructure.persistence;

import com.application.administration.profiles.domain.Profile;
import com.application.administration.profiles.domain.ProfileRepository;
import com.application.administration.shared.domain.identifiers.UserId;
import com.application.administration.shared.domain.identifiers.ProfileId;
import com.application.administration.users.domain.User;
import com.application.shared.domain.Service;
import com.application.shared.domain.criteria.*;
import com.application.shared.infrastructure.hibernate.HibernateRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional("administration-transaction_manager")
public class HibernateProfileRepository extends HibernateRepository<Profile> implements ProfileRepository {

    public HibernateProfileRepository(@Qualifier("administration-session_factory") SessionFactory sessionFactory) {
        super(sessionFactory, Profile.class);
    }

    @Override
    public void save(Profile profile) {
        persist(profile);
    }

    @Override
    public Optional<Profile> search(ProfileId id) {
        return byId(id);
    }

    @Override
    public Optional<Profile> byUserId(UserId id) {
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter(new FilterField("memberId"), FilterOperator.EQUAL, new FilterValue(id.value())));
        Criteria criteria = new Criteria(new Filters(filters), FilterOrder.none());
        return byCriteria(criteria).stream().findFirst();
    }

    @Override
    public List<Profile> searchAll() {
        return all();
    }

    @Override
    public List<Profile> matching(Criteria criteria) {
        return byCriteria(criteria);
    }
}
