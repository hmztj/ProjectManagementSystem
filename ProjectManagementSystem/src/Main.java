import controller.Controller;
import model.Model;
import model.Scala;
import view.View;

public class Main {

    public static void main(String[] args) {

        View frame = new View();
        Model model = new Model();
        Scala scala = new Scala();

        Controller controller = new Controller(frame, model, scala);

    }
}
