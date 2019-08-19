import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private static final double ULLON = -122.2998046875;
    private static final double LRLON = -122.2119140625;
    private static final double ULLAT = 37.892195547244356;
    private static final double LRLAT = 37.82280243352756;
    private static final double LENGTH = Math.abs(ULLON) - Math.abs(LRLON);
    private static final double HEIGHT = ULLAT - LRLAT;

    public Rasterer() {
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {

        boolean query_success = true;
        int sizeOfGrid = getSizeOfGrid(params);

        double targetUllon = params.get("ullon");
        double targetUllat = params.get("ullat");
        double targetLrlon = params.get("lrlon");
        double targetLrlat = params.get("lrlat");

        if (targetUllon < ULLON) {
            targetUllon = ULLON;
        }
        if (targetUllat > ULLAT) {
            targetUllat = ULLAT;
        }
        if (targetLrlon > LRLON) {
            targetLrlon = LRLON;
        }
        if (targetLrlat < LRLAT) {
            targetLrlat = LRLAT;
        }

        String[] ullon = binarySearchUllon(sizeOfGrid, targetUllon);
        String[] ullat = binarySearchUllat(sizeOfGrid, targetUllat);
        String[] lrlon = binarySearchLrlon(sizeOfGrid, targetLrlon);
        String[] lrlat = binarySearchLrlat(sizeOfGrid, targetLrlat);

        int[] upLeft = new int[2];
        upLeft[0] = Integer.parseInt(ullon[0]);
        upLeft[1] = Integer.parseInt(ullat[0]);

        int[] lowRight = new int[2];
        lowRight[0] = Integer.parseInt(lrlon[0]);
        lowRight[1] = Integer.parseInt(lrlat[0]);

        int Ylength = lowRight[1] - upLeft[1] + 1;
        int Xlength = lowRight[0] - upLeft[0] + 1;

        int depth = getTargetDepth(params);

        String[][] render_grid = new String[Ylength][Xlength];

        int Xcoor = upLeft[0];
        int Ycoor = upLeft[1];

        for (int i = 0; i < Ylength; i++) {
            for (int j = 0; j < Xlength; j++) {
                render_grid[i][j] = "d" + depth + "_x" + Xcoor + "_y" + Ycoor + ".png";
                Xcoor++;
            }
            Xcoor = upLeft[0];
            Ycoor++;
        }

        double raster_ul_lon = Double.valueOf(ullon[1]);
        double raster_ul_lat = Double.valueOf(ullat[1]);
        double raster_lr_lon = Double.valueOf(lrlon[1]);
        double raster_lr_lat = Double.valueOf(lrlat[1]);


        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", query_success);
        System.out.println(results);
        return results;
    }

    private double getTargetLonDPP(Map<String, Double> params) {
        double targetLonDPP = (params.get("lrlon") - params.get("ullon")) / params.get("w");
        return targetLonDPP;
    }

    public double getLonDPP(double lrlon, double ullon, double width) {
        return (lrlon - ullon) / width;
    }

    private int getTargetDepth(Map<String, Double> params) {
        int targetDepth = 0;
        double targetLonDPP = getTargetLonDPP(params);
        for (int i = 0; i <= 7; i++) {
            double curLonDPP = getLonDPP(LRLON, LRLON - (LENGTH / (Math.sqrt(Math.pow(4, i)))), 256);
            if (curLonDPP <= targetLonDPP) {
                targetDepth = i;
                break;
            }
        }
        if (targetDepth == 0) {
            targetDepth = 7;
        }
        return targetDepth;
    }



    private int getSizeOfGrid(Map<String, Double> params) {
        int depth = getTargetDepth(params);
        int sizeOfGrid = (int) Math.sqrt(Math.pow(4, depth));
        return sizeOfGrid;
    }

    private double[] getUllonLrlon(int sizeOfGrid, int i) {
        double dis = LENGTH / sizeOfGrid;
        double ullon = ULLON + dis * i;
        double lrlon = ULLON + dis * (i + 1);
        double[] lon = new double[2];
        lon[0] = ullon;
        lon[1] = lrlon;
        return lon;
    }

    private double[] getUllatLrlat(int sizeOfGrid, int j) {
        double dis = HEIGHT / sizeOfGrid;
        double ullat = ULLAT - dis * j;
        double lrlat = ULLAT - dis * (j + 1);
        double[] lat = new double[2];
        lat[0] = ullat;
        lat[1] = lrlat;
        return lat;
    }

    private String[] binarySearchUllon(int sizeOfGrid, double targetUllon) {
        int left = 0, right = sizeOfGrid - 1;

        double[] lon = null;
        String[] ans = new String[2];
        while (left <= right) {
            int mid = left + (right - left) / 2;
            lon = getUllonLrlon(sizeOfGrid, mid);
            if (lon[0] <= targetUllon && lon[1] > targetUllon) {
                ans[0] = String.valueOf(mid);
                ans[1] = String.valueOf(lon[0]);
            }
            if (lon[0] > targetUllon) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private String[] binarySearchUllat(int sizeOfGrid, double targetUllat) {
        int left = 0, right = sizeOfGrid - 1;
        double[] lat = null;
        String[] ans = new String[2];
        while (left <= right) {
            int mid = left + (right - left) / 2;
            lat = getUllatLrlat(sizeOfGrid, mid);
            if (lat[0] >= targetUllat && lat[1] < targetUllat) {
                ans[0] = String.valueOf(mid);
                ans[1] = String.valueOf(lat[0]);
            }
            if (lat[0] < targetUllat) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private String[] binarySearchLrlon(int sizeOfGrid, double targetLrlon) {
        int left = 0, right = sizeOfGrid - 1;
        double[] lon = null;
        String[] ans = new String[2];
        while (left <= right) {
            int mid = left + (right - left) / 2;
            lon = getUllonLrlon(sizeOfGrid, mid);
            if (lon[1] >= targetLrlon && lon[0] < targetLrlon) {
                ans[0] = String.valueOf(mid);
                ans[1] = String.valueOf(lon[1]);
            }
            if (lon[1] < targetLrlon) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return ans;
    }

    private String[] binarySearchLrlat(int sizeOfGrid, double targetLrlat) {
        int left = 0, right = sizeOfGrid - 1;
        double[] lat = null;
        String[] ans = new String[2];
        while (left <= right) {
            int mid = left + (right - left) / 2;
            lat = getUllatLrlat(sizeOfGrid, mid);
            if (lat[1] <= targetLrlat && lat[0] > targetLrlat) {
                ans[0] = String.valueOf(mid);
                ans[1] = String.valueOf(lat[1]);
            }
            if (lat[1] > targetLrlat) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

}
