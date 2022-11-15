package study.jpa_app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.jpa_app.domain.Member;
import study.jpa_app.repository.MemberRepositoryOld;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired MemberService memberService;

  @Autowired
  MemberRepositoryOld memberRepositoryOld;

  @Autowired EntityManager em;

  @Test
  public void join() {
    // given
    Member member = new Member();
    member.setName("MemberA");

    // when
    Long savedId = memberService.join(member);
    em.flush();

    // then
    assertEquals(member, memberRepositoryOld.findOne(savedId));
  }

  @Test()
  public void duplicatedMember() {
    // given
    Member member1 = new Member();
    member1.setName("MemberA");

    Member member2 = new Member();
    member2.setName("MemberA");

    // when
    memberService.join(member1);

    // then
    assertThrows(IllegalStateException.class, () -> memberService.join(member2));
  }
}
