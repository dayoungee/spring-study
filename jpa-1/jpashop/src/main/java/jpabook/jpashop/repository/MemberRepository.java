package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이렇게 만 적어도 Spring JPA에서 자동으로 JPQL생성해주고, Name을 찾아줌. 굳이 구현하지 않아도댐. 인터페이스만 만들어
    // 이것이 스프링 데이터 JPA
    // 앵간한건 다 제공해줌.
    public List<Member> findByName(String name);

}
