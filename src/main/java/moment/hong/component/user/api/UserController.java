package moment.hong.component.user.api;

import lombok.RequiredArgsConstructor;
import moment.hong.component.user.api.request.UserLoginForm;
import moment.hong.component.user.api.request.UserSingUpForm;
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
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model, @ModelAttribute UserLoginForm userLoginForm) {
        model.addAttribute("userLoginForm", userLoginForm);
        return "users/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginForm userLoginForm) {
        userService.login(userLoginForm);
        return "redirect:/";
    }

    @GetMapping("/sign-up")
    public String singUp(@ModelAttribute("userSingUpForm") UserSingUpForm userSingUpForm) {
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute("userSingUpForm") UserSingUpForm userSingUpForm) {
        userService.join(userSingUpForm);
        return "redirect:/";
    }
}