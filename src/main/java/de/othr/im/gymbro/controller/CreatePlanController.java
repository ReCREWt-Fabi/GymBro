package de.othr.im.gymbro.controller;

import de.othr.im.gymbro.service.CreatePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = {"/create_plan"})
public class CreatePlanController {

    private final CreatePlanService createPlanService;

    @Autowired
    public CreatePlanController(CreatePlanService createPlanService) {
        this.createPlanService = createPlanService;
    }
}
