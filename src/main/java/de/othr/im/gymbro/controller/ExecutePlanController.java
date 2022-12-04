package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.service.ExecutePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = {"/execute_plan"})
public class ExecutePlanController {
    private final ExecutePlanService executePlanService;

    @Autowired
    public ExecutePlanController(ExecutePlanService executePlanService) {
        this.executePlanService = executePlanService;
    }
}
