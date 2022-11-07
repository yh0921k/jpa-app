package study.jpa_app.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import study.jpa_app.JpaAppApplication;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = JpaAppApplication.class)
class MemberRepositoryTest {

  @Autowired private MemberRepository memberRepository;

  @Test
  @Transactional // 엔티티 매니저를 통한 모든 데이터 변경은 트랜잭션 안에서 수행되어야 하며,
  @Rollback(false) // 테스트 케이스에 @Transactional이 사용된 경우 테스트가 종료되면 자동으로 롤백을 수행하는데, 이를 방지
  public void testMember() throws Exception {
    // given
    Member member = new Member();
    member.setUsername("MemberA");

    // when
    Long saveId = memberRepository.save(member);
    Member findMember = memberRepository.find(saveId);

    // then
    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

    // 같은 영속성 컨텍스트 안에서는 식별자가 같으면 같은 엔티티(1차캐시)
    assertThat(findMember).isEqualTo(member);
  }
}
