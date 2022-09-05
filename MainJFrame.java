import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class MainJFrame extends JFrame {
    public MainJFrame() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setSize(450, 450);
    }


    double right = 100;
    double left = -100;
    double top = 100;
    double bottom = -100;
    double near = 1;
    double far = 100;


    Camera cam = new Camera(new ProjectionMatrix(right, left, top, bottom, far, near));

    double[] center = {65, 65, 65, 1};
    double[][] points = {{90, 40, 40, 1},//z is depth
            {90, 40, 90, 1},
            {40, 40, 90, 1},
            {40, 40, 40, 1},
            {90, 90, 40, 1},
            {90, 90, 90, 1},
            {40, 90, 90, 1},
            {40, 90, 40, 1}};

    int[] indices = {0, 1, 2, 3, 4, 5, 6, 7};

    //Vector projected = cam.ProjectPoint(new Vector(p));

    double[][] trans = {
            {1, 0, 0, center[0]},
            {0, 1, 0, center[0]},
            {0, 0, 1, center[0]},
            {0, 0, 0, 1}
    };

    double[][] transback = {
            {1, 0, 0, -center[0]},
            {0, 1, 0, -center[0]},
            {0, 0, 1, -center[0]},
            {0, 0, 0, 1}
    };

    double[][] pitcharr = {
            {Math.cos(0.05), 0, Math.sin(0.05), 0},
            {0, 1, 0, 0},
            {-Math.sin(0.05), 0, Math.cos(0.05), 0},
            {0, 0, 0, 1}
    };

    double[][] yawarr = {
            {1, 0, 0, 0},
            {0, Math.cos(0.03), -Math.sin(0.03), 0},
            {0, Math.sin(0.03), Math.cos(0.03), 0},
            {0, 0, 0, 1}
    };

    double[][] model = {
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
    };

    Matrix modelMatrix = new Matrix(model);
    Matrix transform = (new Matrix(trans)).Multiply(new Matrix(pitcharr)).Multiply(new Matrix(yawarr)).Multiply(new Matrix(transback));

    public void paint(Graphics g) {
        //System.out.print("\r" + newframe + "\r");
        //System.out.flush();

        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        //System.out.print(newframe);
        //c.render();
        //c.clear();

        for (int j = 0; j < indices.length; j++) {
            int i = indices[j];
            Vector projected1 = cam.ProjectPoint(new Vector(points[i]));
            Vector projected2 = cam.ProjectPoint(new Vector(points[(i + 1) % points.length]));
            int x1 = (int) (projected1.vector[0] * 450);
            int y1 = (int) (projected1.vector[1] * 450);

            int x2 = (int) (projected2.vector[0] * 450);
            int y2 = (int) (projected2.vector[1] * 450);
            Line2D line = new Line2D.Float(x1, y1, x2, y2);
            g2.draw(line);
            //c.drawLine(x1, y1, x2, y2, 3);
        }

        for (int i = 0; i < points.length; i++) {
            points[i] = (new Vector(points[i])).MatDot(transform).vector;
        }
    }

    public static void main(String[] args){
        MainJFrame main = new MainJFrame();
        main.setVisible(true);
    }
}