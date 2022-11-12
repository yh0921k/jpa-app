package study.jpa_app.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.jpa_app.domain.Member;
import study.jpa_app.service.MemberService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class MemberApiController {

  private final MemberService memberService;

  @GetMapping("/api/v1/members")
  public List<Member> membersV1() {
    return memberService.findMembers();
  }

  @PostMapping("/api/v1/members")
  public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
    Long id = memberService.join(member);

    return new CreateMemberResponse(id);
  }

  @PostMapping("/api/v2/members")
  public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

    Member member = new Member();
    member.setName(request.getName());

    Long id = memberService.join(member);
    return new CreateMemberResponse(id);
  }

  @PutMapping("/api/v2/members/{id}")
  public UpdateMemberResponse updateMemberV2(
      @PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request) {
    memberService.update(id, request.getName());
    Member member = memberService.findOne(id);
    return new UpdateMemberResponse(id, member.getName());
  }

  @Data
  static class UpdateMemberRequest {
    private String name;
  }

  @Data
  @AllArgsConstructor
  static class UpdateMemberResponse {
    private Long id;
    private String name;
  }

  @Data
  static class CreateMemberRequest {
    @NotEmpty private String name;
  }

  @Data
  static class CreateMemberResponse {
    private Long id;

    public CreateMemberResponse(Long id) {
      this.id = id;
    }
  }
}
