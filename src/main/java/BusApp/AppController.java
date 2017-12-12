package BusApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    AppService appService;

    @RequestMapping("/")
    public ModelAndView index()
    {
        appService.estadisticas();
        return new ModelAndView("index");
    }

    @RequestMapping("/routes")
    public ModelAndView routes(){
        List<Route> rutas = appService.listarRutas();
        return new ModelAndView("routes").addObject("rutas", rutas);
    }

    @RequestMapping("/times")
    public ModelAndView times(){
        return new ModelAndView("times");
    }

    @RequestMapping("/stops")
    public ModelAndView stops(){
        return new ModelAndView("stops");
    }

    @RequestMapping("/timetable")
    public ModelAndView timetable(){
        return new ModelAndView("timetable");
    }
}