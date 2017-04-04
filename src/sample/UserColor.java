package sample;

/**
 * Created by anton on 02.04.2017.
 */
public class UserColor {
    private double l;
    private double a;
    private double b;

    UserColor(double l, double a, double b){
        this.l = l;
        this.a = a;
        this.b = b;
    }
    UserColor(double one, double two, double three, String cs){
        switch(cs){
            case("Lab"): new UserColor(one, two, three);
                break;
            case("RGB"): int[] lab = rgb2lab((int)one,(int)two,(int)three);
                new UserColor(lab[0], lab[1], lab[2]);
                break;
        }
    }

    public void setL(double l)
    {
        this.l = l;
    }

    public double getL()
    {
        return l;
    }

    public void setA(double a)
    {
        this.a = a;
    }

    public double getA()
    {
        return a;
    }

    public void setB(double b)
    {
        this.b = b;
    }

    public double getB()
    {
        return b;
    }

    public static double colorDifference76(UserColor color1, UserColor color2)
    {
        double dL = Math.pow((color1.l - color2.l),2);
        double da = Math.pow((color1.a - color2.a),2);
        double db = Math.pow((color1.b - color2.b),2);

        double dE76 = Math.sqrt(dL + da + db);
        return dE76;
    }
    public static int[] rgb2lab(int Re, int Gr, int Bl) {

        double r = Re / 255.0;
        double g = Gr / 255.0;
        double b = Bl / 255.0;

        // D65 standard referent
        double X = 0.9504, Y = 1.0, Z = 1.0888;
        // D50 standard referent
        //double X = 0.9642, Y = 1.0, Z = 0.8251;


        // second, map sRGB to CIE XYZ
        r = r <= 0.04045 ? r / 12.92 : Math.pow((r + 0.055) / 1.055, 2.4);
        g = g <= 0.04045 ? g / 12.92 : Math.pow((g + 0.055) / 1.055, 2.4);
        b = b <= 0.04045 ? b / 12.92 : Math.pow((b + 0.055) / 1.055, 2.4);
        double x = (0.4124564 * r + 0.3575761 * g + 0.1804375 * b) / X,
                y = (0.2126729 * r + 0.7151522 * g + 0.0721750 * b) / Y,
                z = (0.0193339 * r + 0.1191920 * g + 0.9503041 * b) / Z;

        // third, map CIE XYZ to CIE L*a*b* and return
        x = x > 0.008856 ? Math.pow(x, 1.0 / 3) : 7.787037 * x + 4.0 / 29;
        y = y > 0.008856 ? Math.pow(y, 1.0 / 3) : 7.787037 * y + 4.0 / 29;
        z = z > 0.008856 ? Math.pow(z, 1.0 / 3) : 7.787037 * z + 4.0 / 29;

        int L = (int) Math.round(116 * y - 16),
                A = (int) Math.round(500 * (x - y)),
                B = (int) Math.round(200 * (y - z));

		/*  if (binSize > 0) {
		 L = binSize * Math.floor(L / binSize);
		 A = binSize * Math.floor(A / binSize);
		 B = binSize * Math.floor(B / binSize);
		 }*/

        return new int[]{L, A, B};
    }

    public int[] rgb() {
        // first, map CIE L*a*b* to CIE XYZ
        double y = (l + 16) / 116;
        double x = y + a/500;
        double z = y - b/200;

        // D65 standard referent
        double X = 0.950470, Y = 1.0, Z = 1.088830;

        x = X * (x > 0.206893034 ? x*x*x : (x - 4.0/29) / 7.787037);
        y = Y * (y > 0.206893034 ? y*y*y : (y - 4.0/29) / 7.787037);
        z = Z * (z > 0.206893034 ? z*z*z : (z - 4.0/29) / 7.787037);

        // second, map CIE XYZ to sRGB
        double r =  3.2404542*x - 1.5371385*y - 0.4985314*z;
        double g = -0.9692660*x + 1.8760108*y + 0.0415560*z;
        double b =  0.0556434*x - 0.2040259*y + 1.0572252*z;
        r = r <= 0.00304 ? 12.92*r : 1.055*Math.pow(r,1/2.4) - 0.055;
        g = g <= 0.00304 ? 12.92*g : 1.055*Math.pow(g,1/2.4) - 0.055;
        b = b <= 0.00304 ? 12.92*b : 1.055*Math.pow(b,1/2.4) - 0.055;

        // third, get sRGB values
        int ir = (int)Math.round(255*r); ir = Math.max(0, Math.min(ir, 255));
        int ig = (int)Math.round(255*g); ig = Math.max(0, Math.min(ig, 255));
        int ib = (int)Math.round(255*b); ib = Math.max(0, Math.min(ib, 255));
        return new int[]{ir,ig,ib};
    }
}