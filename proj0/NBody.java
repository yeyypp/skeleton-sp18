

/**
 * @author ShuaiYe
 * @date 2019/6/22 21:16
 */
public class NBody {
    public static void main(String[] args) {
         double T = Double.valueOf(args[0]);
         double dt = Double.valueOf(args[1]);
         String filename = args[2];
         double Radius = NBody.readRadius(filename);
         Planet[] planets = NBody.readPlanets(filename);

         StdDraw.setScale(-Radius, Radius);
         StdDraw.picture(0, 0, "images/starfield.jpg");
         for (Planet p : planets) {
             p.draw();
         }
         StdDraw.enableDoubleBuffering();
         double time = 0.0;
         double[] xForces = new double[planets.length];
         double[] yForces = new double[planets.length];
         while (time < T) {
             for (int i = 0; i < planets.length; i++) {
                 xForces[i] = planets[i].calcNetForceExertedByX(planets);
                 yForces[i] = planets[i].calcNetForceExertedByY(planets);
             }
             for (int i = 0; i < planets.length; i++) {
                 planets[i].update(dt, xForces[i], yForces[i]);
             }
             StdDraw.picture(0, 0, "images/starfield.jpg");
             for (Planet p : planets) {
                 p.draw();
             }
             StdDraw.show();
             StdDraw.pause(10);
            time += dt;
         }
         StdOut.printf("%d\n", planets.length);
         StdOut.printf("%.2e\n", Radius);
         for (int i = 0; i < planets.length; i++) {
             StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                     planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                     planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
         }
    }

    private static double readRadius(String path) {
        In in = new In(path);
        int number = in.readInt();
        double Radius = in.readDouble();
        return Radius;
    }

    private static Planet[] readPlanets(String path) {
        In in = new In(path);
        Planet[] planets = new Planet[in.readInt()];
        double Radius = in.readDouble();
        for (int i = 0; i < planets.length; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, img);
        }
        return planets;
    }
}
