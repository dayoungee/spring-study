package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {

   // @PersistenceContext // 이게 있어야 인잭션을 해줌. jpa 하지만 스프링부트는
    //@Autowired // 오토와이어드만 써도 인잭션을 해주게함 그런데 생성자하나만 있으면 어차피 인잭션 해주는 거? lombok으로 간편하게 해결
    private final EntityManager em;

/*
    public MemberRepository(EntityManager em) {
        this.em = em;
    }
*/

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name).getResultList();
    }
}
