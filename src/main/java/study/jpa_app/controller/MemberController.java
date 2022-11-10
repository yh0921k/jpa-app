package study.jpa_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.jpa_app.domain.Address;
import study.jpa_app.domain.Member;
import study.jpa_app.service.MemberService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("members/new")
  public String createForm(Model model) {
    // 빈 객체 반환하여 유효성 검사등에 사용할 수 있음
    // name 필드에 에러가 있는경우 해당 메서드가 불리며 다시 폼이 렌더링되는데, 기존 에러가 없던 필드들을 유지
    model.addAttribute("memberForm", new MemberForm());
    return "members/createMemberForm";
  }

  @PostMapping("members/new")
  public String create(@Valid MemberForm memberForm, BindingResult result) {
    // 폼 클래스에 존재하는 유효성 검사 로직 처리를 위한 어노테이션 추가

    // 타임리프의 라이브러리를 보면 스프링 관련 라이브러리가 존재한다.
    // 스프링부트에서 타임리프를 위해, 혹은 타임리프에서 스프링부트를 위해 제공하는 기능들이 많으며 대표적으로 아래와 같다.
    if(result.hasErrors()) {
      return "members/createMemberForm";
    }

    Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
    Member member = new Member();
    member.setName(memberForm.getName());
    member.setAddress(address);

    memberService.join(member);
    return "redirect:/";
  }


}
