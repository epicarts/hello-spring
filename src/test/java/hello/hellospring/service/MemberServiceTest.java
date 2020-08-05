package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;

    //다른 인스턴스기 때문에 바꾸는게 좋음
//    MemberService memberService = new MemberService(MemoryMemberRepository);

//    MemoryMemberRepository repository = new MemoryMemberRepository();

    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        //필요한건 멤버 변수.. 그러기 위해서 멤버변수를 만들고 셋팅
        Member member = new Member();
        member.setName("hello");

        //than
        //실제 조인(회원가입)을 함. 반환값은 Id임
        Long saveId = memberService.join(member);

        //when
        //실제 저장이 되었는지 찾아서 멤버를 가져오고, 멤버에 넣은 이름과 합침.
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //when
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        memberService.join(member1);
        //로직을 실행했을때 이런 예외가 터져야함.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

//        try {
//            memberService.join(member2);
//            fail("예외가 발생해야 합니다.");
//
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }


        //than

        //given

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}