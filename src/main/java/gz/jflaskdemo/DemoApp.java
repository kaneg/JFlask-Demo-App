package gz.jflaskdemo;

import gz.jflask.FlaskContext;
import gz.jflask.annotation.App;
import gz.jflask.annotation.Default;
import gz.jflask.annotation.Route;
import gz.jflask.annotation.Var;
import gz.jflask.result.Redirect;
import gz.jflask.result.ResponseResult;
import gz.jflask.template.FileTemplate;
import gz.jflask.template.Template;

import javax.script.SimpleBindings;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: kaneg
 * Date: 6/13/15
 * Time: 3:42 PM
 */
@App()
public class DemoApp {
    @Route("/index")
    @Route("/index/<path>")
    @Route("/index/<path>/<page>")
    public Template index(@Default("foobar") String path, @Var("page") @Default("0") long arg) throws IOException {
        HttpServletRequest req = FlaskContext.getRequest();
        System.out.println("In index:" + req.getPathInfo());
        System.out.println("path:" + path);
        System.out.println("page :" + arg);

        return new FileTemplate("index.html");
    }


    @Route("/")
    public ResponseResult root() {
        return new Redirect("index");
    }


    @Route("/foo")
    public String foo1() {
        return "Hello, this is foo aa:" + new Foobar().foobar();
    }


    @Route("/bar")
    @Route("/bar/<name>")
    public Template bar(@Default("buddy")@Var("name") String name) {
        SimpleBindings context = new SimpleBindings();
        context.put("name", name);
        return new FileTemplate("bar.html", context);
    }


    @Route("/static/<path:path>")
    public void download(@Var("path") String path) throws Exception {
        FlaskContext.getResponse().getWriter().printf("download:" + path);
    }
}
