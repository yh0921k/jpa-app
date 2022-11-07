package study.jpa_app.member;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

  @PersistenceContext
  private EntityManager em;

  // Member를 반환하지 않는 이유는 명령과 조회를 분리하기 위함(CQRS)
  public Long save(Member member) {
    em.persist(member);
    return member.getId();
  }

  public Member find(Long id) {
    return em.find(Member.class, id);
  }
}
