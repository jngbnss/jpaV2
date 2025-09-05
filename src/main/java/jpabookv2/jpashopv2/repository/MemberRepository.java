package jpabookv2.jpashopv2.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabookv2.jpashopv2.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

//    @PersistenceContext
//    private EntityManager em;
//    //엔티티 매니저를 활용해서 데이터베이스에 저장하네?

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class,id); // em의 메서드는 클래스랑 id를 매개로 찾는건가?
    }

    public List<Member>findAll(){
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();

    }

    public List<Member>findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
