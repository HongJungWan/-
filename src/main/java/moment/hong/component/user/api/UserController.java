package moment.hong.component.user.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moment.hong.component.user.api.request.UserLoginForm;
import moment.hong.component.user.api.request.UserSignUpForm;
import moment.hong.component.user.application.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userLoginForm", new UserLoginForm());
        return "users/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("userLoginForm") UserLoginForm userLoginForm) {
        userService.login(userLoginForm);
        return "redirect:/";
    }
    
    @GetMapping("/sign-up")
    public String singUp(@ModelAttribute("userSignUpForm") UserSignUpForm userSignUpForm) {
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute("userSignUpForm") UserSignUpForm userSignUpForm) {
        userService.join(userSignUpForm);
        return "redirect:/";
    }
}