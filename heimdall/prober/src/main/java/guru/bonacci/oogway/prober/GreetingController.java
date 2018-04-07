package guru.bonacci.oogway.prober;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {

    @GetMapping("/qa")
    public String qaForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "qa";
    }

    @PostMapping("/qa")
    public String qaSubmit(@ModelAttribute Greeting greeting) {
        return "result";
    }

}
